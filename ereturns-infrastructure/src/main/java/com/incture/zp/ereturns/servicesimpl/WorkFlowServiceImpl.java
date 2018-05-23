package com.incture.zp.ereturns.servicesimpl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	@Override
	public WorkFlowDto getWorkFLowInstance(String requestId,String matCode) {
		
		return importExportUtil.exportWorkFlowDto(workflowRepository.getWorkFlowInstance(requestId,matCode));
	}

	@Override
	public void addWorkflowInstance(WorkFlowDto workFlowDto) {
	
		workflowRepository.addWorkflowInstance(importExportUtil.importWorkFlowDto(workFlowDto));
	}

	
	
}
