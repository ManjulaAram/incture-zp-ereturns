package com.incture.zp.ereturns.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.incture.zp.ereturns.services.NotificationService;

@RestController
@CrossOrigin
@ComponentScan("com.incture.zp.ereturns")
@RequestMapping(value = "/notify", produces = "application/json")
public class EReturnsNotificationController {
	@Autowired
	NotificationService notifyService;

	@RequestMapping("/hello")
	public String hello() {
		return "Say Hello to Notification";
	}

	@ResponseBody
	@RequestMapping(path = "/sendNotification/{userId}", method = RequestMethod.GET)
	public String sendNotification(@PathVariable String userId) {
		
		return notifyService.sendNotification(userId);

	}
}
