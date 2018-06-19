package com.incture.zp.ereturns.servicesimpl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.incture.zp.ereturns.constants.EReturnConstants;
import com.incture.zp.ereturns.dto.ResponseDto;
import com.incture.zp.ereturns.dto.StatusRequestDto;
import com.incture.zp.ereturns.dto.UserDto;
import com.incture.zp.ereturns.services.NotificationService;
import com.incture.zp.ereturns.services.RequestService;
import com.incture.zp.ereturns.services.UserService;
import com.incture.zp.ereturns.utils.PushNotificationUtil;

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	UserService userService;
	
	@Autowired
	RequestService requestService;

	private static final Logger LOGGER = LoggerFactory.getLogger(NotificationServiceImpl.class);
	
	@Override
	public ResponseDto sendNotification(String requestId, String pendingWith, String createdBy) {

		ResponseDto responseDto = new ResponseDto();
		UserDto userDto = new UserDto();
		String token="";
		PushNotificationUtil notifyUtil = new PushNotificationUtil();
		LOGGER.error("Pending with for Push Notification:"+pendingWith);
		if(pendingWith != null && !(pendingWith.equalsIgnoreCase("")))
		{
			LOGGER.error("Created by Pending with for Push Notification2:"+pendingWith);
			List<String> userList = userService.getUsersByRole(pendingWith);
			try {
				StatusRequestDto statusRequestDto=new StatusRequestDto();
				Map<String, String> messageMap=new HashMap<String, String>();
				messageMap=BuildMessage(pendingWith, requestId);
				statusRequestDto.setRequestId(requestId);
				for(int i = 0 ; i < userList.size() ; i++) {
					String userId = userList.get(i);
					userDto = userService.getUserById(userId);
					token = userDto.getMobileToken();
					notifyUtil.sendNotification(messageMap.get("messageTitle"), token, messageMap.get("messageBody"),
							requestService.getStatusDetails(statusRequestDto));
				}
				responseDto.setCode(EReturnConstants.SUCCESS_STATUS_CODE);
				responseDto.setMessage("Notification sent");
				responseDto.setStatus(EReturnConstants.SUCCESS_STATUS);
			} catch (IOException e) {
				responseDto.setCode(EReturnConstants.ERROR_STATUS_CODE);
				responseDto.setMessage(e.getMessage());
				responseDto.setStatus(EReturnConstants.ERROR_STATUS);
			}
		}
		else
		{
			LOGGER.error("Created by Pending with for Push Notification:"+createdBy);
			userDto = userService.getUserById(createdBy);
			token = userDto.getMobileToken();
			try {
				StatusRequestDto statusRequestDto=new StatusRequestDto();
				Map<String, String> messageMap=new HashMap<String, String>();
				statusRequestDto.setRequestId(requestId);
				messageMap=BuildMessage(pendingWith, requestId);
				notifyUtil.sendNotification(messageMap.get("messageTitle"), token, messageMap.get("messageBody"),
						requestService.getStatusDetails(statusRequestDto));
				responseDto.setCode(EReturnConstants.SUCCESS_STATUS_CODE);
				responseDto.setMessage("Notification sent");
				responseDto.setStatus(EReturnConstants.SUCCESS_STATUS);
			} catch (IOException e) {
				responseDto.setCode(EReturnConstants.ERROR_STATUS_CODE);
				responseDto.setMessage(e.getMessage());
				responseDto.setStatus(EReturnConstants.ERROR_STATUS);
			}
		}
		return responseDto;
	}
	
	
	public Map<String,String> BuildMessage(String pendingWith, String requestId)
	{
		Map<String, String> message=new HashMap<String,String>();
		if(pendingWith != null && !(pendingWith.equals("")))
		{
			message.put("messageTitle","A new request for approval");
			message.put("messageBody",("Request "+requestId+"is pending for your approval" ));
		}
		else
		{
			message.put("messageTitle", "Request Approved");
			message.put("messageBody",("Your Request "+requestId+"is approved"));
		}
		return message;
	}
}
