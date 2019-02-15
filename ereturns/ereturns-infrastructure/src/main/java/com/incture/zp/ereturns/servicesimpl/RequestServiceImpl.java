package com.incture.zp.ereturns.servicesimpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incture.zp.ereturns.constants.EReturnConstants;
import com.incture.zp.ereturns.constants.EReturnsWorkflowConstants;
import com.incture.zp.ereturns.dto.AttachmentDto;
import com.incture.zp.ereturns.dto.DuplicateMaterialDto;
import com.incture.zp.ereturns.dto.ItemDto;
import com.incture.zp.ereturns.dto.RequestDto;
import com.incture.zp.ereturns.dto.ResponseDto;
import com.incture.zp.ereturns.dto.ReturnOrderDto;
import com.incture.zp.ereturns.dto.StatusRequestDto;
import com.incture.zp.ereturns.dto.StatusResponseDto;
import com.incture.zp.ereturns.dto.UserNameDto;
import com.incture.zp.ereturns.dto.WorkFlowDto;
import com.incture.zp.ereturns.repositories.AttachmentRepository;
import com.incture.zp.ereturns.repositories.HeaderRepository;
import com.incture.zp.ereturns.repositories.RequestRepository;
import com.incture.zp.ereturns.repositories.ReturnOrderRepository;
import com.incture.zp.ereturns.services.EcmDocumentService;
import com.incture.zp.ereturns.services.NotificationService;
import com.incture.zp.ereturns.services.RequestService;
import com.incture.zp.ereturns.services.UserNameService;
import com.incture.zp.ereturns.services.UserService;
import com.incture.zp.ereturns.services.WorkFlowService;
import com.incture.zp.ereturns.services.WorkflowTriggerService;
import com.incture.zp.ereturns.utils.ImportExportUtil;
import com.incture.zp.ereturns.utils.RestInvoker;
import com.incture.zp.ereturns.utils.ServiceUtil;
import com.sap.core.connectivity.api.configuration.DestinationConfiguration;

@Service
@Transactional
public class RequestServiceImpl implements RequestService {

	@Autowired
	RequestRepository requestRepository;
	
	@Autowired
	ReturnOrderRepository returnOrderRepository;

	@Autowired
	AttachmentRepository attachmentRepository;

	@Autowired
	HeaderRepository headerRepository;

	@Autowired
	EcmDocumentService ecmDocumentService;

	@Autowired
	ImportExportUtil importExportUtil;

	@Autowired
	WorkflowTriggerService workflowTriggerService;

	@Autowired
	WorkFlowService workFlowService;
	
	@Autowired
	ServiceUtil serviceUtil;
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserNameService userNameService;
	
