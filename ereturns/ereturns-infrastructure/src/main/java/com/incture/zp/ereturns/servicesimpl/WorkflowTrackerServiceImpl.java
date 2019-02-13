package com.incture.zp.ereturns.servicesimpl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incture.zp.ereturns.constants.EReturnsWorkflowConstants;
import com.incture.zp.ereturns.dto.ApproverDto;
import com.incture.zp.ereturns.dto.CompleteTaskRequestDto;
import com.incture.zp.ereturns.dto.CreditNoteStatusDto;
import com.incture.zp.ereturns.dto.RequestDto;
import com.incture.zp.ereturns.dto.RequestHistoryDto;
import com.incture.zp.ereturns.dto.ReturnOrderDto;
import com.incture.zp.ereturns.dto.WorkflowInstanceDto;
import com.incture.zp.ereturns.repositories.RequestHistoryRepository;
import com.incture.zp.ereturns.repositories.ReturnOrderRepository;
import com.incture.zp.ereturns.services.RequestService;
import com.incture.zp.ereturns.services.UserService;
import com.incture.zp.ereturns.services.WorkFlowService;
import com.incture.zp.ereturns.services.WorkflowTrackerService;
import com.incture.zp.ereturns.utils.CreditNoteUtil;
import com.incture.zp.ereturns.utils.ServiceUtil;
import com.sap.core.connectivity.api.configuration.DestinationConfiguration;

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
	RequestHistoryRepository requestHistoryRepository;

	@Autowired
	ReturnOrderRepository returnOrderRepository;
	
	@Autowired
	CreditNoteUtil creditNoteUtil;
	
