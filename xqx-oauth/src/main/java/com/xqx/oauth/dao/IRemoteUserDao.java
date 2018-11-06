package com.xqx.oauth.dao;

import com.xqx.base.exception.CallRemoteServiceException;
import com.xqx.base.pojo.dto.UserDTO;


public interface IRemoteUserDao {
	
	public UserDTO findUserByNameAndPassword(String name,String password) throws CallRemoteServiceException;
	
	public boolean doForbiddenByUserId(Long userID) throws CallRemoteServiceException;
	
	public boolean doUnforbiddenByUserId(Long userID) throws CallRemoteServiceException;

}
