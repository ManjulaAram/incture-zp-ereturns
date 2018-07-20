package com.incture.zp.ereturns.repositories;

import java.util.List;

import com.incture.zp.ereturns.dto.RequestHistoryDto;
import com.incture.zp.ereturns.dto.ResponseDto;
import com.incture.zp.ereturns.model.RequestHistory;

public interface RequestHistoryRepository {

	public List<RequestHistoryDto> getRequestHistory(String requestId, String itemCode);
	
	public List<RequestHistoryDto> getApprovedBy(String user);
	
	public ResponseDto addRequestHistory(RequestHistory requestHistory);
}