//	private static final Logger LOGGER = LoggerFactory.getLogger(WorkflowTrackerServiceImpl.class);

	String destination;
	String user;
	String pwd;

	public WorkflowTrackerServiceImpl() {
		DestinationConfiguration destConfiguration = ServiceUtil
				.getDest(EReturnsWorkflowConstants.WORKFLOW_DESTINATION);
		destination = destConfiguration.getProperty(EReturnsWorkflowConstants.WORKFLOW_DESTINATION_URL);
		user = destConfiguration.getProperty(EReturnsWorkflowConstants.WORKFLOW_DESTINATION_USER);
		pwd = destConfiguration.getProperty(EReturnsWorkflowConstants.WORKFLOW_DESTINATION_PWD);

		// destination = EReturnsWorkflowConstants.WORKFLOW_DESTINATION_URL;
		// user = EReturnsWorkflowConstants.WORKFLOW_DESTINATION_USER;
		// pwd = EReturnsWorkflowConstants.WORKFLOW_DESTINATION_PWD;
	}

	public String callCreditNoteStatus(String sdNo) {
		String status = "";
		int deliveryCount = 0;
		int cnCount = 0;
		List<CreditNoteStatusDto> statusList = creditNoteUtil.getCreditNoteStatus(sdNo);
		if(statusList.size() > 0) {
			for(CreditNoteStatusDto dto : statusList) {
				if(dto.getDocumentCategoryOfSubsequentDocument() != null && !(dto.getDocumentCategoryOfSubsequentDocument().equals(""))) {
					if(dto.getDocumentCategoryOfSubsequentDocument().equalsIgnoreCase("T")) {
						deliveryCount = deliveryCount + 1;
					} 
					if(dto.getDocumentCategoryOfSubsequentDocument().equalsIgnoreCase("O")) {
						cnCount = cnCount + 1;
					}
				}
			}
		}
		if(deliveryCount > 1) {
			status = "Multiple Returns Delivery Created";
			return status;
		} else if(deliveryCount == 1) {
			status = "Returns Delivery Posted";
		} else if(cnCount == 1) {
			status = "Credit Note Posted";
		} else if(cnCount > 1) {
			status = "Multiple Credit Note Created";
		}
		
		return status;
	}
	
	public WorkflowInstanceDto getTrackDetails(CompleteTaskRequestDto completeTaskRequestDto) {

		RequestDto requestDto = requestService.getRequestById(completeTaskRequestDto.getRequestId());

		WorkflowInstanceDto instanceDto = new WorkflowInstanceDto();
		List<ApproverDto> approverList = new ArrayList<>();
		List<RequestHistoryDto> executionLogs = requestHistoryRepository
				.getRequestHistory(completeTaskRequestDto.getRequestId(), completeTaskRequestDto.getItemCode());
		List<String> recipientList = new ArrayList<>(); // pending with
		boolean flag = false;
		ApproverDto approverDto = null;
		String material = "";
		String recipient = "";

		for (RequestHistoryDto logObject : executionLogs) {
			if (logObject.getRequestStatus() != null) {
				if (logObject.getRequestStatus().equalsIgnoreCase("INPROGRESS")) {
					instanceDto.setStatus(logObject.getRequestStatus());
					recipient = logObject.getRequestPendingWith();
					flag = false;
				}
				if (logObject.getRequestStatus().equalsIgnoreCase("COMPLETED")) {
					instanceDto.setCompletedAt(logObject.getRequestApprovedDate());
					instanceDto.setStatus(logObject.getRequestStatus());
					flag = true;
				}
				if (logObject.getRequestStatus().equalsIgnoreCase("REJECTED")) {
					instanceDto.setCompletedAt(logObject.getRequestApprovedDate());
					instanceDto.setStatus(logObject.getRequestStatus());
					flag = true;
				}

				if (logObject.getRequestApprovedBy() != null && !(logObject.getRequestApprovedBy().equals(""))) {
					approverDto = new ApproverDto();

					if (logObject.getRequestApprovedBy().equalsIgnoreCase("WF_SYSTEM")) {
						approverDto.setApproverName(logObject.getRequestApprovedBy());
						approverDto.setCommentsByApprover("");
					} else {
						approverDto.setApproverName(userService.getUserNameById(logObject.getRequestApprovedBy()));
						approverDto.setCommentsByApprover(logObject.getRequestorComments());
					}
					approverDto.setApprovalDate(logObject.getRequestApprovedDate());
					if (logObject.getRequestStatus().equalsIgnoreCase("COMPLETED")) {
						approverDto.setStatus(EReturnsWorkflowConstants.STATUS_APPROVED);
					} else if (logObject.getRequestStatus().equalsIgnoreCase("REJECTED")) {
						approverDto.setStatus(EReturnsWorkflowConstants.STATUS_REJECTED);
					} else {
						approverDto.setStatus(EReturnsWorkflowConstants.STATUS_APPROVED);
					}
					approverList.add(approverDto);
				}
			}

			material = logObject.getMaterial();
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		Set<ApproverDto> set = new TreeSet(new Comparator<ApproverDto>() {
			@Override
			public int compare(ApproverDto o1, ApproverDto o2) {
				if ((o1.getApproverName()).equalsIgnoreCase(o2.getApproverName())) {
					return 0;
				}
				return 1;
			}
		});

		set.addAll(approverList);
		List<ApproverDto> finalList = new ArrayList<>();
		finalList.addAll(set);
//		LOGGER.error("Approver list for track:"+finalList.size());
		recipientList.add(recipient);
		if (requestDto.getEccStatus() != null && !(requestDto.getEccStatus().equals(""))) {
			if (requestDto.getEccStatus().equalsIgnoreCase("ECC_ERROR")) {
				flag = false;
				int index = finalList.size() - 1;
				finalList.remove(index);
				instanceDto.setStatus("INPROGRESS");
			}
		}
		
//		if (!flag && finalList.size() > 0) {
//			int index = finalList.size() - 1;
//			finalList.remove(index);
//		}
//		LOGGER.error("flag to check:"+finalList.size());
		if (flag) {
			recipientList.clear();
		}
		instanceDto.setCreatedBy(userService.getUserNameById(requestDto.getRequestCreatedBy()));
		instanceDto.setCreatedAt(requestDto.getRequestCreatedDate());
		instanceDto.setRequestId(requestDto.getRequestId());
		instanceDto.setMaterialCode(material);
		instanceDto.setApproverList(finalList);
		instanceDto.setReceipents(recipientList);
		instanceDto.setEccResponse(requestDto.getEccReturnOrderNo());
		String eccStatus = "";
		if (requestDto.getEccStatus() != null && !(requestDto.getEccStatus().equals(""))) {
			if (requestDto.getEccStatus().equalsIgnoreCase("COMPLETED")) {
				if(requestDto.getEccReturnOrderNo() != null && !(requestDto.getEccReturnOrderNo().equals(""))) {
					eccStatus = callCreditNoteStatus(requestDto.getEccReturnOrderNo());
				}
			}
		}

		instanceDto.setEccStatus(eccStatus);
		returnOrderRepository.updateEccReturnOrderStatusCN(eccStatus, completeTaskRequestDto.getRequestId(), completeTaskRequestDto.getItemCode());
		return instanceDto;
	}

	public WorkflowInstanceDto getTaskInstances(CompleteTaskRequestDto completeTaskRequestDto) {

		RequestDto requestDto = requestService.getRequestById(completeTaskRequestDto.getRequestId());

		WorkflowInstanceDto instanceDto = new WorkflowInstanceDto();
		List<ApproverDto> approverList = new ArrayList<>();
		List<ReturnOrderDto> executionLogs = returnOrderRepository
				.getRequestOrderTrack(completeTaskRequestDto.getRequestId(), completeTaskRequestDto.getItemCode());
		List<String> recipientList = new ArrayList<>(); // pending with
		boolean flag = false;
		ApproverDto approverDto = null;
		String material = "";
		String recipient = "";

		for (ReturnOrderDto logObject : executionLogs) {
			if (logObject.getOrderStatus() != null) {
				if (logObject.getOrderStatus().equalsIgnoreCase("INPROGRESS")) {
					instanceDto.setStatus(logObject.getOrderStatus());
					recipient = logObject.getOrderPendingWith();
					flag = false;
				}
				if (logObject.getOrderStatus().equalsIgnoreCase("COMPLETED")) {
					instanceDto.setCompletedAt(logObject.getOrderApprovedDate());
					instanceDto.setStatus(logObject.getOrderStatus());
					flag = true;
				}
				if (logObject.getOrderStatus().equalsIgnoreCase("REJECTED")) {
					instanceDto.setCompletedAt(logObject.getOrderApprovedDate());
					instanceDto.setStatus(logObject.getOrderStatus());
					flag = true;
				}

				if (logObject.getOrderApprovedBy() != null && !(logObject.getOrderApprovedBy().equals(""))) {
					approverDto = new ApproverDto();

					if (logObject.getOrderApprovedBy().equalsIgnoreCase("WF_SYSTEM")) {
						approverDto.setApproverName(logObject.getOrderApprovedBy());
						approverDto.setCommentsByApprover("");
					} else {
						approverDto.setApproverName(userService.getUserNameById(logObject.getOrderApprovedBy()));
						approverDto.setCommentsByApprover(logObject.getOrderComments());
					}
					approverDto.setApprovalDate(logObject.getOrderApprovedDate());
					if (logObject.getOrderStatus().equalsIgnoreCase("COMPLETED")) {
						approverDto.setStatus(EReturnsWorkflowConstants.STATUS_APPROVED);
					} else if (logObject.getOrderStatus().equalsIgnoreCase("REJECTED")) {
						approverDto.setStatus(EReturnsWorkflowConstants.STATUS_REJECTED);
					} else {
						approverDto.setStatus(EReturnsWorkflowConstants.STATUS_APPROVED);
					}
					approverList.add(approverDto);
				}
			}

//			material = logObject.getMaterial();
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		Set<ApproverDto> set = new TreeSet(new Comparator<ApproverDto>() {
			@Override
			public int compare(ApproverDto o1, ApproverDto o2) {
				if ((o1.getApproverName()).equalsIgnoreCase(o2.getApproverName())) {
					return 0;
				}
				return 1;
			}
		});

		set.addAll(approverList);
		List<ApproverDto> finalList = new ArrayList<>();
		finalList.addAll(set);

		recipientList.add(recipient);
		// if(requestDto.getEccStatus() != null &&
		// !(requestDto.getEccStatus().equals(""))) {
		// if(requestDto.getEccStatus().equalsIgnoreCase("ECC_ERROR")) {
		// flag = false;
		// int index = finalList.size() - 1;
		// finalList.remove(index);
		// instanceDto.setStatus("INPROGRESS");
		// }
		// }
		if (!flag && finalList.size() > 0) {
			int index = finalList.size() - 1;
			finalList.remove(index);
		}

		if (flag) {
			recipientList.clear();
		}
		instanceDto.setCreatedBy(userService.getUserNameById(requestDto.getRequestCreatedBy()));
		instanceDto.setCreatedAt(requestDto.getRequestCreatedDate());
		instanceDto.setRequestId(requestDto.getRequestId());
		instanceDto.setMaterialCode(material);
		instanceDto.setApproverList(finalList);
		instanceDto.setReceipents(recipientList);
		instanceDto.setEccResponse(requestDto.getEccReturnOrderNo());

		return instanceDto;
	}

}
