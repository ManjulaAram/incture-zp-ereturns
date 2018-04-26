 package com.incture.zp.ereturns.repositoriesimpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.incture.zp.ereturns.dto.RequestDto;
import com.incture.zp.ereturns.dto.ResponseDto;
import com.incture.zp.ereturns.dto.StatusRequestDto;
import com.incture.zp.ereturns.dto.StatusResponseDto;
import com.incture.zp.ereturns.model.Request;
import com.incture.zp.ereturns.model.ReturnOrder;
import com.incture.zp.ereturns.repositories.RequestRepository;
import com.incture.zp.ereturns.utils.GetReferenceData;
import com.incture.zp.ereturns.utils.ImportExportUtil;
import com.incture.zp.ereturns.utils.SequenceNumberGen;

@Repository
public class RequestRepositoryImpl implements RequestRepository {

	@Autowired
    private SessionFactory sessionFactory;
	
	@Autowired
	ImportExportUtil importExportUtil;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ImportExportUtil.class);
	
	public String getNextSeqNumber(String referenceCode, int noOfDigits) {
		return SequenceNumberGen.getInstance().getNextSeqNumber(
				referenceCode, noOfDigits, sessionFactory.getCurrentSession());
	}

	@Override
	public ResponseDto addRequest(Request request) {
		ResponseDto responseDto = new ResponseDto();
		String requestId = getNextSeqNumber(new GetReferenceData().execute("R"), 6);
		if(request.getRequestId() == null || request.getRequestId().equals("")) {
			request.setRequestId(requestId);
			responseDto.setMessage("Request "+ requestId +" Saved Successfully");
		}
		sessionFactory.getCurrentSession().saveOrUpdate(request);
		responseDto.setMessage("Request "+request.getRequestId()+" Updated Successfully");
		responseDto.setStatus("SUCCESS");
		responseDto.setCode("00");
		return responseDto;
	}

	
	@Override
	public StatusResponseDto getStatusDetails(StatusRequestDto requestDto) {
		
		String constraint = "";
		StringBuilder queryString = new StringBuilder();
		queryString.append("SELECT r, o FROM Request r, ReturnOrder o WHERE r.requestId = o.returnOrderData.requestId ");
		
		if (requestDto.getRequestId() != null && !(requestDto.getRequestId().equals(""))) {
			queryString.append(" AND r.requestId=:requestId");
			constraint = "requestId";
		}
		
		if (requestDto.getCreatedBy() != null && !(requestDto.getCreatedBy().equals(""))) {
			queryString.append(" AND r.requestCreatedBy=:requestCreatedBy");
		}
		
		if (requestDto.getCustomerCode() != null && !(requestDto.getCustomerCode().equals(""))) {
			queryString.append(" AND o.userCode=:userCode");
		}
		
		if((requestDto.getStartDate() != null && !(requestDto.getStartDate().equals(""))) && 
				(requestDto.getEndDate() != null) && !(requestDto.getEndDate().equals(""))) {
			queryString.append(" AND r.requestCreatedDate BETWEEN :startDate AND :endDate");
		}

		if (requestDto.getPendingWith() != null && !(requestDto.getPendingWith().equals(""))) {
			queryString.append(" AND r.requestPendingWith=:requestPendingWith");
		}
		
		Query query = sessionFactory.getCurrentSession().createQuery(queryString.toString());
		if (requestDto.getRequestId() != null && !(requestDto.getRequestId().equals(""))) {
			query.setParameter("requestId", requestDto.getRequestId());
		}
		
		if (requestDto.getCreatedBy() != null && !(requestDto.getCreatedBy().equals(""))) {
			query.setParameter("requestCreatedBy", requestDto.getCreatedBy());
		}
		
		if (requestDto.getCustomerCode() != null && !(requestDto.getCustomerCode().equals(""))) {
			query.setParameter("userCode", requestDto.getCustomerCode());
		}
		
		if (requestDto.getPendingWith() != null && !(requestDto.getPendingWith().equals(""))) {
			query.setParameter("requestPendingWith", requestDto.getPendingWith());
		}
		if((requestDto.getStartDate() != null && !(requestDto.getStartDate().equals(""))) && 
				(requestDto.getEndDate() != null) && !(requestDto.getEndDate().equals(""))) {
			query.setParameter("startDate", requestDto.getStartDate());
			query.setParameter("endDate", requestDto.getEndDate());
		}

		
		List<RequestDto> reqList = new ArrayList<>();
		Request request = null;
		ReturnOrder returnOrder = null;
		StatusResponseDto statusResponseDto = new StatusResponseDto();
		int size;
		int count = 0;
		Set<ReturnOrder> setReturnOrder = new HashSet<>();
		@SuppressWarnings("unchecked")
		List<Object[]> objectsList = query.list();
		for (Object[] objects : objectsList) {
			request = (Request) objects[0];
			returnOrder = (ReturnOrder) objects[1];
			
			if(constraint.equalsIgnoreCase("requestId")) {
				reqList.add(importExportUtil.exportRequestDto(request));
				break;
			} else {
				size = request.getSetReturnOrder().size();
				LOGGER.error("Adding for size:"+size);
				if(size > 1) { 
					setReturnOrder.add(returnOrder);
					request.setSetReturnOrder(setReturnOrder);
					count = size;
				} else {
					LOGGER.error("Adding for count:"+count); 
					if(count > 1) {
						setReturnOrder.add(returnOrder);
						request.setSetReturnOrder(setReturnOrder);
						count = count - 1;
					}
					reqList.add(importExportUtil.exportRequestDto(request));
				}
			}
		}
		statusResponseDto.setRequestDto(reqList);
		return statusResponseDto;
	}

	@Override
	public Request getRequestById(String id) {
		return (Request) sessionFactory.getCurrentSession().get(Request.class, id);
	}

}
