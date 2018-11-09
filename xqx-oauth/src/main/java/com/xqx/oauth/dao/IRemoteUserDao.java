package com.xqx.oauth.dao;

import com.xqx.base.exception.CallRemoteServiceException;
import com.xqx.base.pojo.dto.UserDTO;

/**
 * 远程访问dao接口
 */
public interface IRemoteUserDao {

	/**
	 * 根据用户名密码远程获取用户对象
	 * 
	 * @param name     用户名
	 * @param password 密码
	 * @return 用户对象实体UserDTO
	 * @throws CallRemoteServiceException
	 */
	public UserDTO findUserByNameAndPassword(String name, String password) throws CallRemoteServiceException;

	/**
	 * 远程将用户加入黑名单
	 * 
	 * @param userID 用户唯一标识
	 * @return 是否加入成功
	 * @throws CallRemoteServiceException
	 */
	public boolean addBlackList(Long userID) throws CallRemoteServiceException;

	/**
	 * 远程将用户移除黑名单
	 * 
	 * @param userID 用户唯一标识
	 * @return 是否移除成功
	 * @throws CallRemoteServiceException
	 */
	public boolean doUnforbiddenByUserId(Long userID) throws CallRemoteServiceException;

}
