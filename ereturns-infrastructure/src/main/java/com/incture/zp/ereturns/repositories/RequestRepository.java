package com.incture.zp.ereturns.repositories;

import java.util.List;

import com.incture.zp.ereturns.dto.ReturnRequestDto;
import com.incture.zp.ereturns.dto.StatusRequestDto;
import com.incture.zp.ereturns.model.Request;

public interface RequestRepository {

	public void addRequest(Request request);
	
	public List<ReturnRequestDto> getStatusDetails(StatusRequestDto requestDto);
}
