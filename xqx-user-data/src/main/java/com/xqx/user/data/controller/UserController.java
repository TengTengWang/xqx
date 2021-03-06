package com.xqx.user.data.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xqx.base.exception.ServiceException;
import com.xqx.base.pojo.dto.UserDTO;
import com.xqx.base.vo.ResponseMessage;
import com.xqx.user.data.dao.IUserRepository;
import com.xqx.user.data.pojo.entity.UserDO;
import com.xqx.user.data.service.IUserService;

/**
 * 用户信息操作
 */
@RestController
public class UserController {
	private static Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private IUserService userService;

	@Autowired
	private IUserRepository userRepostory;

	/**
	 * 根据用户名密码查询用户
	 * 
	 * @param name     用户名
	 * @param password 密码
	 * @return 包含用户信息的实体
	 */
	@PostMapping(value = "/findUserByNameAndPassword")
	public ResponseMessage<UserDTO> findUserByNameAndPassword(@RequestParam("name") String name,
			@RequestParam("password") String password) {
		logger.info("请求查询用户name={}的数据");
		try {
			UserDTO user = userService.getUserByNameAndPassword(name, password);

			logger.info(user.toString());
			return ResponseMessage.success(user);
		} catch (ServiceException e) {
			return ResponseMessage.fail(e.getErrorCode().getCode(), e.getErrMsg());
		}
	}

	/**
	 * 根据用户名查询用户
	 * 
	 * @param name 用户名
	 * @return 包含用户信息的实体
	 */
	@PostMapping(value = "/findByName")
	public ResponseMessage<List<UserDO>> findByName(@RequestParam("name") String name) {
		logger.info("请求查询用户name={}的数据");

		List<UserDO> users = userRepostory.findByName(name);
		List<UserDO> users1 = userRepostory.findByName1("tengshao");
		List<UserDO> users2 = userRepostory.findByName2("tengshao");

		logger.info(users1.toString());
		logger.info(users2.toString());
		return ResponseMessage.success(users);
	}

	/**
	 * 获取所有用户信息
	 * 
	 * @return 包含用户传输实体信息的实体
	 */
	@PostMapping(value = "/listAllUser")
	public ResponseMessage<List<UserDTO>> listAllUser() {
		List<UserDTO> userList = userService.listAllUser();
		logger.info(userList.toString());
		return ResponseMessage.success(userList);
	}

}
