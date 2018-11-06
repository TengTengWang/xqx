package com.xqx.oauth.dao;

import com.xqx.base.exception.CallRemoteServiceException;
import com.xqx.base.pojo.dto.UserDTO;


public interface IRemoteUserDao {
	
	public UserDTO getUser(String name,String password) throws CallRemoteServiceException;
	
	public boolean doForbidden(Long userID) throws CallRemoteServiceException;
	
	public boolean doUnforbidden(Long userID) throws CallRemoteServiceException;

}
