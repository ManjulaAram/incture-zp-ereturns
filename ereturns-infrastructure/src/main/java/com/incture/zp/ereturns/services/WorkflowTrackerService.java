package com.incture.zp.ereturns.services;

import com.incture.zp.ereturns.dto.CompleteTaskRequestDto;
import com.incture.zp.ereturns.dto.WorkflowInstanceDto;

public interface WorkflowTrackerService {

	WorkflowInstanceDto getTaskDetails(CompleteTaskRequestDto completeTaskRequestDto);
}
