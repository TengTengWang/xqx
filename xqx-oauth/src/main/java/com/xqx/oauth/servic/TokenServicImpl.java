package com.xqx.oauth.servic;


import com.google.common.base.Strings;
import com.xqx.base.exception.CallRemoteServiceException;
import com.xqx.base.exception.ErrorCode;
import com.xqx.base.exception.ServiceException;
import com.xqx.base.gson.GsonUtil;
import com.xqx.base.vo.ResponseMessage;
import com.xqx.base.vo.Token;
import com.xqx.oauth.exception.TokenException;
import com.xqx.oauth.exception.TokenExpiredException;
import com.xqx.oauth.util.JWTHelper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class TokenServicImpl implements TokenService {

    private static final Logger logger = LoggerFactory.getLogger(TokenServicImpl.class);

    @Autowired
    RestTemplate restTemplate;

    private static final Long EXPIRE_TIME = 60000L;
    private static final Long EXPIRE_TIME_MAX = 60000L * 10;



    @HystrixCommand(fallbackMethod = "doLoginFallback")
    private String doLogin(String name, String password) throws CallRemoteServiceException {
        // 调用登陆接口
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<String, Object>();
        paramMap.add("name", name);
        paramMap.add("password", password);

        String body = restTemplate.postForEntity("http://XQX-USER/getUser",paramMap, String.class).getBody();

        logger.info("执行登陆结果 == " + body);
        return body;
    }

    public String doLoginFallback(Throwable throwable) {
        return "Error! " + throwable.getLocalizedMessage();
    }

    @Override
    public Token createToken(String name, String password) throws ServiceException {
        if (Strings.isNullOrEmpty(name)) {
            throw new ServiceException(ErrorCode.ILLEGAL_ARGUMENT, "参数Name不能为空");
        }
        if (Strings.isNullOrEmpty(password)) {
            throw new ServiceException(ErrorCode.ILLEGAL_ARGUMENT, "参数Password不能为空");
        }

        try {
            String body = doLogin(name, password);
            ResponseMessage responseMessage = GsonUtil.fromJson(body, ResponseMessage.class);
            if (responseMessage.getStatus() != 0) {
                throw new ServiceException(ErrorCode.getErrorCode(responseMessage.getStatus()), responseMessage.getMessage());
            }
            Token token = signToken(responseMessage.getData().toString());
            return token;
        } catch (CallRemoteServiceException e) {
            throw new ServiceException(e,e.getErrorCode(),e.getErrMsg());
        } catch (TokenException e) {
            throw new ServiceException(e, ErrorCode.TOKEN_EXCEPTION, "构建Token失败");
        }
    }

    @Override
    public Token createToken(String context) throws ServiceException {
        if (Strings.isNullOrEmpty(context)) {
            throw new ServiceException(ErrorCode.ILLEGAL_ARGUMENT, "参数SignContext不能为空");
        }
        try {
            return signToken(context);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(e, ErrorCode.TOKEN_EXCEPTION, e.getMessage());
        }

    }

    @Override
    public String verifyToken(String accessToken) throws ServiceException {
        if (Strings.isNullOrEmpty(accessToken)) {
            throw new ServiceException(ErrorCode.ILLEGAL_ARGUMENT, "参数accessToken不能为空");
        }
        try {
            return JWTHelper.unsign(accessToken);
        } catch (TokenExpiredException e) {
            // 过期
            throw new ServiceException(e, ErrorCode.TOKEN_EXPIRED, e.getMessage());
        } catch (TokenException e) {
            throw new ServiceException(e, ErrorCode.TOKEN_EXCEPTION, e.getMessage());
        }
    }

    private Token signToken(String str) throws TokenException {
        String accessToken = JWTHelper.sign(str, EXPIRE_TIME);
        String refreshToken = JWTHelper.sign(str, EXPIRE_TIME_MAX);
        return new Token(accessToken, refreshToken);
    }
}
