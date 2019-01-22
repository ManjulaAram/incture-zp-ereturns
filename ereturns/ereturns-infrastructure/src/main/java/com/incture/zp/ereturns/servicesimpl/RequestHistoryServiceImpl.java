package com.incture.zp.ereturns.servicesimpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incture.zp.ereturns.dto.AttachmentDto;
import com.incture.zp.ereturns.dto.RequestHistoryDto;
import com.incture.zp.ereturns.dto.ResponseDto;
import com.incture.zp.ereturns.dto.ReturnOrderDto;
import com.incture.zp.ereturns.dto.RoleDto;
import com.incture.zp.ereturns.dto.StatusPendingDto;
import com.incture.zp.ereturns.dto.StatusResponseDto;
import com.incture.zp.ereturns.repositories.AttachmentRepository;
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
	AttachmentRepository attachmentRepository;

	@Autowired
	ImportExportUtil importExportUtil;

	@Override
	public StatusPendingDto getStatusForApprover(RoleDto roleDto) {

		List<String> pendingRequest = new ArrayList<>();
		List<String> approvedRequest = new ArrayList<>();
		List<String> rejectedRequest = new ArrayList<>();
		StatusPendingDto statusPendingDto = new StatusPendingDto();

		List<ReturnOrderDto> list = returnOrderRepository.getPendingWith(roleDto.getRole());
		List<RequestHistoryDto> reqList = requestHistoryRepository.getApprovedBy(roleDto.getUserId());
		for (ReturnOrderDto returnOrderDto : list) {
			pendingRequest.add(returnOrderDto.getRequestId());
		}
		int approved = 0;
		int rejected = 0;
		for (RequestHistoryDto requestHistoryDto : reqList) {
			if (requestHistoryDto.getRequestStatus() != null && !(requestHistoryDto.getRequestStatus().equals(""))) {
				if (requestHistoryDto.getRequestStatus().equalsIgnoreCase("REJECTED")) {
					rejected = rejected + 1;
					rejectedRequest.add(requestHistoryDto.getRequestId());
				} else {
					approved = approved + 1;
					approvedRequest.add(requestHistoryDto.getRequestId());
				}
			}
		}

		statusPendingDto.setPending(list.size());
		statusPendingDto.setApproved(approved);
		statusPendingDto.setRejected(rejected);

		statusPendingDto.setApprovedRequest(approvedRequest);
		statusPendingDto.setPendingRequest(pendingRequest);
		statusPendingDto.setRejectedRequest(rejectedRequest);

		return statusPendingDto;
	}

	@Override
	public ResponseDto addRequestHistory(RequestHistoryDto requestHistoryDto) {
		return requestHistoryRepository.addRequestHistory(importExportUtil.importRequestHistoryDto(requestHistoryDto));
	}

	@Override
	public List<StatusResponseDto> getApproverDashboardList(RoleDto roleDto, String status) {
		if (status.equalsIgnoreCase("PENDING")) {
			List<StatusResponseDto> pendingList = returnOrderRepository.getRequestorList("", roleDto.getRole());
			List<StatusResponseDto> modifiedPendingList = new ArrayList<StatusResponseDto>();
			for(Iterator<StatusResponseDto> itr = pendingList.iterator(); itr.hasNext();) {
				StatusResponseDto statusResponseDto = itr.next();
				Set<AttachmentDto> setAttachmentDto = attachmentRepository.getAttachmentsById(statusResponseDto.getRequestId());
				statusResponseDto.setAttachments(setAttachmentDto);
				modifiedPendingList.add(statusResponseDto);
			}
			return modifiedPendingList;
		} else if (status.equalsIgnoreCase("APPROVED")) {
			List<StatusResponseDto> modifiedApproved = new ArrayList<>();

			List<StatusResponseDto> approvedList = returnOrderRepository.getRequestorList("", null);
			List<RequestHistoryDto> reqList = requestHistoryRepository.getApprovedBy(roleDto.getUserId());
			for (int i = 0; i < reqList.size(); i++) {
				for (int j = 0; j < approvedList.size(); j++) {
					
					RequestHistoryDto requestHistoryDto = reqList.get(i);
					StatusResponseDto statusResponseDto = approvedList.get(j);
					if (requestHistoryDto.getRequestId().equalsIgnoreCase(statusResponseDto.getRequestId())
							&& ((requestHistoryDto.getRequestStatus().equalsIgnoreCase("COMPLETED")
									|| (requestHistoryDto.getRequestStatus().equalsIgnoreCase("INPROGRESS"))))) {
						statusResponseDto.setRequestStatus("Approved");
						modifiedApproved.add(statusResponseDto);
						break;
					}
				}
			}
			return modifiedApproved;
		} else if (status.equalsIgnoreCase("REJECTED")) {
			List<StatusResponseDto> modifiedRejected = new ArrayList<>();
			List<StatusResponseDto> rejectedList = returnOrderRepository.getRequestorList("", null);
			List<RequestHistoryDto> reqList = requestHistoryRepository.getApprovedBy(roleDto.getUserId());
			for (int i = 0; i < reqList.size(); i++) {
				for (int j = 0; j < rejectedList.size(); j++) {
					RequestHistoryDto requestHistoryDto = reqList.get(i);
					StatusResponseDto statusResponseDto = rejectedList.get(j);
					if (requestHistoryDto.getRequestId().equalsIgnoreCase(statusResponseDto.getRequestId())
							&& requestHistoryDto.getRequestStatus().equalsIgnoreCase("REJECTED")) {
						modifiedRejected.add(statusResponseDto);
						break;
					}

				}
			}
			return modifiedRejected;
		}

		return null;
	}

}
