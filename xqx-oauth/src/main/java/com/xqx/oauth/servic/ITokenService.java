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
	// 黑名单，设置初始容量
    static final Set<Long> BLACK_LIST = new HashSet<>(16000);

    Token createToken(String name, String password) throws ServiceException;

    Token createToken(UserDTO userDTO) throws ServiceException;
    
    Token refreshToken(String oldRefreshToken) throws ServiceException;

    UserDTO verifyToken(String accessToken) throws ServiceException;

    void addBlackList(Long userId) throws ServiceException;
    
    void removeBlackList(Long userId) throws ServiceException;
}
