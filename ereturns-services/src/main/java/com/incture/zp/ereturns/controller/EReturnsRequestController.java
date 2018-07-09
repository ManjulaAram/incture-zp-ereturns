package com.incture.zp.ereturns.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.incture.zp.ereturns.dto.CompleteTaskRequestDto;
import com.incture.zp.ereturns.dto.CompleteTasksDto;
import com.incture.zp.ereturns.dto.RequestDto;
import com.incture.zp.ereturns.dto.ResponseDto;
import com.incture.zp.ereturns.dto.ReturnOrderDto;
import com.incture.zp.ereturns.dto.StatusPendingDto;
import com.incture.zp.ereturns.dto.StatusRequestDto;
import com.incture.zp.ereturns.dto.StatusResponseDto;
import com.incture.zp.ereturns.dto.WorkflowInstanceDto;
import com.incture.zp.ereturns.services.HciMappingEccService;
import com.incture.zp.ereturns.services.RequestService;
import com.incture.zp.ereturns.services.ReturnOrderService;
import com.incture.zp.ereturns.services.WorkflowTrackerService;
import com.incture.zp.ereturns.services.WorkflowTriggerService;

@RestController
@CrossOrigin
@ComponentScan("com.incture.zp.ereturns")
@RequestMapping(value = "/request", produces = "application/json")
public class EReturnsRequestController {

	@Autowired
	RequestService requestService;

	@Autowired
	WorkflowTrackerService wfTraackerService;

	@Autowired
	ReturnOrderService returnOrderService;
	
	@Autowired
	HciMappingEccService hciMappingEccService;

	@Autowired
	WorkflowTriggerService workFlowTriggerService;
	
	@RequestMapping("/hello")
	public String welcome() {// Welcome page, non-rest
		return "Welcome to RestTemplate Example.";
	}

	@RequestMapping(value = "/addRequest", method = RequestMethod.POST, consumes = { "application/json" })
	@ResponseBody
	public ResponseDto addRequest(@RequestBody RequestDto requestDto) {
		return requestService.addRequest(requestDto);
	}

	@RequestMapping(value = "/updateRequest", method = RequestMethod.POST, consumes = { "application/json" })
	@ResponseBody
	public ResponseDto updateRequest(@RequestBody RequestDto requestDto) {
		return requestService.updateRequestStatus(requestDto);
	}

	@RequestMapping(value = "/getStatusDetails", method = RequestMethod.POST, consumes = { "application/json" })
	@ResponseBody
	public List<StatusResponseDto> getStatusDetails(@RequestBody StatusRequestDto requestDto) {
		return requestService.getStatusDetails(requestDto);
	}

	@RequestMapping(value = "/deleteReturnOrderByItemCode/{itemCode}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseDto deleteReturnOrderByItemCode(@PathVariable(value = "itemCode") String itemCode) {
		return returnOrderService.deleteReturnOrderByItemCode(itemCode);
	}

	@RequestMapping(value = "/deleteReturnOrderByInvoiceNo/{invoiceNo}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseDto deleteReturnOrderByInvoiceNo(@PathVariable(value = "invoiceNo") String invoiceNo) {
		return returnOrderService.deleteReturnOrderByInvoiceNo(invoiceNo);
	}

	@RequestMapping(value = "/getRequestById/{requestId}", method = RequestMethod.GET)
	@ResponseBody
	public RequestDto getRequestById(@PathVariable(value = "requestId") String requestId) {
		return requestService.getRequestById(requestId);
	}

	@RequestMapping(value = "/getAllRequests", method = RequestMethod.GET)
	@ResponseBody
	public List<RequestDto> getAllRequests() {
		return requestService.getAllRequests();
	}
	
	@RequestMapping(value = "/completeWorkflow", method = RequestMethod.POST, consumes = { "application/json" })
	@ResponseBody
	public ResponseDto completeWorkflow(@RequestBody CompleteTasksDto completeTasksDto) {
		ResponseDto responseDto = null;
		for(CompleteTaskRequestDto completeTaskRequestDto : completeTasksDto.getCompleteRequestDto()) {
			List<ReturnOrderDto> list = returnOrderService.getReturnOrderByRequestId(completeTaskRequestDto.getRequestId());
			for(ReturnOrderDto returnOrderDto : list) {
				completeTaskRequestDto.setItemCode(returnOrderDto.getItemCode());
				completeTaskRequestDto.setRequestId(returnOrderDto.getRequestId());
				completeTaskRequestDto.setFlag(completeTaskRequestDto.getFlag());
				completeTaskRequestDto.setLoginUser(completeTaskRequestDto.getLoginUser());
				completeTaskRequestDto.setOrderComments(completeTaskRequestDto.getOrderComments());
				responseDto = workFlowTriggerService.completeTask(completeTaskRequestDto);
			}
		}
		return responseDto;
		 
	}
	
	@RequestMapping(value = "/getTaskInstance", method = RequestMethod.POST,consumes = { "application/json" })
	@ResponseBody
	public WorkflowInstanceDto getAllRequests(@RequestBody CompleteTaskRequestDto completeTaskRequestDto) {
//		return wfTraackerService.getTaskDetails(completeTaskRequestDto);
		return wfTraackerService.getTrackDetails(completeTaskRequestDto);
	}
	
	@RequestMapping(value = "/getTrackDetails", method = RequestMethod.POST,consumes = { "application/json" })
	@ResponseBody
	public WorkflowInstanceDto getTrackDetails(@RequestBody CompleteTaskRequestDto completeTaskRequestDto) {
		return wfTraackerService.getTrackDetails(completeTaskRequestDto);
	}
	
	@RequestMapping(value = "/pushData", method = RequestMethod.POST, consumes = { "application/json" })
	@ResponseBody
	public ResponseDto pushData(@RequestBody RequestDto requestDto) {
		return hciMappingEccService.pushDataToEcc(requestDto);
	}

	@RequestMapping(value = "/getPendingStatus/{userId}", method = RequestMethod.GET)
	@ResponseBody
	public StatusPendingDto getRequestStatusByUserId(@PathVariable(value = "userId") String userId) {
		return returnOrderService.getRequestStatusByUserId(userId);
	}
	
	@RequestMapping(value = "/getRequestStatus/{requestId}", method = RequestMethod.GET)
	@ResponseBody
	public String getRequestStatus(@PathVariable(value = "requestId") String requestId) {
		return requestService.getRequestStatus(requestId);
	}
	
	@RequestMapping(value = "/pushDataToECC/{requestId}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseDto pushDataToECC(@PathVariable(value = "requestId") String requestId) {
		return requestService.pushDataToEccForTest(requestId);
	}
}
