package com.incture.zp.ereturns.services;

import com.incture.zp.ereturns.dto.RequestDto;
import com.incture.zp.ereturns.dto.ResponseDto;

public interface WorkflowTriggerService {

	public String triggerWorkflow(String data);

	public ResponseDto completeTask(RequestDto requestDto);
}
