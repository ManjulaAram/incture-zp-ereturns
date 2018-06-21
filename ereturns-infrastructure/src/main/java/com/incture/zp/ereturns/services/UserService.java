package com.incture.zp.ereturns.services;

import java.util.List;

import com.incture.zp.ereturns.dto.EmailDto;
import com.incture.zp.ereturns.dto.IdpUserIdDto;
import com.incture.zp.ereturns.dto.ResponseDto;
import com.incture.zp.ereturns.dto.UserDto;

public interface UserService {

	public ResponseDto addUser(UserDto userDto);
	
	public UserDto getUserById(String id);

	public EmailDto getEmailByRole(String role);
	
	public String getUserNameById(String userId);
	
	public IdpUserIdDto getUserIdByRole(String role);
	
	public ResponseDto loginUser();
	
	public List<String> getUsersByRole(String role);
}