	@Autowired
	NotificationService notificationService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RequestServiceImpl.class);

	@Override
	public ResponseDto addRequest(RequestDto requestDto) {
		ResponseDto responseDto = new ResponseDto();
		boolean processStartFlag = false;
		boolean exist = userNameService.isUserExist(requestDto.getRequestCreatedBy());
		if(!exist) {
			UserNameDto userNameDto = new UserNameDto();
			userNameDto.setUserId(requestDto.getRequestCreatedBy());
			userNameDto.setUserName(userService.getUserNameById(requestDto.getRequestCreatedBy()));
			userNameService.addUserName(userNameDto);
		}
		String requestId = "";
		String unref = requestDto.getUnrefFlag();
		if(unref != null && !(unref.equals("")) && unref.equalsIgnoreCase("TRUE")) {
			responseDto = saveData(requestDto);
			if (responseDto != null) {
				if (responseDto.getCode().equals(EReturnConstants.SUCCESS_STATUS_CODE)) {
					requestId = getRequestId(responseDto);
					processStartFlag = true;
				}
			}
		} else {
			DuplicateMaterialDto duplicateDto;
			synchronized (this) {
				duplicateDto = findDuplicate(requestDto);
			}
//			duplicateDto.setDuplicate(false);
			if(duplicateDto.isDuplicate()) {
				responseDto.setCode(EReturnConstants.DUPLICATE_CODE);
				responseDto.setMessage(duplicateDto.getMaterials().toString());
				responseDto.setStatus(EReturnConstants.DUPLICATE);
			} else {
				responseDto = saveData(requestDto);
				if (responseDto != null) {
					if (responseDto.getCode().equals(EReturnConstants.SUCCESS_STATUS_CODE)) {
						requestId = getRequestId(responseDto);
						processStartFlag = true;
					}
				}
			}
		}
		if (processStartFlag) {
			responseDto = triggerWorkflow(requestId, responseDto);
		}
		return responseDto;
	}

	@Override
	public RequestDto getRequestById(String id) {
		RequestDto requestDto = importExportUtil.exportRequestDto(requestRepository.getRequestById(id));

		Set<AttachmentDto> setAttachmentDto = attachmentRepository.getAttachmentsById(id);
		requestDto.setSetAttachments(setAttachmentDto);
		return requestDto;
	}

	@Override
	public List<StatusResponseDto> getStatusDetails(StatusRequestDto requestDto) {
		List<StatusResponseDto> rList = requestRepository.getStatusDetails(requestDto);
		List<StatusResponseDto> modifiedList = new ArrayList<StatusResponseDto>();
		for(Iterator<StatusResponseDto> itr = rList.iterator(); itr.hasNext();) {
			StatusResponseDto statusResponseDto = itr.next();
			Set<AttachmentDto> setAttachmentDto = attachmentRepository.getAttachmentsById(statusResponseDto.getRequestId());
			statusResponseDto.setAttachments(setAttachmentDto);
			modifiedList.add(statusResponseDto);
		}
		return modifiedList;
	}

	
	@Override
	public ResponseDto updateRequestStatus(RequestDto requestDto) {
		ResponseDto responseDto = null;
		try {
			responseDto = requestRepository.addRequest(importExportUtil.importRequestDto(requestDto));
		} catch (Exception e) {
			if(responseDto != null) {
				responseDto.setCode(EReturnConstants.ERROR_STATUS_CODE);
				responseDto.setStatus(EReturnConstants.ERROR_STATUS);
				responseDto.setMessage(e.getMessage());
			}
		}
		return responseDto;
	}

	@Override
	public List<RequestDto> getAllRequests() {
		return requestRepository.getAllRequests();
	}

	private DuplicateMaterialDto findDuplicate(RequestDto requestDto) {
		DuplicateMaterialDto duplicateMaterialDto = new DuplicateMaterialDto();
		List<ItemDto> itemList = new ArrayList<>();
		itemList.addAll(requestDto.getHeaderDto().getItemSet());
		
		
		List<String> materials = new ArrayList<>();
		boolean duplicate = false;
		List<RequestDto> list = getAllRequests();
		List<ReturnOrderDto> returnOrderList = new ArrayList<>();
		if (list != null && list.size() > 0) {
			for (RequestDto requestDto2 : list) {
				if (requestDto2.getHeaderDto().getInvoiceNo() != null) {
					if (requestDto2.getHeaderDto().getInvoiceNo().equalsIgnoreCase(requestDto.getHeaderDto().getInvoiceNo())
							&& ((requestDto2.getRequestStatus().equalsIgnoreCase(EReturnConstants.NEW)) || (requestDto2
									.getRequestStatus().equalsIgnoreCase(EReturnConstants.INPROGRESS)) || requestDto2
									.getRequestStatus().equalsIgnoreCase(EReturnConstants.REJECTED))) {
						for (ItemDto itemDto : requestDto2.getHeaderDto().getItemSet()) {
							for (int i = 0; i < itemList.size(); i++) {
								ItemDto itemDto2 = itemList.get(i);
								if (itemDto.getMaterial().equalsIgnoreCase(itemDto2.getMaterial())) {
									returnOrderList.addAll(requestDto2.getSetReturnOrderDto());
									if(returnOrderList.get(i).getOrderStatus().equalsIgnoreCase(EReturnConstants.NEW) || 
									(returnOrderList.get(i).getOrderStatus().equalsIgnoreCase(EReturnConstants.INPROGRESS))) {
										duplicate = true;
										int remainingQty = Integer.parseInt(returnOrderList.get(i).getReturnQty());
										materials.add("Request " + requestDto2.getRequestId() + " for Invoice "
												+ requestDto.getHeaderDto().getInvoiceNo() + " and Material "
												+ itemDto.getMaterial() + " is already in approval queue with quantity "
												+ remainingQty + " .Hence cannot proceed for new Request");
										break;
									} else {
										duplicate = false;
									}
								} 
							}
						}
					}
				}
			}
		}
		duplicateMaterialDto.setMaterials(materials);
		duplicateMaterialDto.setDuplicate(duplicate);
		return duplicateMaterialDto;
	}
	
	private ResponseDto triggerWorkflow(String requestId, ResponseDto responseDto) {
		String workFlowInstanceId = "";
		RequestDto requestDto = getRequestById(requestId);
		DestinationConfiguration destConfiguration = ServiceUtil.getDest(EReturnConstants.DATABASE_DESTINATION);
		String destination = destConfiguration.getProperty(EReturnConstants.DATABASE_DESTINATION_URL);
		String username = destConfiguration.getProperty(EReturnConstants.DATABASE_DESTINATION_USER);
		String password = destConfiguration.getProperty(EReturnConstants.DATABASE_DESTINATION_PWD);
		String url = destination;

//		String url = EReturnConstants.DATABASE_DESTINATION_URL;
//		String username = EReturnConstants.DATABASE_DESTINATION_USER;
//		String password = EReturnConstants.DATABASE_DESTINATION_PWD;

		for (ItemDto itemDto : requestDto.getHeaderDto().getItemSet()) {
			WorkFlowDto workFlowDto = new WorkFlowDto();
			
			// start process
			JSONObject jsonObj = new JSONObject();
			jsonObj.put(EReturnsWorkflowConstants.REQUEST_ID, requestId);
			jsonObj.put(EReturnsWorkflowConstants.ITEM_CODE, itemDto.getItemCode());
			jsonObj.put(EReturnsWorkflowConstants.INITIATOR, requestDto.getRequestCreatedBy());
			jsonObj.put(EReturnsWorkflowConstants.INVOICE, requestDto.getHeaderDto().getInvoiceNo());
			jsonObj.put(EReturnsWorkflowConstants.MATERIAL, itemDto.getMaterialDesc());
			
			jsonObj.put(EReturnsWorkflowConstants.CREATED_DATE, requestDto.getRequestCreatedDate());
			jsonObj.put(EReturnsWorkflowConstants.CUSTOMER, requestDto.getCustomerNo());

			JSONObject obj = new JSONObject();
			obj.put(EReturnsWorkflowConstants.CONTEXT, jsonObj);
			obj.put(EReturnsWorkflowConstants.DEFINITION_ID, EReturnsWorkflowConstants.DEFINITION_VALUE);

			String payload = obj.toString();
			String output = workflowTriggerService.triggerWorkflow(payload);
			JSONObject resultJsonObject = new JSONObject(output);
			workFlowInstanceId = resultJsonObject.getString(EReturnsWorkflowConstants.WORKFLOW_INSTANCE_ID);

			workFlowDto.setRequestId(requestId);
			workFlowDto.setWorkFlowInstanceId(workFlowInstanceId);

			workFlowDto.setMaterialCode(itemDto.getItemCode());
			workFlowDto.setPrincipal(itemDto.getPrincipalCode());
			workFlowDto.setTaskInstanceId("");
			workFlowService.addWorkflowInstance(workFlowDto);
			
			LOGGER.error("Process triggered successfully :" + output);
			
			RestInvoker restInvoker = new RestInvoker(url, username, password);
			String path = EReturnConstants.DATABASE_REST_API_REQUESTID+requestId+EReturnConstants.DATABASE_REST_API_ITEMCODE+itemDto.getItemCode();
			String response = restInvoker.getData(path);

			JSONObject responseObject = new JSONObject(response);
			if(responseObject != null) {
				boolean policy = (boolean) responseObject.get(EReturnConstants.IN_EXPIRY);
				String reason = (String) responseObject.get(EReturnConstants.REASON);
				String principal = (String) responseObject.get(EReturnConstants.PRINCIPAL_GROUP);
				
				if(policy)
				{
//					if(!(reason.equalsIgnoreCase("T21")) && !(reason.equalsIgnoreCase("T22"))) {
//					//Auto approve
//					// notificationService.sendNotificationForRequestor(requestId, requestDto.getRequestCreatedBy(), "A");
//					} else {
//					//Principal
//						notificationService.sendNotificationForApprover(requestId, "Principal");
//					}
					LOGGER.error("Principal coming from workflow for in-policy:" + principal+"..."+reason);
				} else {
					//Principal code based user
					LOGGER.error("Principal coming from workflow for out-policy:" + principal+"...."+reason);
					notificationService.sendNotificationForApprover(requestId, principal);
				}
			}
		}
		return responseDto;
	}
	
	@Override
	public String getRequestStatus(String requestId) {
		return requestRepository.getRequestStatus(requestId);
	}
	
	@Override
	public String getRequestClient(String requestId) {
		return requestRepository.getRequestClient(requestId);
	}
	
	@Override
	public ResponseDto postToEcc(String requestId) {
		RequestDto requestDto = getRequestById(requestId);
		String client = requestDto.getClient();
		if(client != null && !(client.equals("")) && client.equalsIgnoreCase("WEB")) {
			requestDto.setPurchaseOrder("ERC");
		} else {
			requestDto.setPurchaseOrder("ERS");
		}
		ResponseDto responseDto = new ResponseDto();
		responseDto.setCode("01");
		responseDto.setMessage("Approval group is not assigned.");
		responseDto.setStatus("SUCCESS");
		return responseDto;
//		return hciMappingService.pushDataToEcc(requestDto, "", "AUTO");
	}

	private ResponseDto saveData(RequestDto requestDto) {
		ResponseDto responseDto = new ResponseDto(); 
		try { 
			responseDto = requestRepository.addRequest(importExportUtil.importRequestDto(requestDto));

//			Set<AttachmentDto> setAttachment = requestDto.getSetAttachments();
//			for (AttachmentDto attachmentDto : setAttachment) {
//				byte[] decodedString = Base64.decodeBase64(attachmentDto.getContent());
//				String attachmentName = ecmDocumentService.uploadAttachment(decodedString,
//						attachmentDto.getAttachmentName(), attachmentDto.getAttachmentType());
//				attachmentDto.setAttachmentName(attachmentName);
//				attachmentDto.setRequestId(getRequestId(responseDto));
//				Attachment attachment = importExportUtil.importAttachmentDto(attachmentDto);
//				attachmentRepository.addAttachment(attachment);
//			}
			String requestId = "";
			if(responseDto.getMessage() != null && !(responseDto.getMessage().equals(""))) {
				requestId = responseDto.getMessage().substring(8, 19);
			}
			requestDto.setRequestId(requestId);
			attachmentRepository.updateAttachment(requestDto);
		} catch (Exception e) {
			if(responseDto != null) {
				responseDto.setCode(EReturnConstants.ERROR_STATUS_CODE);
				responseDto.setStatus(EReturnConstants.ERROR_STATUS);
				responseDto.setMessage(e.getMessage());
			}
		}
		return responseDto;
	}
	
	private String getRequestId(ResponseDto responseDto) {
		String requestId = "";
		if (responseDto != null) {
			if (responseDto.getMessage() != null) {
				requestId = responseDto.getMessage().substring(8, 19);
			}
		}
		return requestId;
	}
	
	
}
