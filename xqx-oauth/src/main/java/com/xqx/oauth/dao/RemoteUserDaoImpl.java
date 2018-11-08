package com.xqx.oauth.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.dianping.cat.Cat;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.xqx.base.exception.CallRemoteServiceException;
import com.xqx.base.exception.ErrorCode;
import com.xqx.base.pojo.dto.UserDTO;
import com.xqx.base.vo.ResponseMessage;

@Component
public class RemoteUserDaoImpl implements IRemoteUserDao {

	private static final Logger logger = LoggerFactory.getLogger(RemoteUserDaoImpl.class);
	private static final Gson gson = new GsonBuilder().create();

	private static final String PROTOCOL = "http://";
	private static final String USER_DATA_SERVER_NAME = "XQX-USER-DATA";

	@Autowired
	RestTemplate restTemplate;

	@Override
	@Cacheable(value = "user", keyGenerator = "wiselyKeyGenerator", sync = true, condition = "#UserDTO != null")
	@HystrixCommand(fallbackMethod = "findUserByNameAndPasswordFallback")
	public UserDTO findUserByNameAndPassword(String name, String password) throws CallRemoteServiceException {
		MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<String, Object>();
		paramMap.add("name", name);
		paramMap.add("password", password);
		ResponseMessage<UserDTO> body = null;
		String url = PROTOCOL + USER_DATA_SERVER_NAME + "/findUserByNameAndPassword";
		try {
			body = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(paramMap),
					new ParameterizedTypeReference<ResponseMessage<UserDTO>>() {
					}).getBody();
		} catch (IllegalStateException e) {
			logger.error("远程访问失败：{}", e.getMessage());
			throw new CallRemoteServiceException(ErrorCode.REMOTE_EXCEPTION, "微服务" + USER_DATA_SERVER_NAME + "未启动");
		}
		logger.info("执行登陆结果 == " + body);
		if (body.getStatus() != 0) {
			logger.error("远程访问返回结果错误：{}", body.getMessage());
			throw new CallRemoteServiceException(ErrorCode.getErrorCode(body.getStatus()), body.getMessage());
		}
		return body.getData();
	}

	@Override
	@HystrixCommand(fallbackMethod = "doForbiddenByUserIdFallback")
	public boolean doForbiddenByUserId(Long userID) throws CallRemoteServiceException {
		// 调用登陆接口
		MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<String, Object>();
		paramMap.add("id", userID);

		String url = PROTOCOL + USER_DATA_SERVER_NAME + "/doForbiddenByUserId";
		String body = restTemplate.postForEntity(url, paramMap, String.class).getBody();

		logger.info("执行冻结用户结果 == " + body);
		ResponseMessage<?> responseMessage = gson.fromJson(body, ResponseMessage.class);
		if (responseMessage.getStatus() != 0) {
			throw new CallRemoteServiceException(ErrorCode.getErrorCode(responseMessage.getStatus()),
					responseMessage.getMessage());
		}
		return (Boolean) responseMessage.getData();

	}

	@Override
	@HystrixCommand(fallbackMethod = "doUnforbiddenByUserIdFallback")
	public boolean doUnforbiddenByUserId(Long userID) throws CallRemoteServiceException {
		// 调用登陆接口
		MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<String, Object>();
		paramMap.add("id", userID);

		String url = PROTOCOL + USER_DATA_SERVER_NAME + "/doUnforbiddenByUserId";
		String body = restTemplate.postForEntity(url, paramMap, String.class).getBody();

		logger.info("执行解冻用户结果 == " + body);
		ResponseMessage<?> responseMessage = gson.fromJson(body, ResponseMessage.class);
		if (responseMessage.getStatus() != 0) {
			throw new CallRemoteServiceException(ErrorCode.getErrorCode(responseMessage.getStatus()),
					responseMessage.getMessage());
		}
		return (Boolean) responseMessage.getData();
	}

	protected UserDTO findUserByNameAndPasswordFallback(String name, String password, Throwable throwable) {
		// cat埋点
		Cat.logError(throwable);
		return null;
	}

	protected boolean doForbiddenByUserIdFallback(String accessToken, Throwable throwable) {
		Cat.logError(throwable);
		return false;
	}

	protected boolean doUnforbiddenByUserIdFallback(Long userId, Throwable throwable) {
		Cat.logError(throwable);
		return false;
	}

}
