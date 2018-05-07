package com.incture.zp.ereturns.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.incture.zp.ereturns.dto.UserDto;
import com.incture.zp.ereturns.model.Role;
import com.incture.zp.ereturns.services.UserService;

@RestController
@CrossOrigin
@ComponentScan("com.incture.zp.ereturns")
@RequestMapping(value = "/user", produces = "application/json")
public class EReturnsUserController {

	@Autowired
	UserService userService;

	@RequestMapping("/hello")
	public String helloWorld()
	{
		return "HELLo GET ROLES";
	}
	
	@RequestMapping("/getUserRole/{userId}")
	public List<Role> getRole(@RequestBody @PathVariable String userId) {
		
		return userService.getUserRole(userId);
		
	}
	@RequestMapping("/getUser/{userId}")
	public UserDto getUser(@RequestBody @PathVariable String userId) {
		
		return userService.getUserById(userId);
		
	}
}
