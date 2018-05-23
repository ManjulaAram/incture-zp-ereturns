package com.incture.zp.ereturns.services;

import com.incture.zp.ereturns.dto.RequestDto;
import com.incture.zp.ereturns.dto.WorkflowInstanceDto;

public interface WorkflowTrackerService {


	WorkflowInstanceDto getTaskDetails(RequestDto requestDto);
}
