package com.xqx.oauth.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.xqx.base.exception.CallRemoteServiceException;
import com.xqx.base.exception.ErrorCode;
import com.xqx.base.pojo.dto.UserDTO;
import com.xqx.base.vo.ResponseMessage;
import com.xqx.oauth.servic.TokenServicImpl;


@Component
public class RemoteUserDaoImpl implements IRemoteUserDao {
	
	private static final Logger logger = LoggerFactory.getLogger(TokenServicImpl.class);
	private static final Gson gson = new GsonBuilder().create();
	private static final String serverName = "XQX-USER-DATA";
	
	@Autowired
    RestTemplate restTemplate;

	@Override
	@Cacheable(value = "user", keyGenerator = "wiselyKeyGenerator", sync = true)
	public UserDTO getUser(String name, String password) throws CallRemoteServiceException {
		// 调用登陆接口
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<String, Object>();
        paramMap.add("name", name);
        paramMap.add("password", password);
        String body = "";
        try {
	        body = restTemplate.postForEntity("http://" + serverName + "/getUser",paramMap, String.class).getBody();
        }catch (IllegalStateException e) {
        	logger.error("远程访问失败：{}", e.getMessage());
            throw new CallRemoteServiceException(ErrorCode.REMOTE_EXCEPTION, "微服务" + serverName + "未启动");
		}
        logger.info("执行登陆结果 == " + body);
        ResponseMessage<?> responseMessage = gson.fromJson(body, ResponseMessage.class);
        if (responseMessage.getStatus() != 0) {
            throw new CallRemoteServiceException(ErrorCode.getErrorCode(responseMessage.getStatus()), responseMessage.getMessage());
        }
        return gson.fromJson(responseMessage.getData().toString(),UserDTO.class);
	}

	@Override
	public boolean doForbidden(Long userID) throws CallRemoteServiceException {
		// 调用登陆接口
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<String, Object>();
        paramMap.add("id", userID);

        String body = restTemplate.postForEntity("http://"+serverName+"/doForbidden",paramMap, String.class).getBody();

        logger.info("执行冻结用户结果 == " + body);
        ResponseMessage<?> responseMessage = gson.fromJson(body, ResponseMessage.class);
        if (responseMessage.getStatus() != 0) {
            throw new CallRemoteServiceException(ErrorCode.getErrorCode(responseMessage.getStatus()), responseMessage.getMessage());
        }
        return (Boolean)responseMessage.getData();

	}
	
	@Override
	public boolean doUnforbidden(Long userID) throws CallRemoteServiceException {
		// 调用登陆接口
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<String, Object>();
        paramMap.add("id", userID);

        String body = restTemplate.postForEntity("http://"+serverName+"/doUnforbidden",paramMap, String.class).getBody();

        logger.info("执行解冻用户结果 == " + body);
        ResponseMessage<?> responseMessage = gson.fromJson(body, ResponseMessage.class);
        if (responseMessage.getStatus() != 0) {
            throw new CallRemoteServiceException(ErrorCode.getErrorCode(responseMessage.getStatus()), responseMessage.getMessage());
        }
        return (Boolean)responseMessage.getData();
	}


}
