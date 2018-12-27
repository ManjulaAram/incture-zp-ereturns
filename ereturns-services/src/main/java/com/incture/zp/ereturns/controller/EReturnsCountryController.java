package com.incture.zp.ereturns.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.incture.zp.ereturns.dto.CountryDto;
import com.incture.zp.ereturns.services.CountryService;

@RestController
@CrossOrigin
@ComponentScan("com.incture.zp.ereturns")
@RequestMapping(value = "/country", produces = "application/json")
public class EReturnsCountryController {
	
		@Autowired
		CountryService countryService;

		@RequestMapping("/hello")
		public String hello() {
			return "Say Hello to Notification";
		}

		@ResponseBody
		@RequestMapping(value = "/getAllCountries")
		public List<CountryDto> getAllCountries() {
			return countryService.getCountries();
		}

}
