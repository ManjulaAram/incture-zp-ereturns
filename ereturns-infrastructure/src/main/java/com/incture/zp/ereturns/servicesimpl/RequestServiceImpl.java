package com.incture.zp.ereturns.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incture.zp.ereturns.dto.RequestDto;
import com.incture.zp.ereturns.dto.ResponseDto;
import com.incture.zp.ereturns.dto.ReturnOrderDto;
import com.incture.zp.ereturns.dto.StatusRequestDto;
import com.incture.zp.ereturns.dto.StatusResponseDto;
import com.incture.zp.ereturns.repositories.RequestRepository;
import com.incture.zp.ereturns.services.RequestService;
import com.incture.zp.ereturns.utils.ImportExportUtil;
import com.incture.zp.ereturns.utils.RulesUtil;

@Service
@Transactional
public class RequestServiceImpl implements RequestService {

	@Autowired
	RequestRepository requestRepository;
	
	@Autowired
	ImportExportUtil importExportUtil;
	
	@Autowired RulesUtil rulesUtil;

	@Override
	public ResponseDto addRequest(RequestDto requestDto) {
		return requestRepository.addRequest(importExportUtil.importRequestDto(requestDto));
	}

	@Override
	public RequestDto getRequestById(String id) {
		return importExportUtil.exportRequestDto(requestRepository.getRequestById(id));
	}

	@Override
	public StatusResponseDto getStatusDetails(StatusRequestDto requestDto) {
		StatusResponseDto rList = requestRepository.getStatusDetails(requestDto);
		return rList;
	}

	@Override
	public ResponseDto updateRequestStatus(RequestDto requestDto) {
//		if(requestDto.getSetReturnOrderDto() != null) {
//			for(ReturnOrderDto returnOrderDto : requestDto.getSetReturnOrderDto()) {
//				String pending = rulesUtil.defineReasonRule(returnOrderDto.getReason());
//				requestDto.setRequestPendingWith(pending);
//			}
//		}
		return requestRepository.addRequest(importExportUtil.importRequestDto(requestDto));
	}

}
