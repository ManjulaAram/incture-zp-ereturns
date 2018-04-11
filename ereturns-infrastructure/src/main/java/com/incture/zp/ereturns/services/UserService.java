package com.incture.zp.ereturns.services;

import com.incture.zp.ereturns.dto.UserDto;

public interface UserService {

	public void addUser(UserDto userDto);
	
	public UserDto getUserById(String id);
}
