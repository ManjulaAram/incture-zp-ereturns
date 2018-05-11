package com.incture.zp.ereturns.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.incture.zp.ereturns.dto.ReturnReasonDto;
import com.incture.zp.ereturns.services.ReturnReasonService;

@RestController
@CrossOrigin
@ComponentScan("com.incture.zp.ereturns")
@RequestMapping(value = "/reason", produces = "application/json")
public class EReturnsReasonContoller {

	@Autowired
	ReturnReasonService reasonService;

	@RequestMapping("/hello")
	public String hello() {
		return "Say Hello to Notification";
	}

	@ResponseBody
	@RequestMapping(value = "/getAllReasons")
	public List<ReturnReasonDto> getAllReasons() {
		return reasonService.getAllReasons();
	}
}
