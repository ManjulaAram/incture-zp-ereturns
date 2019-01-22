package com.incture.zp.ereturns.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.incture.zp.ereturns.dto.ResponseDto;
import com.incture.zp.ereturns.dto.UserDto;
import com.incture.zp.ereturns.services.HeaderService;
import com.incture.zp.ereturns.services.UserService;

@RestController
@CrossOrigin
@ComponentScan("com.incture.zp.ereturns")
@RequestMapping(value = "/Header", produces = "application/json")
public class EReturnsHeaderController {

	@Autowired
	UserService userService;
	
	@Autowired 
	HeaderService headerService;

	@RequestMapping(value = "/addUser", method = RequestMethod.POST, consumes = { "application/json" })
	@ResponseBody
	public ResponseDto addUser(@RequestBody UserDto userDto) {
		return userService.addUser(userDto);
	}
	
}
