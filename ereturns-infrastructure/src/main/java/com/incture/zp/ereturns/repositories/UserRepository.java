package com.incture.zp.ereturns.repositories;

import com.incture.zp.ereturns.dto.ResponseDto;
import com.incture.zp.ereturns.model.User;

public interface UserRepository {

	public ResponseDto addUser(User user);
	
	public ResponseDto updateUser(User user);
	
	public ResponseDto deleteUser(String id);
	
	public User getUserById(String id);
	
}
