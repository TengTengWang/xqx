package com.xqx.user.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.xqx.base.exception.ErrorCode;
import com.xqx.base.exception.ServiceException;
import com.xqx.base.vo.ResponseMessage;
import com.xqx.base.vo.UserInfo;
import com.xqx.user.entity.User;
import com.xqx.user.entity.util.EntityConverter;
import com.xqx.user.service.UserService;

@RestController
public class UserController {
	private static Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/ex")
	public String ex(int a) throws IOException {
    	// TODO Test, Should be delete
		if (System.currentTimeMillis() % 3 == 1) {
			System.out.println("runtime");
			throw new RuntimeException("取三余一RuntimeException");
		}    	
		if (System.currentTimeMillis() % 3 == 0) {
			try {
				System.out.println("超时180s");
				Thread.sleep(1000 * 60 * 180);
			} catch (InterruptedException e) {
			}
		}
		return "ok";
	}

	@RequestMapping(value = "/getUser", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage<UserInfo> getUser(@RequestParam("name") String name,
			@RequestParam("password") String password) {
		try {
			User user = userService.getUser(name, password);

			logger.info(user.toString());
			return ResponseMessage.success(EntityConverter.toUserInfo(user));
		} catch (ServiceException e) {
			return ResponseMessage.fail(e.getErrorCode().getCode(), e.getErrMsg());
		}
	}

	@RequestMapping(value = "/doForbidden")
	@ResponseBody
	public ResponseMessage<Boolean> doForbidden(@RequestParam("id") Long id) {
		try {
			User user = userService.doForbidden(id);
			if (user != null) {
				return ResponseMessage.success(true);
			} else {
				return ResponseMessage.fail(ErrorCode.SERVICE_ERROR.getCode(), "用户ID" + id + "设置黑名单失败");
			}
		} catch (ServiceException e) {
			return ResponseMessage.fail(e.getErrorCode().getCode(), e.getErrMsg());
		}
	}

	@RequestMapping(value = "/doUnforbidden")
	@ResponseBody
	public ResponseMessage<Boolean> doUnforbidden(@RequestParam("id") Long id) {
		try {
			User user = userService.doUnforbidden(id);
			if (user != null) {
				return ResponseMessage.success(true);
			} else {
				return ResponseMessage.fail(ErrorCode.SERVICE_ERROR.getCode(), "用户ID" + id + "设置黑名单失败");
			}
		} catch (ServiceException e) {
			return ResponseMessage.fail(e.getErrorCode().getCode(), e.getErrMsg());
		}
	}

	@RequestMapping(value = "/getUserByName")
	@ResponseBody
	public ResponseMessage<List<UserInfo>> getUserByName(@RequestParam("name") String name) {
		try {
			List<User> userList = userService.listUserByName(name);
			logger.info(userList.toString());
			return ResponseMessage.success(EntityConverter.toUserInfoList(userList));
		} catch (ServiceException e) {
			return ResponseMessage.fail(e.getErrorCode().getCode(), e.getErrMsg());
		}
	}

	@RequestMapping(value = "/getAllUser")
	@ResponseBody
	public ResponseMessage<List<UserInfo>> getAllUser() {


		try {
			List<User> userList = userService.listUser();
			logger.info(userList.toString());
			return ResponseMessage.success(EntityConverter.toUserInfoList(userList));
		} catch (ServiceException e) {
			return ResponseMessage.fail(e.getErrorCode().getCode(), e.getErrMsg());
		}
	}

}
