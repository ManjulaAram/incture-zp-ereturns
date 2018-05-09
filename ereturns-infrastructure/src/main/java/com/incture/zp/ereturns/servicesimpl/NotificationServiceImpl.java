package com.incture.zp.ereturns.servicesimpl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.incture.zp.ereturns.dto.UserDto;
import com.incture.zp.ereturns.services.NotificationService;
import com.incture.zp.ereturns.services.UserService;
import com.incture.zp.ereturns.utils.PushNotificationUtil;

@Service
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	UserService userService;

	@Override
	public String sendNotification(String userId) {

		UserDto userDto = userService.getUserById(userId);
		String messageBody = "A new Request for approval";
		String token = userDto.getMobileToken();
		String messageTitle = "Request Submitted in queue";
		PushNotificationUtil notifyUtil = new PushNotificationUtil();

		try {

			notifyUtil.sendNotification(messageTitle, token, messageBody);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "RQ00005";
	}
}
