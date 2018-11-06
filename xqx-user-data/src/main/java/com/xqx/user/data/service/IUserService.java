package com.xqx.user.data.service;

import java.util.List;

import com.xqx.base.exception.ServiceException;
import com.xqx.base.pojo.dto.UserDTO;

/**
 * 逻辑层接口
 * @author teng
 *
 */
public interface IUserService {

	/**
	 * 保存
	 * @param user
	 * @return
	 * @throws ServiceException
	 */
	UserDTO saveUser(UserDTO user) throws ServiceException;

	/**
	 * 删除
	 * @param id
	 * @throws ServiceException
	 */
    void removeUserById(Long id) throws ServiceException;

    /**
     * 获取单独对象
     * @param id
     * @return
     * @throws ServiceException
     */
    UserDTO getUserByID(Long id) throws ServiceException;

    /**
     * 查询用户
     * @param name
     * @param password
     * @return
     * @throws ServiceException
     */
    UserDTO getUserByNameAndPassword(String name,String password) throws ServiceException;

    /**
     * 获取所有用户
     * @return
     * @throws ServiceException
     */
    List<UserDTO> listAllUser() throws ServiceException;

    /**
     * 统计用户数量
     * @param name
     * @return
     * @throws ServiceException
     */
    Long countUserByName(String name) throws ServiceException;
    
    /**
     * 冻结用户
     * @param id
     * @return
     * @throws ServiceException
     */
    UserDTO doForbiddenById(Long id)throws ServiceException; 
    
    /**
     * 解冻用户
     * @param id
     * @return
     * @throws ServiceException
     */
    UserDTO doUnforbiddenById(Long id)throws ServiceException; 
}
