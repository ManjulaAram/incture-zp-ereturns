package com.incture.zp.ereturns.services;

import com.incture.zp.ereturns.dto.RequestDto;
import com.incture.zp.ereturns.dto.ResponseDto;
import com.incture.zp.ereturns.dto.StatusRequestDto;
import com.incture.zp.ereturns.dto.StatusResponseDto;

public interface RequestService {

	public ResponseDto addRequest(RequestDto requestDto);
	
	public RequestDto getRequestById(String id);
	
	public StatusResponseDto getStatusDetails(StatusRequestDto requestDto);
	
	public ResponseDto updateRequestStatus(RequestDto requestDto);

}
