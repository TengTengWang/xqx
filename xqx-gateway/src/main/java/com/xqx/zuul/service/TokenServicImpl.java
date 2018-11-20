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
import com.xqx.base.vo.Token;
import com.xqx.zuul.dao.IRemoteUserDao;
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
	private IRemoteUserDao remoteUserDao;

	@Override
	public Token createTokenByNameAndPassword(String name, String password) throws ServiceException {
		if (Strings.isNullOrEmpty(name)) {
			throw new ServiceException(ErrorCode.ILLEGAL_ARGUMENT, "参数Name不能为空");
		}
		if (Strings.isNullOrEmpty(password)) {
			throw new ServiceException(ErrorCode.ILLEGAL_ARGUMENT, "参数Password不能为空");
		}

		// 通过User微服务检查登陆
		UserDTO userDTO = null;
		try {
			userDTO = remoteUserDao.findUserByNameAndPassword(name, password);
		} catch (CallRemoteServiceException e) {
			throw new ServiceException(e);
		}
		if (userDTO == null) {
			throw new ServiceException(ErrorCode.DAO_NOTFOUND, "未找到当前用户信息");
		}
		if (userDTO.getForbidden()) {
			BLACK_LIST.add(userDTO.getId());
			throw new ServiceException(ErrorCode.TOKEN_BLACLIST, ErrorCode.TOKEN_BLACLIST.getDescription());
		}
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
			throw new ServiceException(ErrorCode.ILLEGAL_ARGUMENT, "参数accessToken不能为空");
		}
		try {
			System.out.println("accessToken ===  " + accessToken);
			String context = JWTHelper.unsign(accessToken);
			System.out.println("Context ===  " + context);
			UserDTO userDTO = gson.fromJson(context, UserDTO.class);
			// 检查黑名单
			checkBlackList(userDTO.getId());
			return userDTO;
		} catch (TokenExpiredException e) {
			// 过期
			throw new ServiceException(e, ErrorCode.TOKEN_EXPIRED, e.getMessage());
		} catch (TokenException e) {
			throw new ServiceException(e, ErrorCode.TOKEN_EXCEPTION, e.getMessage());
		}
	}

	@Override
	public void addBlackList(Long userId) throws ServiceException {
		try {
			BLACK_LIST.add(userId);
			// TODO 是否加入xxl-job完成
			remoteUserDao.addBlackList(userId);
		} catch (CallRemoteServiceException e) {
			throw new ServiceException(e);
		} catch (Exception e) {
			throw new ServiceException(e, ErrorCode.UNKNOWN_ERROR, "添加黑名单失败");
		}
	}

	@Override
	public void removeBlackList(Long userId) throws ServiceException {
		try {
			BLACK_LIST.remove(userId);
			// TODO 是否加入xxl-job完成
			remoteUserDao.doUnforbiddenByUserId(userId);
		} catch (CallRemoteServiceException e) {
			throw new ServiceException(e);
		} catch (Exception e) {
			throw new ServiceException(e, ErrorCode.UNKNOWN_ERROR, "删除黑名单失败");
		}
	}

	/**
	 * 检查黑名单是否存在该用户
	 * 
	 * @param userId 用户唯一标识
	 * @throws ServiceException 业务异常，包含：用户已存在错误
	 */
	private void checkBlackList(Long userId) throws ServiceException {

		if (BLACK_LIST.contains(userId)) {
			throw new ServiceException(ErrorCode.TOKEN_BLACLIST, ErrorCode.TOKEN_BLACLIST.getDescription());
		}
	}

}
