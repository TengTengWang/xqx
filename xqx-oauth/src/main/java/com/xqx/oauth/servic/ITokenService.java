package com.xqx.oauth.servic;

import java.util.HashSet;
import java.util.Set;

import com.xqx.base.exception.ServiceException;
import com.xqx.base.pojo.dto.UserDTO;
import com.xqx.base.vo.Token;

/**
 * token服务接口
 */
public interface ITokenService {
	/** 黑名单，设置初始容量 */
	static final Set<Long> BLACK_LIST = new HashSet<>(16000);

	/**
	 * 根据用户输入身份信息生成TOKEN
	 * 
	 * @param name     用户名
	 * @param password 密码
	 * @return Token实体
	 * @throws ServiceException 业务异常，包括：参数为空错误、用户信息未找到错误、生成Token异常错误
	 */
	Token createTokenByNameAndPassword(String name, String password) throws ServiceException;

	/**
	 * 根据刷新令牌签发新的访问令牌Token实体
	 * 
	 * @param oldRefreshToken刷新令牌
	 * @return Token实体
	 * @throws ServiceException 业务异常，包括：参数为空错误，签发Token失败错误
	 */
	Token refreshToken(String oldRefreshToken) throws ServiceException;

	/**
	 * 解析访问令牌
	 * 
	 * @param accessToken 访问令牌
	 * @return 用户数据UserDTO实体
	 * @throws ServiceException 业务异常，包括：参数为空错误，token过期错误，签发token异常错误，该用户在黑名单错误
	 */
	UserDTO verifyToken(String accessToken) throws ServiceException;

	/**
	 * 添加用户到黑名单
	 * 
	 * @param userId 用户唯一标识
	 * @throws ServiceException 业务异常，包括：访问远程服务失败错误，未知异常错误
	 */
	void addBlackList(Long userId) throws ServiceException;

	/**
	 * 将用户从黑名单移除
	 * 
	 * @param userId 用户唯一标识
	 * @throws ServiceException业务异常，包括：访问远程服务失败错误，未知异常错误
	 */
	void removeBlackList(Long userId) throws ServiceException;
}
