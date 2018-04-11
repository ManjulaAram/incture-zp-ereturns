package com.incture.zp.ereturns.repositories;

import java.util.List;

import com.incture.zp.ereturns.dto.ResponseDto;
import com.incture.zp.ereturns.dto.StatusRequestDto;
import com.incture.zp.ereturns.dto.StatusResponseDto;
import com.incture.zp.ereturns.model.Request;

public interface RequestRepository {

	public ResponseDto addRequest(Request request);
	
	public Request getRequestById(String id);
	
	public List<StatusResponseDto> getStatusDetails(StatusRequestDto requestDto);
	
}
