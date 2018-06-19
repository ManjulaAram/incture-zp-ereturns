package com.incture.zp.ereturns.servicesimpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;
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
import com.incture.zp.ereturns.dto.WorkFlowDto;
import com.incture.zp.ereturns.model.Attachment;
import com.incture.zp.ereturns.repositories.AttachmentRepository;
import com.incture.zp.ereturns.repositories.HeaderRepository;
import com.incture.zp.ereturns.repositories.RequestRepository;
import com.incture.zp.ereturns.repositories.ReturnOrderRepository;
import com.incture.zp.ereturns.services.EcmDocumentService;
import com.incture.zp.ereturns.services.HciMappingEccService;
import com.incture.zp.ereturns.services.NotificationService;
import com.incture.zp.ereturns.services.RequestService;
import com.incture.zp.ereturns.services.WorkFlowService;
import com.incture.zp.ereturns.services.WorkflowTriggerService;
import com.incture.zp.ereturns.utils.ImportExportUtil;
import com.incture.zp.ereturns.utils.ServiceUtil;

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
	HciMappingEccService hciMappingService;

	@Autowired
	ServiceUtil serviceUtil;
	
	@Autowired
	NotificationService notificationService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RequestServiceImpl.class);

	@Override
	public ResponseDto addRequest(RequestDto requestDto) {
		ResponseDto responseDto = new ResponseDto();
		boolean processStartFlag = false;

		String requestId = null;
		DuplicateMaterialDto duplicateDto = findDuplicate(requestDto);
		duplicateDto.setDuplicate(false);
		if(duplicateDto.isDuplicate()) {
			responseDto.setCode(EReturnConstants.DUPLICATE_CODE);
			responseDto.setMessage(duplicateDto.getMaterials().toString());
			responseDto.setStatus(EReturnConstants.DUPLICATE);
		} else {
			try { 
				responseDto = requestRepository.addRequest(importExportUtil.importRequestDto(requestDto));
				if (responseDto != null) {
					if (responseDto.getMessage() != null) {
						requestId = responseDto.getMessage().substring(8, 19);
					}
				}

				Set<AttachmentDto> setAttachment = requestDto.getSetAttachments();
				for (AttachmentDto attachmentDto : setAttachment) {
					byte[] decodedString = Base64.decodeBase64(attachmentDto.getContent());
					String attachmentName = ecmDocumentService.uploadAttachment(decodedString,
							attachmentDto.getAttachmentName(), attachmentDto.getAttachmentType());
					attachmentDto.setAttachmentName(attachmentName);
					attachmentDto.setRequestId(requestId);
					Attachment attachment = importExportUtil.importAttachmentDto(attachmentDto);
					attachmentRepository.addAttachment(attachment);
				}

				if (responseDto != null) {
					if (responseDto.getCode().equals(EReturnConstants.SUCCESS_STATUS_CODE)) {
						processStartFlag = true;
					}
				}
			} catch (Exception e) {
				if(responseDto != null) {
					responseDto.setCode(EReturnConstants.ERROR_STATUS_CODE);
					responseDto.setStatus(EReturnConstants.ERROR_STATUS);
					responseDto.setMessage(e.getMessage());
				}
			}
		}
		
		if (processStartFlag) {
			responseDto = triggerWorkflow(requestDto, requestId, responseDto);
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
		List<String> materials = new ArrayList<>();
		boolean duplicate = false;
		List<RequestDto> list = getAllRequests();
		if (list != null && list.size() > 0) {
			for (RequestDto requestDto2 : list) {
				if (requestDto2.getHeaderDto().getInvoiceNo().equals(requestDto.getHeaderDto().getInvoiceNo()) &&
						((requestDto2.getRequestStatus().equalsIgnoreCase(EReturnConstants.NEW)) || 
								(requestDto2.getRequestStatus().equalsIgnoreCase(EReturnConstants.INPROGRESS)))) {
					for (ItemDto itemDto : requestDto2.getHeaderDto().getItemSet()) {
						for (ItemDto itemDto2 : requestDto.getHeaderDto().getItemSet()) {
							if (itemDto.getMaterial().equals(itemDto2.getMaterial())) {
								duplicate = true;
								materials.add("Duplicate Invoice "+requestDto.getHeaderDto().getInvoiceNo()
										+" and Material "+itemDto.getMaterial() +" exist");
								break;
							} else {
								duplicate = false;
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
	
	private ResponseDto triggerWorkflow(RequestDto requestDto, String requestId, ResponseDto responseDto) {
		boolean eccFlag = false;
		for (ItemDto itemDto : requestDto.getHeaderDto().getItemSet()) {
			String workFlowInstanceId = "";
			WorkFlowDto workFlowDto = new WorkFlowDto();

			// start process
			JSONObject jsonObj = new JSONObject();
			jsonObj.put(EReturnsWorkflowConstants.REQUEST_ID, requestId);
			jsonObj.put(EReturnsWorkflowConstants.ITEM_CODE, itemDto.getItemCode());
			jsonObj.put(EReturnsWorkflowConstants.INITIATOR, requestDto.getRequestCreatedBy());
			jsonObj.put(EReturnsWorkflowConstants.INVOICE, requestDto.getHeaderDto().getInvoiceNo());
			jsonObj.put(EReturnsWorkflowConstants.MATERIAL, itemDto.getMaterialDesc());


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
		}
		
		try {
			RequestDto requestDto2 = getRequestById(requestId);
			Thread.sleep(10000);
			List<ReturnOrderDto> returnList = returnOrderRepository.getReturnOrderByRequestId(requestId);
			if(returnList.size() > 0) {
				for(ReturnOrderDto returnOrderDto : returnList) {
					if(returnOrderDto.getRequestId().equalsIgnoreCase(requestId)) {
						if(returnOrderDto.getOrderStatus().equalsIgnoreCase(EReturnConstants.COMPLETE)) {
							responseDto = hciMappingService.pushDataToEcc(requestDto2);
							eccFlag = true;
							LOGGER.error("Data pushed to HCI successfully :" + responseDto.getMessage());
							break;
						}
					}
				}
			}
			if(eccFlag) {
				if(responseDto.getStatus().equalsIgnoreCase(EReturnConstants.ECC_SUCCESS_STATUS)) {
					notificationService.sendNotification(requestDto2.getRequestId(), requestDto2.getRequestPendingWith(), requestDto2.getRequestCreatedBy());
					requestRepository.updateEccReturnOrder(EReturnConstants.COMPLETE, responseDto.getMessage(), requestId);
				} else if(responseDto.getStatus().equalsIgnoreCase(EReturnConstants.ECC_ERROR_STATUS)) {
					responseDto.setMessage(responseDto.getMessage());
					
					//Re-triggering the process
					workFlowService.deleteWorkflow(requestId);
					
					LOGGER.error("Re-Triggering workflow:" + responseDto.getMessage());
					triggerWorkflow(requestDto2, requestId, responseDto);
				} 
			} else {
				notificationService.sendNotification(requestDto2.getRequestId(), requestDto2.getRequestPendingWith(), requestDto2.getRequestCreatedBy());
			}

		} catch (InterruptedException e) {
			if(responseDto != null) {
				responseDto.setCode(EReturnConstants.ERROR_STATUS_CODE);
				responseDto.setStatus(EReturnConstants.ERROR_STATUS);
				responseDto.setMessage(e.getMessage());
			}
			e.printStackTrace();
		}
		return responseDto;
	}
	
	@Override
	public ResponseDto pushDataToEccForTest(String requestId) {
		RequestDto requestDto = getRequestById(requestId);
		return hciMappingService.pushDataToEcc(requestDto);
	}

}
