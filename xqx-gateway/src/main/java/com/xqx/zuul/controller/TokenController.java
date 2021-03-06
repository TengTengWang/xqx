package com.xqx.zuul.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xqx.base.exception.ServiceException;
import com.xqx.base.vo.ResponseMessage;
import com.xqx.base.vo.Token;
import com.xqx.zuul.service.ITokenService;

/**
 * 登录token签发与黑名单管理
 */
@RestController
public class TokenController {
	private static final Logger logger = LoggerFactory.getLogger(TokenController.class);

	@Autowired
	private ITokenService tokenService;

	/**
	 * 用户登录
	 * 
	 * @param name     用户名
	 * @param password 密码
	 * @return 包含登录信息的实体
	 * @throws ServiceException 业务异常，包括：参数为空错误、用户信息未找到错误、生成Token异常错误
	 */
	@PostMapping(value = "/login")
	public ResponseMessage<Token> doLogin(@RequestParam("name") String name, @RequestParam("password") String password)
			throws ServiceException {
		logger.info("用户登录，用户名：{}", name);

		Token token = tokenService.createTokenByNameAndPassword(name, password);

		return ResponseMessage.success(token);
	}

	/**
	 * 校验token
	 * 
	 * @param accessToken 访问令牌
	 * @return 校验是否通过的实体
	 * @throws ServiceException 业务异常，包括：参数为空错误，token过期错误，签发token异常错误，该用户在黑名单错误
	 */
	@PostMapping(value = "/verifyToken")
	public ResponseMessage<Boolean> verifyToken(@RequestParam("accessToken") String accessToken)
			throws ServiceException {
		logger.info("检验Token，token：{}", accessToken);

		tokenService.verifyToken(accessToken);

		return ResponseMessage.success(true);
	}

	/**
	 * 通过刷新令牌签发一个新的访问令牌
	 * 
	 * @param refreshToken 刷新令牌
	 * @return 新的访问令牌的实体
	 * @throws ServiceException 业务异常，包括：参数为空错误，签发Token失败错误
	 */
	@PostMapping(value = "/refreshToken")
	public ResponseMessage<Token> refreshToken(@RequestParam("refreshToken") String refreshToken)
			throws ServiceException {
		logger.info("更新Token，refreshToken：{}", refreshToken);

		Token token = tokenService.refreshToken(refreshToken);

		return ResponseMessage.success(token);
	}
}
