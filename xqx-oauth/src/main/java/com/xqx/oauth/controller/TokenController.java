package com.xqx.oauth.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.xqx.base.exception.ErrorCode;
import com.xqx.base.exception.ServiceException;
import com.xqx.base.vo.ResponseMessage;
import com.xqx.base.vo.Token;
import com.xqx.oauth.servic.ITokenService;

@RestController
public class TokenController {
	private static final Logger logger = LoggerFactory.getLogger(TokenController.class);

	@Autowired
	private ITokenService tokenService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "doLoginFallback")
	public ResponseMessage<Token> doLogin(@RequestParam("name") String name,
			@RequestParam("password") String password) {
		logger.info("登陆，用户名：{}", name);
		try {
			Token token = tokenService.createTokenByNameAndPassword(name, password);
			if (token == null) {
				return ResponseMessage.fail(ErrorCode.TOKEN_EXCEPTION.getCode(), "创建Token失败");
			} else {
				return ResponseMessage.success(token);
			}
		} catch (ServiceException e) {
			logger.warn("登录失败：ErrorCode = {}, Message = {}", e.getErrorCode().getCode(), e.getErrMsg());
			return ResponseMessage.fail(e.getErrorCode().getCode(), e.getErrMsg());
		}
	}

	@RequestMapping(value = "/verifyToken", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "doVerifyTokenFallback")
	public ResponseMessage<Boolean> verifyToken(@RequestParam("accessToken") String accessToken) {
		logger.info("检验Token，token：{}", accessToken);
		try {
			if (tokenService.verifyToken(accessToken) != null) {
				return ResponseMessage.success(true);
			} else {
				return ResponseMessage.fail(ErrorCode.TOKEN_EXCEPTION.getCode(), "Token解析失败");
			}
		} catch (ServiceException e) {
			return ResponseMessage.fail(e.getErrorCode().getCode(), e.getErrMsg());
		}
	}

	@RequestMapping(value = "/refreshToken", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "doRefreshTokenFallback")
	public ResponseMessage<Token> refreshToken(@RequestParam("refreshToken") String refreshToken) {
		logger.info("更新Token，refreshToken：{}", refreshToken);
		try {

			Token token = tokenService.refreshToken(refreshToken);
			if (token == null) {
				return ResponseMessage.fail(ErrorCode.TOKEN_EXCEPTION.getCode(), "刷新Token失败");
			} else {
				return ResponseMessage.success(token);
			}
		} catch (ServiceException e) {
			return ResponseMessage.fail(e.getErrorCode().getCode(), e.getErrMsg());
		}
	}

	@RequestMapping(value = "/addBlackList")
	@HystrixCommand(fallbackMethod = "doAddBlackListFallback")
	public ResponseMessage<Boolean> addBlackList(@RequestParam("userId") Long userId) {
		logger.info("添加黑名单：{}", userId);
		try {
			tokenService.addBlackList(userId);
			return ResponseMessage.success(true);
		} catch (ServiceException e) {
			return ResponseMessage.fail(e.getErrorCode().getCode(), e.getErrMsg());
		}
	}

	@RequestMapping(value = "/removeBlackList")
	@HystrixCommand(fallbackMethod = "doRemoveBlackListFallback")
	public ResponseMessage<Boolean> removeBlackList(@RequestParam("userId") Long userId) {
		logger.info("删除黑名单：{}", userId);
		try {
			tokenService.removeBlackList(userId);
			return ResponseMessage.success(true);
		} catch (ServiceException e) {
			return ResponseMessage.fail(e.getErrorCode().getCode(), e.getErrMsg());
		}
	}

	protected ResponseMessage<Token> doLoginFallback(String name, String password, Throwable throwable) {
		return ResponseMessage.fail(ErrorCode.HYSTRIX_FALLBACK.getCode(), "执行登陆失败 " + throwable.getLocalizedMessage());
	}

	protected ResponseMessage<Boolean> doVerifyTokenFallback(String accessToken, Throwable throwable) {
		return ResponseMessage.fail(ErrorCode.HYSTRIX_FALLBACK.getCode(), "执行校验失败 " + throwable.getLocalizedMessage());
	}

	protected ResponseMessage<Token> doRefreshTokenFallback(String refreshToken, Throwable throwable) {
		return ResponseMessage.fail(ErrorCode.HYSTRIX_FALLBACK.getCode(), "执行刷新失败 " + throwable.getLocalizedMessage());
	}

	protected ResponseMessage<Boolean> doAddBlackListFallback(Long userId, Throwable throwable) {
		return ResponseMessage.fail(ErrorCode.HYSTRIX_FALLBACK.getCode(),
				"执行冻结用户失败 " + throwable.getLocalizedMessage());
	}

	protected ResponseMessage<Boolean> doRemoveBlackListFallback(Long userId, Throwable throwable) {
		return ResponseMessage.fail(ErrorCode.HYSTRIX_FALLBACK.getCode(),
				"执行解冻用户失败 " + throwable.getLocalizedMessage());
	}
}
