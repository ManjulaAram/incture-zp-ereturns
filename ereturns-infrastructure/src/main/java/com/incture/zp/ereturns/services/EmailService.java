package com.incture.zp.ereturns.services;

import com.incture.zp.ereturns.dto.EmailRequestDto;
import com.incture.zp.ereturns.dto.EmailResponseDto;

public interface EmailService {

	 public String sendMail(String payload, String toemailIds, String subject);
	 
	 public EmailResponseDto triggerEmail(EmailRequestDto emailRequestDto);
}
