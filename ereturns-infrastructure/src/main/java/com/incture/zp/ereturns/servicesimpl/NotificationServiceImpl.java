package com.incture.zp.ereturns.servicesimpl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	public ResponseDto sendNotificationForApprover(String requestId, String pendingWith) {

		ResponseDto responseDto = new ResponseDto();
		PushNotificationUtil notifyUtil = new PushNotificationUtil();
		LOGGER.error("Pending with for Push Notification:"+pendingWith);
		if(pendingWith != null && !(pendingWith.equalsIgnoreCase("")))
		{
			try {
				StatusRequestDto statusRequestDto=new StatusRequestDto();
				Map<String, String> messageMap=new HashMap<String, String>();
				messageMap.put("messageTitle","A new request for approval");
				messageMap.put("messageBody",("Request "+requestId+" is pending for your approval" ));
				statusRequestDto.setRequestId(requestId);
				List<String> userList = userService.getUsersByRole(pendingWith);
				if(userList != null && userList.size() > 0) {
					for(int i = 0 ; i < userList.size() ; i++) {
						String userId = userList.get(i);
						UserDto userDto = userService.getUserById(userId);
						if(userDto != null) {
							if(userDto.getMobileToken() != null && !(userDto.getMobileToken().equals(""))) {
								String token = userDto.getMobileToken();
								notifyUtil.sendNotification(messageMap.get("messageTitle"), token, messageMap.get("messageBody"),
										requestService.getStatusDetails(statusRequestDto));
							}
						}
					}
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
		return responseDto;
	}
	
	@Override
	public ResponseDto sendNotificationForRequestor(String requestId, String createdBy, String action) {

		ResponseDto responseDto = new ResponseDto();
		PushNotificationUtil notifyUtil = new PushNotificationUtil();
	
			LOGGER.error("Created by Pending with for Push Notification:"+createdBy);
			UserDto userDto = userService.getUserDetailsById(createdBy);
			if(userDto != null && !(userDto.getMobileToken().equals(""))) {
				String token = userDto.getMobileToken();
				try {
					Map<String, String> messageMap=new HashMap<String, String>();
					if(action.equalsIgnoreCase("A")) {
						messageMap.put("messageTitle", "Request Approved");
						messageMap.put("messageBody",("Your Request "+requestId+" is approved"));
					} else if(action.equalsIgnoreCase("R")) {
						messageMap.put("messageTitle", "Request Rejected");
						messageMap.put("messageBody",("Your Request "+requestId+" is Rejected"));
					}
					notifyUtil.sendNotification(messageMap.get("messageTitle"), token, messageMap.get("messageBody"), null);
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
	
}
