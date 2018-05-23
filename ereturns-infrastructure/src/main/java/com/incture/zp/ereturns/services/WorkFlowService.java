package com.incture.zp.ereturns.services;

import com.incture.zp.ereturns.dto.WorkFlowDto;

public interface WorkFlowService {

	public WorkFlowDto getWorkFLowInstance(String requestId, String matCode);

	public void addWorkflowInstance(WorkFlowDto workFlowDto);

}
