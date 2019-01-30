package com.incture.zp.ereturns.repositories;

import com.incture.zp.ereturns.dto.ResponseDto;
import com.incture.zp.ereturns.dto.UserNameDto;
import com.incture.zp.ereturns.model.UserName;

public interface UserNameRepository {

	public ResponseDto addUserName(UserName userName);
	
	public boolean isUserExist(String userId);
	
	public UserNameDto getUserById(String userId);
}
