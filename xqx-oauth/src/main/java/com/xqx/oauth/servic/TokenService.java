package com.xqx.oauth.servic;

import com.xqx.base.exception.ServiceException;
import com.xqx.base.vo.Token;

/**
 * token服务接口
 */
public interface TokenService {

    Token createToken(String name, String password) throws ServiceException;

    Token createToken(String context) throws ServiceException;

    String verifyToken(String accessToken) throws ServiceException;

}
