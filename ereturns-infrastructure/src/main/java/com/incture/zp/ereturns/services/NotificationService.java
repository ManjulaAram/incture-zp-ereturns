package com.incture.zp.ereturns.services;

import com.incture.zp.ereturns.dto.RequestDto;
import com.incture.zp.ereturns.dto.ResponseDto;

public interface NotificationService {


	public ResponseDto sendNotification(RequestDto requestDto);
}
