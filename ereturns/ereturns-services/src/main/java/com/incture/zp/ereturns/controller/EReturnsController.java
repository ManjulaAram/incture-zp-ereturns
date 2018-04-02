package com.incture.zp.ereturns.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EReturnsController {

	@RequestMapping("/hello")
	public String welcome() {// Welcome page, non-rest
		return "Welcome to RestTemplate Example.";
	}
}
