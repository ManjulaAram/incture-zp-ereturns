package com.incture.zp.ereturns.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incture.zp.ereturns.dto.ResponseDto;
import com.incture.zp.ereturns.dto.UserNameDto;
import com.incture.zp.ereturns.repositories.UserNameRepository;
import com.incture.zp.ereturns.services.UserNameService;
import com.incture.zp.ereturns.utils.ImportExportUtil;

@Service
@Transactional
public class UserNameServiceImpl implements UserNameService {

	@Autowired
	UserNameRepository userNameRepository;
	
	@Autowired
	ImportExportUtil importExportUtil;
	
	@Override
	public ResponseDto addUserName(UserNameDto userNameDto) {
		return userNameRepository.addUserName(importExportUtil.importUserNameDto(userNameDto));
	}

	@Override
	public boolean isUserExist(String userId) {
		return userNameRepository.isUserExist(userId);
	}

}
