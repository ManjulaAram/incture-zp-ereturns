package com.incture.zp.ereturns.services;

import com.incture.zp.ereturns.dto.RequestHistoryDto;
import com.incture.zp.ereturns.dto.ResponseDto;
import com.incture.zp.ereturns.dto.RoleDto;
import com.incture.zp.ereturns.dto.StatusPendingDto;

public interface RequestHistoryService {

	public StatusPendingDto getStatusForApprover(RoleDto roleDto);
	
	public ResponseDto addRequestHistory(RequestHistoryDto requestHistoryDto);
}
