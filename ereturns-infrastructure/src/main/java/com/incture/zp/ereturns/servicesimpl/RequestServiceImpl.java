package com.incture.zp.ereturns.servicesimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incture.zp.ereturns.dto.AttachmentDto;
import com.incture.zp.ereturns.dto.RequestDto;
import com.incture.zp.ereturns.dto.ResponseDto;
import com.incture.zp.ereturns.dto.StatusRequestDto;
import com.incture.zp.ereturns.dto.StatusResponseDto;
import com.incture.zp.ereturns.dto.WorkFlowDto;
import com.incture.zp.ereturns.model.Attachment;
import com.incture.zp.ereturns.repositories.AttachmentRepository;
import com.incture.zp.ereturns.repositories.HeaderRepository;
import com.incture.zp.ereturns.repositories.RequestRepository;
import com.incture.zp.ereturns.services.EcmDocumentService;
import com.incture.zp.ereturns.services.RequestService;
import com.incture.zp.ereturns.services.WorkFlowService;
import com.incture.zp.ereturns.services.WorkflowTriggerService;
import com.incture.zp.ereturns.utils.ImportExportUtil;

@Service
@Transactional
public class RequestServiceImpl implements RequestService {

	@Autowired
	RequestRepository requestRepository;
	
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
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RequestServiceImpl.class);
	
	@Override
	public ResponseDto addRequest(RequestDto requestDto) {
		ResponseDto responseDto = new ResponseDto();
		boolean processStartFlag = false;
		
		String requestId = null;
		try {
			
			responseDto = requestRepository.addRequest(importExportUtil.importRequestDto(requestDto));
			if(responseDto != null) {
				if(responseDto.getMessage() != null) {
					requestId = responseDto.getMessage().substring(8, 19);
				}
			}
			
			Set<AttachmentDto> setAttachment = requestDto.getSetAttachments();
			for(AttachmentDto attachmentDto : setAttachment) {
				byte[] decodedString = Base64.decodeBase64(attachmentDto.getContent());
				String attachmentName = ecmDocumentService.uploadAttachment(decodedString, attachmentDto.getAttachmentName(), 
						attachmentDto.getAttachmentType());
				LOGGER.error("Attachment URL:"+attachmentName);
				attachmentDto.setAttachmentName(attachmentName);
				attachmentDto.setRequestId(requestId);
				Attachment attachment = importExportUtil.importAttachmentDto(attachmentDto);
				attachmentRepository.addAttachment(attachment);
			}

			
			if(responseDto != null) {
				if(responseDto.getCode().equals("00")) {
					processStartFlag = true;
				}
			}
		} catch (Exception e) {
			responseDto.setCode("01");
			responseDto.setStatus("ERROR");
			responseDto.setMessage(e.getMessage());
		}
		
		if(processStartFlag) {
			
			String workFlowInstanceId="";
			WorkFlowDto workFlowDto=new WorkFlowDto();
			
			// start process
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("requestId", requestId);
			
			JSONObject obj = new JSONObject(); 
			obj.put("context", jsonObj);
			obj.put("definitionId", "zp_return_test");
			
			String payload = obj.toString(); 
			String output = workflowTriggerService.triggerWorkflow(payload);
			JSONObject resultJsonObject = new JSONObject(output);
			workFlowInstanceId=resultJsonObject.getString("id");
			
			workFlowDto.setRequestId(requestId);
			workFlowDto.setWorkFlowInstanceId(workFlowInstanceId);
			workFlowService.addWorkflowInstance(workFlowDto);
			
			LOGGER.error("Process triggered successfully :"+output);
		}
		return responseDto;
	}

	@Override
	public RequestDto getRequestById(String id) {
		RequestDto requestDto = importExportUtil.exportRequestDto(requestRepository.getRequestById(id));
		
		LOGGER.error("Request Id is: "+id);
		Set<AttachmentDto> setAttachmentDto = attachmentRepository.getAttachmentsById(id);
		requestDto.setSetAttachments(setAttachmentDto);
		return requestDto;
	}

	@Override
	public StatusResponseDto getStatusDetails(StatusRequestDto requestDto) {
		StatusResponseDto rList = requestRepository.getStatusDetails(requestDto);
		List<RequestDto> list = rList.getRequestDto();
		List<RequestDto> modifiedList = new ArrayList<>();
		for(RequestDto requestDto2 : list) {
			LOGGER.error("Adding attachment: "+requestDto2.getRequestId());
			Set<AttachmentDto> setAttachmentDto = attachmentRepository.getAttachmentsById(requestDto2.getRequestId());
			requestDto2.setSetAttachments(setAttachmentDto);
			modifiedList.add(requestDto2);
		}
		rList.setRequestDto(modifiedList);
		LOGGER.error("After Adding attachment: "+rList.getMessage());
		return rList;
	}

	@Override
	public ResponseDto updateRequestStatus(RequestDto requestDto) {
		ResponseDto responseDto = null;
		try {
			responseDto = requestRepository.addRequest(importExportUtil.importRequestDto(requestDto));
		} catch (Exception e) {
			responseDto.setCode("01");
			responseDto.setStatus("ERROR");
			responseDto.setMessage(e.getMessage());
		}
		return responseDto;
	}

}
