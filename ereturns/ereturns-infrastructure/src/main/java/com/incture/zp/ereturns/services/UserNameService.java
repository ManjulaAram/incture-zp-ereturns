package com.incture.zp.ereturns.services;

import com.incture.zp.ereturns.dto.ResponseDto;
import com.incture.zp.ereturns.dto.UserNameDto;

public interface UserNameService {

	public ResponseDto addUserName(UserNameDto userNameDto);
	
	public boolean isUserExist(String userId);
}
