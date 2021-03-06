package com.xqx.zuul.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.xqx.base.exception.CallRemoteServiceException;
import com.xqx.base.exception.ErrorCode;
import com.xqx.base.exception.ServiceException;
import com.xqx.base.pojo.dto.UserDTO;
import com.xqx.base.util.RemoteRespCheckUtils;
import com.xqx.base.vo.ResponseMessage;
import com.xqx.base.vo.Token;
import com.xqx.zuul.dao.IUserDataFeignClient;
import com.xqx.zuul.exception.TokenException;
import com.xqx.zuul.exception.TokenExpiredException;
import com.xqx.zuul.util.JWTHelper;

@Service
public class TokenServicImpl implements ITokenService {

	private static final Logger logger = LoggerFactory.getLogger(TokenServicImpl.class);

	private static final Gson gson = new GsonBuilder().create();

	/** 访问令牌过期时间 */
	@Value("${token.expried.time:1800}")
	private Long exprieTime;

	/** 刷新令牌过期时间 */
	@Value("${token.expried.max.time:86400}")
	private Long exprieTimeMax;

	@Autowired
	private IUserDataFeignClient feignClient;

	@Override
	public Token createTokenByNameAndPassword(String name, String password) throws ServiceException {
		if (Strings.isNullOrEmpty(name)) {
			logger.info("参数Name不能为空");
			throw new ServiceException(ErrorCode.ILLEGAL_ARGUMENT, "参数Name不能为空");
		}
		if (Strings.isNullOrEmpty(password)) {
			logger.info("参数Password不能为空");
			throw new ServiceException(ErrorCode.ILLEGAL_ARGUMENT, "参数Password不能为空");
		}

		ResponseMessage<?> remoteResp = feignClient.findUserByNameAndPassword(name, password);
		try {
			RemoteRespCheckUtils.checkResponse(remoteResp);
		} catch (CallRemoteServiceException e) {
			logger.info("查询用户信息失败，{}", e.getErrMsg());
			throw new ServiceException(e.getErrorCode(), "微服务访问失败");
		}

		if (remoteResp.getData() == null) {
			logger.info("未找到当前用户信息");
			throw new ServiceException(ErrorCode.DAO_NOTFOUND, "未找到当前用户信息");
		}
		UserDTO userDTO = new Gson().fromJson(remoteResp.getData().toString(), UserDTO.class);
// 		TODO 黑名单
//		if (userDTO.getForbidden()) {
//			BLACK_LIST.add(userDTO.getId());
//			throw new ServiceException(ErrorCode.TOKEN_BLACLIST, ErrorCode.TOKEN_BLACLIST.getDescription());
//		}
		// 生成Token
		return createTokenByUser(userDTO);
	}

	/**
	 * 通过用户信息签发新的Token实体内容
	 * 
	 * @param userDTO 用户信息
	 * @return Token实体
	 * @throws ServiceException 业务异常，包括：签发Token失败
	 */
	private Token createTokenByUser(UserDTO userDTO) throws ServiceException {
		if (userDTO == null) {
			logger.info("参数userInfo不能为空");
			throw new ServiceException(ErrorCode.ILLEGAL_ARGUMENT, "参数userInfo不能为空");
		}
		try {
			String userInfoStr = gson.toJson(userDTO);
			logger.info("userInfoStr:{}", userInfoStr);
			logger.info("Token过期时长：{}，最大过期时长：{}", exprieTime, exprieTimeMax);
			String accessToken = JWTHelper.sign(userInfoStr, exprieTime);
			String refreshToken = JWTHelper.sign(userInfoStr, exprieTimeMax);
			return new Token(userDTO.getName(), exprieTime, accessToken, refreshToken);
		} catch (Exception e) {
			logger.error("根据UserDTO生产Token失败", e);
			throw new ServiceException(e, ErrorCode.TOKEN_EXCEPTION, e.getMessage());
		}
	}

	@Override
	public Token refreshToken(String oldRefreshToken) throws ServiceException {
		if (Strings.isNullOrEmpty(oldRefreshToken)) {
			logger.info("参数RefreshToken不能为空");
			throw new ServiceException(ErrorCode.ILLEGAL_ARGUMENT, "参数RefreshToken不能为空");
		}
		try {
			UserDTO userDTO = verifyToken(oldRefreshToken);
			// 签发TOKEN
			String accessToken = JWTHelper.sign(gson.toJson(userDTO), exprieTime);
			return new Token(userDTO.getName(), exprieTime, accessToken, oldRefreshToken);
		} catch (JsonSyntaxException e) {
			throw new ServiceException(e, ErrorCode.TOKEN_EXCEPTION, e.getMessage());
		} catch (TokenException e) {
			throw new ServiceException(e, ErrorCode.TOKEN_EXCEPTION, e.getMessage());
		}
	}

	@Override
	public UserDTO verifyToken(String accessToken) throws ServiceException {
		if (Strings.isNullOrEmpty(accessToken)) {
			logger.info("参数accessToken不能为空");
			throw new ServiceException(ErrorCode.ILLEGAL_ARGUMENT, "参数accessToken不能为空");
		}
		try {
			System.out.println("accessToken ===  " + accessToken);
			String context = JWTHelper.unsign(accessToken);
			System.out.println("Context ===  " + context);
			UserDTO userDTO = gson.fromJson(context, UserDTO.class);
			// TODO 黑名单
			// checkBlackList(userDTO.getId());
			return userDTO;
		} catch (TokenExpiredException e) {
			// 过期
			logger.info("Token过期");
			throw new ServiceException(e, ErrorCode.TOKEN_EXPIRED, e.getMessage());
		} catch (TokenException e) {
			logger.info("Token异常", e.getMessage());
			throw new ServiceException(e, ErrorCode.TOKEN_EXCEPTION, e.getMessage());
		}
	}

}
