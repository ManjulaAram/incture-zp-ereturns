package com.incture.zp.ereturns.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.incture.zp.ereturns.dto.RequestDto;
import com.incture.zp.ereturns.dto.ResponseDto;
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
	@RequestMapping(path = "/sendNotificationForApprover", method = RequestMethod.POST)
	public ResponseDto sendNotificationForApprover(@RequestBody RequestDto requestDto) {
		return notifyService.sendNotificationForApprover(requestDto.getRequestId(), requestDto.getRequestPendingWith());

	}
	
	@ResponseBody
	@RequestMapping(path = "/sendNotificationForRequestor", method = RequestMethod.POST)
	public ResponseDto sendNotificationForRequestor(@RequestBody RequestDto requestDto) {
		return notifyService.sendNotificationForRequestor(requestDto.getRequestId(), requestDto.getRequestCreatedBy(), "");

	}
}
