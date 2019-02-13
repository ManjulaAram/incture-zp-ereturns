package com.incture.zp.ereturns.repositories;

import java.util.List;

import com.incture.zp.ereturns.dto.RequestDto;
import com.incture.zp.ereturns.dto.ResponseDto;
import com.incture.zp.ereturns.dto.StatusRequestDto;
import com.incture.zp.ereturns.dto.StatusResponseDto;
import com.incture.zp.ereturns.dto.UpdateDto;
import com.incture.zp.ereturns.model.Request;

public interface RequestRepository {

	public ResponseDto addRequest(Request request) throws Exception;
	
	public Request getRequestById(String id);
	
	public List<StatusResponseDto> getStatusDetails(StatusRequestDto requestDto);
	
	public List<RequestDto> getAllRequests();
	
	public int updateEccReturnOrder(String eccStatus, String eccNo, String requestId);
	
	public String getRequestStatus(String requestId);
	
	public int updateRequestTrigger(UpdateDto updateDto);
	
	public String getRequestClient(String requestId);
	
}
