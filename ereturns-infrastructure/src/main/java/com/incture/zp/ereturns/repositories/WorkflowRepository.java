package com.incture.zp.ereturns.repositories;

import com.incture.zp.ereturns.dto.ResponseDto;
import com.incture.zp.ereturns.model.WorkFlow;

public interface WorkflowRepository {

	public ResponseDto addWorkflowInstance(WorkFlow workFlow);

	public WorkFlow getWorkFlowInstance(String requestId,String matCode);

	public ResponseDto deleteWorkflow(String requestId);
}
