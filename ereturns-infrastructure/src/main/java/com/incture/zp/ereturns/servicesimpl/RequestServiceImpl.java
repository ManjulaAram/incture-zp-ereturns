package com.incture.zp.ereturns.servicesimpl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incture.zp.ereturns.dto.AttachmentDto;
import com.incture.zp.ereturns.dto.RequestDto;
import com.incture.zp.ereturns.dto.ResponseDto;
import com.incture.zp.ereturns.dto.StatusRequestDto;
import com.incture.zp.ereturns.dto.StatusResponseDto;
import com.incture.zp.ereturns.model.Attachment;
import com.incture.zp.ereturns.repositories.AttachmentRepository;
import com.incture.zp.ereturns.repositories.RequestRepository;
import com.incture.zp.ereturns.services.RequestService;
import com.incture.zp.ereturns.utils.ImportExportUtil;

@Service
@Transactional
public class RequestServiceImpl implements RequestService {

	@Autowired
	RequestRepository requestRepository;
	
	@Autowired
	AttachmentRepository attachmentRepository;
	
	@Autowired
	ImportExportUtil importExportUtil;
	
	@Override
	public ResponseDto addRequest(RequestDto requestDto) {
		ResponseDto responseDto = new ResponseDto();
		boolean processStartFlag = false;
		
		try {
			responseDto = requestRepository.addRequest(importExportUtil.importRequestDto(requestDto));
			
			Set<AttachmentDto> setAttachment = requestDto.getSetAttachmentDto();
			for(AttachmentDto attachmentDto : setAttachment) {
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
			// start process
		}
		return responseDto;
	}

	@Override
	public RequestDto getRequestById(String id) {
		RequestDto requestDto = importExportUtil.exportRequestDto(requestRepository.getRequestById(id));
		Set<AttachmentDto> setAttachmentDto = attachmentRepository.getAttachmentsById(id);
		requestDto.setSetAttachmentDto(setAttachmentDto);
		return requestDto;
	}

	@Override
	public StatusResponseDto getStatusDetails(StatusRequestDto requestDto) {
		StatusResponseDto rList = requestRepository.getStatusDetails(requestDto);
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
