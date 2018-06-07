package com.incture.zp.ereturns.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.incture.zp.ereturns.dto.ResponseDto;
import com.incture.zp.ereturns.dto.RoleDto;
import com.incture.zp.ereturns.dto.UserDto;
import com.incture.zp.ereturns.dto.UserMailDto;
import com.incture.zp.ereturns.services.UserService;

@RestController
@CrossOrigin
@ComponentScan("com.incture.zp.ereturns")
@RequestMapping(value = "/user", produces = "application/json")
public class EReturnsUserController {

	@Autowired
	UserService userService;

	@RequestMapping("/getUserRole/{userId}")
	public List<RoleDto> getRole(@RequestBody @PathVariable String userId) {
		return userService.getUserRole(userId);
	}
	@RequestMapping("/getUser/{userId}")
	public UserDto getUser(@RequestBody @PathVariable String userId) {
		return userService.getUserById(userId);
	}
	
	@RequestMapping(path="/addUser",consumes="application/json",method=RequestMethod.POST)
	public ResponseDto addUser(@RequestBody UserDto userDto)
	{
		return userService.addUser(userDto);
	}
	
	@RequestMapping(path="/updateMobileToken",consumes="application/json",method=RequestMethod.POST)
	public ResponseDto updateMobileToken(@RequestBody UserDto userDto)
	{
		return userService.updateMobileToken(userDto);
	}
	
	@RequestMapping("/getMail/{role}")
	public UserMailDto getMailIds(@PathVariable String role)
	{
		return userService.GetMailIdByRole(role);
	}

}
