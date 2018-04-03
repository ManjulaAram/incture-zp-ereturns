package com.incture.zp.ereturns.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.incture.zp.ereturns.dto.ReturnUserDto;
import com.incture.zp.ereturns.dto.UserDto;
import com.incture.zp.ereturns.services.UserService;

@RestController
public class EReturnsController {

	@Autowired
	UserService userService;
	
	@RequestMapping("/hello")
	public String welcome() {// Welcome page, non-rest
		return "Welcome to RestTemplate Example.";
	}
	
	@RequestMapping(value = "/addUser", method = RequestMethod.POST, consumes = { "application/JSON" }, produces = { "application/json" })
	@ResponseBody
	public ReturnUserDto addUser(@RequestBody UserDto userDto) {
		return userService.addUser(userDto);
	}
}
