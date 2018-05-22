package com.incture.zp.ereturns.services;

import java.util.List;

import com.incture.zp.ereturns.dto.ResponseDto;
import com.incture.zp.ereturns.dto.UserDto;
import com.incture.zp.ereturns.model.Role;

public interface UserService {

	public ResponseDto addUser(UserDto userDto);
	
	public UserDto getUserById(String id);

	public List<Role> getUserRole(String userId);
	
	public ResponseDto updateUser(UserDto userDto);
}
