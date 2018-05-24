package com.incture.zp.ereturns.servicesimpl;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incture.zp.ereturns.constants.EReturnsWorkflowConstants;
import com.incture.zp.ereturns.dto.ApproverDto;
import com.incture.zp.ereturns.dto.CompleteTaskRequestDto;
import com.incture.zp.ereturns.dto.WorkFlowDto;
import com.incture.zp.ereturns.dto.WorkflowInstanceDto;
import com.incture.zp.ereturns.services.ReturnOrderService;
import com.incture.zp.ereturns.services.UserService;
import com.incture.zp.ereturns.services.WorkFlowService;
import com.incture.zp.ereturns.services.WorkflowTrackerService;
import com.incture.zp.ereturns.utils.RestInvoker;

@Service
@Transactional
public class WorkflowTrackerServiceImpl implements WorkflowTrackerService {


	@Autowired
	WorkFlowService workflowService;

	@Autowired
	UserService userService;

	@Autowired
	ReturnOrderService returnOrderService;
	private static final Logger LOGGER = LoggerFactory.getLogger(WorkflowTrackerService.class);

	// Tracking
	@Override
	public WorkflowInstanceDto getTaskDetails(CompleteTaskRequestDto completeTaskRequestDto) {
		String url = EReturnsWorkflowConstants.WORKFLOW_REST_API;
		String username = EReturnsWorkflowConstants.WF_INITIATOR_USER_NAME;
		String password = EReturnsWorkflowConstants.WF_INITIATOR_PASSWORD;

		WorkflowInstanceDto instanceDto = new WorkflowInstanceDto();
		String taskInstanceId = "";
		WorkFlowDto workflowDto = new WorkFlowDto();
		workflowDto = workflowService.getWorkFLowInstance(completeTaskRequestDto.getRequestId(),
				completeTaskRequestDto.getItemCode());
		String workflowInstanceId = workflowDto.getWorkFlowInstanceId();
		RestInvoker restInvoker = new RestInvoker(url, username, password);

		String response = restInvoker.getData("v1/workflow-instances/" + workflowInstanceId + "/execution-logs");

		List<String> recipientList = new ArrayList<>();
		List<ApproverDto> approverList = new ArrayList<>();
		JSONArray executionLogs = new JSONArray(response);

		JSONArray receipents = new JSONArray();
		for (int i = 0; i < executionLogs.length(); i++) {
			JSONObject logObject = executionLogs.getJSONObject(i);
			if (logObject.get("type").equals("WORKFLOW_STARTED")) {
				instanceDto.setCreatedBy(userService.getUserById(logObject.get("userId").toString()).getUserName());
				instanceDto.setCreatedAt(formatDateString(logObject.get("timestamp").toString()));
			}
			if (logObject.get("type").equals("USERTASK_CREATED")) {
				taskInstanceId = logObject.get("taskId").toString();
				receipents = logObject.getJSONArray("recipientUsers");
			}

			if (logObject.get("type").equals("USERTASK_COMPLETED")
					&& logObject.get("taskId").toString().equals(taskInstanceId)) {
				ApproverDto approverDto = new ApproverDto();

				approverDto.setApproverName(userService.getUserById(logObject.get("userId").toString()).getUserName());
				approverDto.setApprovalDate(formatDateString(logObject.get("timestamp").toString()));
				approverDto.setStatus(getTaskStatus(logObject.get("taskId").toString()));
				approverList.add(approverDto);
			}
			if (logObject.get("type").equals("WORKFLOW_COMPLETED")) {
				instanceDto.setCompletedAt(formatDateString(logObject.get("timestamp").toString()));
				instanceDto.setStatus(EReturnsWorkflowConstants.COMPLETED);
			} else {
				instanceDto.setStatus(EReturnsWorkflowConstants.IN_PROGRESS);
			}

		}
		if (instanceDto.getStatus().equals("COMPLETED")) {
			recipientList.clear();
		} else {
			for (int j = 0; j < receipents.length(); j++) {
				recipientList.add(receipents.get(j).toString());
			}
		}
		instanceDto.setRequestId(workflowDto.getRequestId());
		instanceDto.setMaterialCode(workflowDto.getMaterialCode());
		instanceDto.setApproverList(approverList);
		instanceDto.setReceipents(recipientList);
		System.out.println(instanceDto.getCreatedBy());
		System.out.println(instanceDto.getCreatedAt());

		return instanceDto;
	}
	
	private String getTaskStatus(String taskId)
	{
		String url = EReturnsWorkflowConstants.WORKFLOW_REST_API;
		String username = EReturnsWorkflowConstants.WF_INITIATOR_USER_NAME;
		String password = EReturnsWorkflowConstants.WF_INITIATOR_PASSWORD;
		String status="";
		RestInvoker restInvoker = new RestInvoker(url, username, password);
		String response = restInvoker.getData("v1/task-instances/" + taskId + "/context");
		
		JSONObject responseObject=new JSONObject(response);
		
		if(responseObject.get("Action").equals("A"))
		{
			status= "Approved";
			
		}
		else if(responseObject.get("Action").equals("R"))
		{
			status="Rejected";
		}
return status;
	}

	private String formatDateString(String date) {
		String output = null;
		String[] strArr = date.split("T");

		output = (strArr[0] + " " + strArr[1].substring(0, strArr[1].length() - 1));
		LOGGER.error(output.toString());

		return output;
	}

}
