package com.incture.zp.ereturns.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incture.zp.ereturns.dto.ResponseDto;
import com.incture.zp.ereturns.dto.UserDto;
import com.incture.zp.ereturns.model.User;
import com.incture.zp.ereturns.repositories.UserRepository;
import com.incture.zp.ereturns.services.UserService;
import com.incture.zp.ereturns.utils.ImportExportUtil;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired 
	UserRepository userRepository;
	
	@Autowired
	ImportExportUtil importExportUtil;

	@Override
	public ResponseDto addUser(UserDto userDto) {
		return userRepository.addUser(importExportUtil.importUserDto(userDto));
	}
	
	@Override
	public UserDto getUserById(String id) {
		User user = userRepository.getUserById(id);
		UserDto userDto = importExportUtil.exportUserDto(user);
		return userDto;
	}
	
}
