package com.incture.zp.ereturns.services;

import com.incture.zp.ereturns.dto.ResponseDto;

public interface NotificationService {

	public ResponseDto sendNotificationForApprover(String requestId, String pendingWith);
	
	public ResponseDto sendNotificationForRequestor(String requestId, String createdBy, String action);
	
}
