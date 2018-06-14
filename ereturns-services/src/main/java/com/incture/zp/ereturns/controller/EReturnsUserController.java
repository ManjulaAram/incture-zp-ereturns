package com.incture.zp.ereturns.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.incture.zp.ereturns.dto.EmailDto;
import com.incture.zp.ereturns.dto.IdpUserIdDto;
import com.incture.zp.ereturns.dto.LoginDto;
import com.incture.zp.ereturns.dto.ResponseDto;
import com.incture.zp.ereturns.dto.UserDto;
import com.incture.zp.ereturns.services.UserService;

@RestController
@CrossOrigin
@ComponentScan("com.incture.zp.ereturns")
@RequestMapping(value = "/user", produces = "application/json")
public class EReturnsUserController {

	@Autowired
	UserService userService;

	@RequestMapping("/getUser/{userId}")
	public UserDto getUser(@RequestBody @PathVariable String userId) {
		return userService.getUserById(userId);
	}
	
	@RequestMapping(path="/addUser",consumes="application/json",method=RequestMethod.POST)
	public ResponseDto addUser(@RequestBody UserDto userDto)
	{
		return userService.addUser(userDto);
	}
	
	@RequestMapping("/getMail/{role}")
	public EmailDto getMailIds(@PathVariable String role)
	{
		return userService.getEmailByRole(role);
	}

	@RequestMapping("/getUserIdByRole/{role}")
	public IdpUserIdDto getUserIdByRole(@PathVariable String role)
	{
		return userService.getUserIdByRole(role);
	}
	
	@RequestMapping(path="/loginUser",consumes="application/json",method=RequestMethod.POST)
	public ResponseDto loginUser(@RequestBody LoginDto loginDto)
	{
		return userService.loginUser(loginDto);
	}
}
