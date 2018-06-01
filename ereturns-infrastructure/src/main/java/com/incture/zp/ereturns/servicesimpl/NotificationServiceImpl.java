package com.incture.zp.ereturns.servicesimpl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.incture.zp.ereturns.dto.RequestDto;
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

	@Override
	public ResponseDto sendNotification(RequestDto requestDto) {

		ResponseDto responseDto=new ResponseDto();
		UserDto userDto =new UserDto();
		String token="";
		PushNotificationUtil notifyUtil = new PushNotificationUtil();
		if(!(requestDto.getRequestPendingWith().equals(null)) && !(requestDto.getRequestPendingWith().equals("")))
		{
			userDto = userService.getUserById(requestDto.getRequestPendingWith());
			token = userDto.getMobileToken();
			try {
				
				StatusRequestDto statusRequestDto=new StatusRequestDto();
				Map<String, String> messageMap=new HashMap<String, String>();
				messageMap=BuildMessage(requestDto);
				statusRequestDto.setPendingWith(requestDto.getRequestPendingWith());
				statusRequestDto.setRequestId(requestDto.getRequestId());
				notifyUtil.sendNotification(messageMap.get("messageTitle"), token, messageMap.get("messageBody"),requestService.getStatusDetails(statusRequestDto));
				responseDto.setCode("200");
				responseDto.setMessage("Notification sent");
				responseDto.setStatus("SUCCESS");
			} catch (IOException e) {
				responseDto.setCode("01");
				responseDto.setMessage(e.getMessage());
				responseDto.setStatus("ERROR");
			}
		}
		else
		{
			userDto = userService.getUserById(requestDto.getRequestCreatedBy());
			token = userDto.getMobileToken();
			try {

				StatusRequestDto statusRequestDto=new StatusRequestDto();
				Map<String, String> messageMap=new HashMap<String, String>();
				statusRequestDto.setPendingWith(requestDto.getRequestPendingWith());
				statusRequestDto.setRequestId(requestDto.getRequestId());
				messageMap=BuildMessage(requestDto);
				notifyUtil.sendNotification(messageMap.get("messageTitle"), token, messageMap.get("messageBody"),requestService.getStatusDetails(statusRequestDto));
				responseDto.setCode("200");
				responseDto.setMessage("Notification sent");
				responseDto.setStatus("SUCCESS");
			} catch (IOException e) {
				responseDto.setCode("01");
				responseDto.setMessage(e.getMessage());
				responseDto.setStatus("ERROR");
			}
		}
		
		

		
		

		return responseDto;
	}
	
	
	public Map<String,String> BuildMessage(RequestDto requestDto)
	{
		Map<String, String> message=new HashMap<String,String>();
		if(!(requestDto.getRequestPendingWith().equals(null)) && !(requestDto.getRequestPendingWith().equals("")))
		{
			message.put("messageTitle","A new request for approval");
			message.put("messageBody",("Request "+requestDto.getRequestId()+"is pending for approval" ));
		}
		else
		{
			message.put("messageTitle", "Request Approved");
			message.put("messageBody",("Request "+requestDto.getRequestId()+"is approved"));
		}
		
		return message;
	}
}
