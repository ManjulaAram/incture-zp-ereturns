package com.incture.zp.ereturns.services;

import com.incture.zp.ereturns.dto.ReturnUserDto;
import com.incture.zp.ereturns.dto.UserDto;

public interface UserService {

	public ReturnUserDto addUser(UserDto userDto);
	
	public ReturnUserDto getUserById(String id);
}
