package com.xqx.user.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.xqx.base.exception.ServiceException;
import com.xqx.base.vo.ResponseMessage;
import com.xqx.user.entity.User;
import com.xqx.user.service.UserService;


@RestController
public class UserController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/getUser")
    @ResponseBody
    public ResponseMessage<User> getUser(@RequestParam("name")String name,@RequestParam("password")String password){
    	User user;
		try {
			user = userService.getUser(name, password);
			logger.info(user.toString());
			return ResponseMessage.success(user);
		} catch (ServiceException e) {
			return ResponseMessage.fail(e.getErrorCode().getCode(), e.getErrMsg());
		}
    }
    
    @RequestMapping(value = "/getUserByName")
    @ResponseBody
    public ResponseMessage<List<User>> getUserByName(@RequestParam("name")String name){
    	List<User> userList;
		try {
			userList = userService.listUserByName(name);
			logger.info(userList.toString());
			return ResponseMessage.success(userList);
		} catch (ServiceException e) {
			return ResponseMessage.fail(e.getErrorCode().getCode(), e.getErrMsg());
		}
    }
    
    @RequestMapping(value = "/getAllUser")
    @ResponseBody
    public ResponseMessage<List<User>> getAllUser(){
    	List<User> userList;
		try {
			userList = userService.listUser();
			logger.info(userList.toString());
			return ResponseMessage.success(userList);
		} catch (ServiceException e) {
			return ResponseMessage.fail(e.getErrorCode().getCode(), e.getErrMsg());
		}
    }

}
