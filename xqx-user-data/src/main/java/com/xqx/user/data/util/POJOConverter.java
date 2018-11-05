package com.xqx.user.data.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.xqx.base.pojo.dto.UserDTO;
import com.xqx.user.data.pojo.entity.UserDO;

public class POJOConverter {
	
	public static UserDO toUserDO(UserDTO user) {
		if(user == null) {
			throw new NullPointerException("user对象不能为空");
		}
		return new UserDO(user.getId(),user.getName(),user.getPassword(),user.getForbidden());
	}
	
	public static List<UserDO> toUserDOList(Collection<UserDTO> userList) {
		if(userList == null || userList.isEmpty()) {
			throw new NullPointerException("userList对象不能为空");
		}
		List<UserDO> userDOList = new ArrayList<>();
		userList.stream().forEach(u -> userDOList.add(toUserDO(u)));
		return userDOList;
	}

	public static UserDTO toUserDTO(UserDO user) {
		if(user == null) {
			throw new NullPointerException("user对象不能为空");
		}
		return new UserDTO(user.getId(),user.getName(),user.getPassword(),user.getForbidden());
	}
	
	public static List<UserDTO> toUserDTOList(Collection<UserDO> userList) {
		if(userList == null || userList.isEmpty()) {
			throw new NullPointerException("userList对象不能为空");
		}
		List<UserDTO> userDOList = new ArrayList<>();
		userList.stream().forEach(u -> userDOList.add(toUserDTO(u)));
		return userDOList;
	}
}
