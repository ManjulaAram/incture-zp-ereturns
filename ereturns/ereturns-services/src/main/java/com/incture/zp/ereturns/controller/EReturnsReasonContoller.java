package com.incture.zp.ereturns.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.incture.zp.ereturns.dto.ReasonDto;
import com.incture.zp.ereturns.services.ReasonService;

@RestController
@CrossOrigin
@ComponentScan("com.incture.zp.ereturns")
@RequestMapping(value = "/reason", produces = "application/json")
public class EReturnsReasonContoller {

	@Autowired
	ReasonService reasonService;

	@RequestMapping("/hello")
	public String hello() {
		return "Say Hello to Notification";
	}

	@ResponseBody
	@RequestMapping(value = "/getAllReasons")
	public List<ReasonDto> getAllReasons() {
		return reasonService.getAllReasons();
	}
	
	@ResponseBody
	@RequestMapping(value = "/getReasonByCountry/{country}", method = RequestMethod.GET)
	public List<ReasonDto> getReasonByCountry(@PathVariable(value = "country") String country) {
		return reasonService.getReasonByCountry(country);
	}
}
