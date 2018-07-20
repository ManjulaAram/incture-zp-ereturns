package com.incture.zp.ereturns.servicesimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incture.zp.ereturns.dto.RequestHistoryDto;
import com.incture.zp.ereturns.dto.ResponseDto;
import com.incture.zp.ereturns.dto.ReturnOrderDto;
import com.incture.zp.ereturns.dto.RoleDto;
import com.incture.zp.ereturns.dto.StatusPendingDto;
import com.incture.zp.ereturns.repositories.RequestHistoryRepository;
import com.incture.zp.ereturns.repositories.ReturnOrderRepository;
import com.incture.zp.ereturns.services.RequestHistoryService;
import com.incture.zp.ereturns.utils.ImportExportUtil;

@Service
@Transactional
public class RequestHistoryServiceImpl implements RequestHistoryService {

	@Autowired
	RequestHistoryRepository requestHistoryRepository;
	
	@Autowired
	ReturnOrderRepository returnOrderRepository;
	
	@Autowired
	ImportExportUtil importExportUtil;
	
	@Override
	public StatusPendingDto getStatusForApprover(RoleDto roleDto) {
		
		StatusPendingDto statusPendingDto = new StatusPendingDto();
		List<ReturnOrderDto> list = returnOrderRepository.getPendingWith(roleDto.getRole());
		List<RequestHistoryDto> reqList = requestHistoryRepository.getApprovedBy(roleDto.getUserId());
		int approved = 0;
		int rejected = 0;
		for(RequestHistoryDto requestHistoryDto : reqList) {
			if(requestHistoryDto.getRequestStatus() != null && !(requestHistoryDto.getRequestStatus().equals(""))) {
				if(requestHistoryDto.getRequestStatus().equalsIgnoreCase("REJECTED")) {
					rejected = rejected + 1;
				} else {
					approved = approved + 1;
				}
			}
		}
		
		statusPendingDto.setPending(list.size());
		statusPendingDto.setApproved(approved);
		statusPendingDto.setRejected(rejected);
		
		return statusPendingDto;
	}

	@Override
	public ResponseDto addRequestHistory(RequestHistoryDto requestHistoryDto) {
		return requestHistoryRepository.addRequestHistory(importExportUtil.importRequestHistoryDto(requestHistoryDto));
	}

}
