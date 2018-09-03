package com.xqx.user.service;

import java.util.List;

import com.xqx.base.exception.ServiceException;
import com.xqx.user.entity.User;

public interface UserService {

    boolean saveUser(User user) throws ServiceException;

    boolean deleteUser(Long id) throws ServiceException;

    User getUserByID(Long id) throws ServiceException;

    User getUser(String name,String password) throws ServiceException;

    List<User> listUserByName(String name) throws ServiceException;

    List<User> listUser() throws ServiceException;

    Long getCount(String name) throws ServiceException;
}
