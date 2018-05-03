package com.incture.zp.ereturns.repositories;

import com.incture.zp.ereturns.dto.ResponseDto;
import com.incture.zp.ereturns.dto.UserDto;
import com.incture.zp.ereturns.model.User;

public interface UserRepository {

	public ResponseDto addUser(UserDto userDto);
	
	
	public ResponseDto delete(UserDto userDto);
	
	public UserDto getUserById(String id);
	
}
