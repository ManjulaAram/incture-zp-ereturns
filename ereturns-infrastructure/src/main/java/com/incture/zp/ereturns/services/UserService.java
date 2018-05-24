package com.incture.zp.ereturns.services;

import java.util.List;

import com.incture.zp.ereturns.dto.ResponseDto;
import com.incture.zp.ereturns.dto.RoleDto;
import com.incture.zp.ereturns.dto.UserDto;

public interface UserService {

	public ResponseDto addUser(UserDto userDto);
	
	public UserDto getUserById(String id);

	public List<RoleDto> getUserRole(String userId);
	
	public ResponseDto updateMobileToken(UserDto userDto);
}
