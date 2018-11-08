package com.xqx.oauth.servic;

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
import com.xqx.oauth.dao.IRemoteUserDao;
import com.xqx.oauth.exception.TokenException;
import com.xqx.oauth.exception.TokenExpiredException;
import com.xqx.oauth.util.JWTHelper;

@Service
public class TokenServicImpl implements ITokenService {

	private static final Logger logger = LoggerFactory.getLogger(TokenServicImpl.class);

	private static final Gson gson = new GsonBuilder().create();

	@Value("${token.expried.time:1800}")
	private Long exprieTime;

	@Value("${token.expried.max.time:86400}")
	private Long exprieTimeMax;

	@Autowired
	private IRemoteUserDao remoteUserDao;

	/**
	 * 根据用户输入身份信息生成TOKEN
	 */
	@Override
	public Token createTokenByNameAndPassword(String name, String password) throws ServiceException {
		if (Strings.isNullOrEmpty(name)) {
			throw new ServiceException(ErrorCode.ILLEGAL_ARGUMENT, "参数Name不能为空");
		}
		if (Strings.isNullOrEmpty(password)) {
			throw new ServiceException(ErrorCode.ILLEGAL_ARGUMENT, "参数Password不能为空");
		}

		try {
			// 通过User微服务检查登陆
			UserDTO userDTO = remoteUserDao.findUserByNameAndPassword(name, password);
			if (userDTO == null) {
				throw new ServiceException(ErrorCode.DAO_NOTFOUND, "未找到当前用户信息");
			}
			if (userDTO.getForbidden()) {
				BLACK_LIST.add(userDTO.getId());
				throw new ServiceException(ErrorCode.TOKEN_BLACLIST, ErrorCode.TOKEN_BLACLIST.getDescription());
			}
			// 生成Token
			return createTokenByUser(userDTO);
		} catch (CallRemoteServiceException e) {
			throw new ServiceException(e, e.getErrorCode(), e.getErrMsg());
		}
	}

	@Override
	public Token createTokenByUser(UserDTO userDTO) throws ServiceException {
		if (userDTO == null) {
			throw new ServiceException(ErrorCode.ILLEGAL_ARGUMENT, "参数userInfo不能为空");
		}
		try {
			String userInfoStr = gson.toJson(userDTO);
			logger.info("Token过期时长：{}，最大过期时长：{}", exprieTime, exprieTimeMax);
			String accessToken = JWTHelper.sign(userInfoStr, exprieTime);
			String refreshToken = JWTHelper.sign(userInfoStr, exprieTimeMax);
			return new Token(userDTO.getName(), exprieTime, accessToken, refreshToken);
		} catch (Exception e) {
			e.printStackTrace();
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
			String accessToken = JWTHelper.sign(userDTO.toString(), exprieTime);
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
			String context = JWTHelper.unsign(accessToken);

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
			remoteUserDao.doForbiddenByUserId(userId);
		} catch (CallRemoteServiceException e) {
			throw new ServiceException(e, e.getErrorCode(), e.getErrMsg());
		} catch (Exception e) {
			throw new ServiceException(e, ErrorCode.UNKNOWN_ERROR, "添加黑名单失败");
		}
	}

	@Override
	public void removeBlackList(Long userId) throws ServiceException {
		try {
			BLACK_LIST.remove(userId);
			remoteUserDao.doUnforbiddenByUserId(userId);
		} catch (CallRemoteServiceException e) {
			throw new ServiceException(e, e.getErrorCode(), e.getErrMsg());
		} catch (Exception e) {
			throw new ServiceException(e, ErrorCode.UNKNOWN_ERROR, "删除黑名单失败");
		}
	}

	/**
	 * 检查黑名单
	 * 
	 * @param userId
	 * @throws ServiceException
	 */
	private void checkBlackList(Long userId) throws ServiceException {

		if (BLACK_LIST.contains(userId)) {
			throw new ServiceException(ErrorCode.TOKEN_BLACLIST, ErrorCode.TOKEN_BLACLIST.getDescription());
		}
	}

}
