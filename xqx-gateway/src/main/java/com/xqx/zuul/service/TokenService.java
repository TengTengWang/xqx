package com.xqx.zuul.service;

import com.xqx.base.exception.ErrorCode;
import com.xqx.base.gson.GsonUtil;
import com.xqx.base.vo.ResponseMessage;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class TokenService {

    private static final Logger logger = LoggerFactory.getLogger(TokenService.class);
    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "verifyTokenFallback")
    public ResponseMessage<String> verifyToken(String token){
        logger.info("Token ==== "+token);

        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<String, String>();
        paramMap.add("accessToken", token);
        String body = restTemplate.postForEntity("http://XQX-OAUTH-v1/verifyToken", paramMap, String.class).getBody();
        logger.info("Body ====== "+body);
        return  GsonUtil.fromJson(body,ResponseMessage.class);
    }

    public ResponseMessage<String> verifyTokenFallback(String token,Throwable throwable){
        throwable.printStackTrace();
        return ResponseMessage.fail(ErrorCode.TIME_OUT.getCode(),"请求超时");
    }
}
