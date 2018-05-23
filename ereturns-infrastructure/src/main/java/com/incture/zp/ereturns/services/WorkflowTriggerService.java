package com.incture.zp.ereturns.services;

import com.incture.zp.ereturns.dto.CompleteTaskRequestDto;
import com.incture.zp.ereturns.dto.ResponseDto;

public interface WorkflowTriggerService {

	public String triggerWorkflow(String data);

	public ResponseDto completeTask(CompleteTaskRequestDto requestDto);
}
