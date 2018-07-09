package com.incture.zp.ereturns.servicesimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incture.zp.ereturns.dto.ResponseDto;
import com.incture.zp.ereturns.dto.WorkFlowDto;
import com.incture.zp.ereturns.repositories.WorkflowRepository;
import com.incture.zp.ereturns.services.WorkFlowService;
import com.incture.zp.ereturns.utils.ImportExportUtil;

@Service
@Transactional
public class WorkFlowServiceImpl implements WorkFlowService {

	@Autowired
	ImportExportUtil importExportUtil;
	
	@Autowired
	WorkflowRepository workflowRepository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WorkFlowServiceImpl.class);
	
	@Override
	public WorkFlowDto getWorkFLowInstance(String requestId,String matCode) {
		LOGGER.error("RequestID:"+requestId+"..."+matCode);
		return workflowRepository.getWorkFlowInstance(requestId, matCode);
	}

	@Override
	public void addWorkflowInstance(WorkFlowDto workFlowDto) {
	
		workflowRepository.addWorkflowInstance(importExportUtil.importWorkFlowDto(workFlowDto));
	}

	@Override
	public ResponseDto deleteWorkflow(String requestId) {
		return workflowRepository.deleteWorkflow(requestId);
	}

	
	
}
