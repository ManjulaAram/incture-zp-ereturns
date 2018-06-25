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
import com.incture.zp.ereturns.dto.RequestDto;
import com.incture.zp.ereturns.dto.ReturnOrderDto;
import com.incture.zp.ereturns.dto.WorkFlowDto;
import com.incture.zp.ereturns.dto.WorkflowInstanceDto;
import com.incture.zp.ereturns.repositories.ReturnOrderRepository;
import com.incture.zp.ereturns.services.RequestService;
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
	RequestService requestService;
	
	@Autowired
	ReturnOrderRepository returnOrderRepository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WorkflowTrackerService.class);
	
	String destination;
	String user;
	String pwd;
	
	public WorkflowTrackerServiceImpl() {
//		DestinationConfiguration destConfiguration = ServiceUtil.getDest(EReturnsWorkflowConstants.WORKFLOW_DESTINATION);
//		destination = destConfiguration.getProperty(EReturnsWorkflowConstants.WORKFLOW_DESTINATION_URL);
//		user = destConfiguration.getProperty(EReturnsWorkflowConstants.WORKFLOW_DESTINATION_USER);
//		pwd = destConfiguration.getProperty(EReturnsWorkflowConstants.WORKFLOW_DESTINATION_PWD);
		
		destination = EReturnsWorkflowConstants.WORKFLOW_DESTINATION_URL;
		user = EReturnsWorkflowConstants.WORKFLOW_DESTINATION_USER;
		pwd = EReturnsWorkflowConstants.WORKFLOW_DESTINATION_PWD;
	}
	
	// Tracking
	@Override
	public WorkflowInstanceDto getTaskDetails(CompleteTaskRequestDto completeTaskRequestDto) {
		
		String url = destination+EReturnsWorkflowConstants.WORKFLOW_REST_API;

		RequestDto requestDto = requestService.getRequestById(completeTaskRequestDto.getRequestId());
		
		WorkflowInstanceDto instanceDto = new WorkflowInstanceDto();
		String taskInstanceId = "";
		LOGGER.error("Request id and Item coming from ui:"+completeTaskRequestDto.getRequestId()+"..."+completeTaskRequestDto.getItemCode());
		WorkFlowDto workflowDto = workflowService.getWorkFLowInstance(completeTaskRequestDto.getRequestId(),
				completeTaskRequestDto.getItemCode());
		String workflowInstanceId = workflowDto.getWorkFlowInstanceId();
		RestInvoker restInvoker = new RestInvoker(url, user, pwd);

		String response = restInvoker.getData("v1/workflow-instances/" + workflowInstanceId + "/execution-logs");
		LOGGER.error("From Work instances execution - logs:"+response);
		List<String> recipientList = new ArrayList<>();
		List<ApproverDto> approverList = new ArrayList<>();
		JSONArray executionLogs = new JSONArray(response);

		JSONArray receipents = new JSONArray();
		ApproverDto approverDto = null;
		for (int i = 0; i < executionLogs.length(); i++) {
			JSONObject logObject = executionLogs.getJSONObject(i);
			if (logObject.get(EReturnsWorkflowConstants.TYPE).equals("WORKFLOW_STARTED")) {
				instanceDto.setCreatedBy(userService.getUserNameById(requestDto.getRequestCreatedBy()));
				instanceDto.setCreatedAt(formatDateString(logObject.get(EReturnsWorkflowConstants.TIMESTAMP).toString()));
			}
			if (logObject.get(EReturnsWorkflowConstants.TYPE).equals("USERTASK_CREATED")) {
				taskInstanceId = logObject.get(EReturnsWorkflowConstants.TASK_ID).toString();
				receipents = logObject.getJSONArray("recipientUsers");
			}

			if (logObject.get(EReturnsWorkflowConstants.TYPE).equals("USERTASK_COMPLETED")
					&& logObject.get(EReturnsWorkflowConstants.TASK_ID).toString().equals(taskInstanceId)) {
				approverDto = new ApproverDto();

				approverDto.setApproverName(userService.getUserNameById(logObject.get(EReturnsWorkflowConstants.USER_ID).toString()));
				approverDto.setApprovalDate(formatDateString(logObject.get(EReturnsWorkflowConstants.TIMESTAMP).toString()));
				approverDto.setStatus(getTaskStatus(logObject.get(EReturnsWorkflowConstants.TASK_ID).toString()));
				approverList.add(approverDto);
			}
			if (logObject.get(EReturnsWorkflowConstants.TYPE).equals("WORKFLOW_COMPLETED")) {
				instanceDto.setCompletedAt(formatDateString(logObject.get(EReturnsWorkflowConstants.TIMESTAMP).toString()));
				instanceDto.setStatus(EReturnsWorkflowConstants.COMPLETED);
			} else {
				instanceDto.setStatus(EReturnsWorkflowConstants.IN_PROGRESS);
			}

		}
		if (instanceDto.getStatus().equals(EReturnsWorkflowConstants.COMPLETED)) {
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
		instanceDto.setEccResponse(requestDto.getEccReturnOrderNo());
		List<String> comments = new ArrayList<>();
		List<ReturnOrderDto> returnList = returnOrderRepository.getReturnOrderByRequestId(requestDto.getRequestId());
		if(returnList.size() > 0) {
			for(ReturnOrderDto returnOrderDto : returnList) {
				comments.add(returnOrderDto.getOrderComments());
			}
		}
		instanceDto.setCommentsByApprover(comments);
		return instanceDto;
	}
	
	private String getTaskStatus(String taskId)
	{
		String url = destination+EReturnsWorkflowConstants.WORKFLOW_REST_API;
		String status="";
		RestInvoker restInvoker = new RestInvoker(url, user, pwd);
		String response = restInvoker.getData("v1/task-instances/" + taskId + "/context");
		
		JSONObject responseObject=new JSONObject(response);
		
		if(responseObject.get(EReturnsWorkflowConstants.ACTION).equals("A"))
		{
			status = EReturnsWorkflowConstants.STATUS_APPROVED;
			
		}
		else if(responseObject.get(EReturnsWorkflowConstants.ACTION).equals("R"))
		{
			status = EReturnsWorkflowConstants.STATUS_REJECTED;
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
