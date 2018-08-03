package com.incture.zp.ereturns.services;

import java.util.List;

import com.incture.zp.ereturns.dto.RequestHistoryDto;
import com.incture.zp.ereturns.dto.ResponseDto;
import com.incture.zp.ereturns.dto.RoleDto;
import com.incture.zp.ereturns.dto.StatusPendingDto;
import com.incture.zp.ereturns.dto.StatusResponseDto;

public interface RequestHistoryService {

	public StatusPendingDto getStatusForApprover(RoleDto roleDto);
	
	public ResponseDto addRequestHistory(RequestHistoryDto requestHistoryDto);
	
	public List<StatusResponseDto> getApproverDashboardList(RoleDto roleDto, String status);
}
