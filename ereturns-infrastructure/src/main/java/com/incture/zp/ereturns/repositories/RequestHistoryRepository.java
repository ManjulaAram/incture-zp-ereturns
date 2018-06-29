package com.incture.zp.ereturns.repositories;

import java.util.List;

import com.incture.zp.ereturns.dto.RequestHistoryDto;

public interface RequestHistoryRepository {

	public List<RequestHistoryDto> getRequestHistory(String requestId);
	
}
