 package com.incture.zp.ereturns.repositoriesimpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
	
	public String getNextSeqNumber(String referenceCode, int noOfDigits) {
		return SequenceNumberGen.getInstance().getNextSeqNumber(
				referenceCode, noOfDigits, sessionFactory.getCurrentSession());
	}

	@Override
	public ResponseDto addRequest(Request request) {
		ResponseDto responseDto = new ResponseDto();
		String requestId = getNextSeqNumber(new GetReferenceData().execute(""), 6);
		if(request.getRequestId() == null || request.getRequestId().equals("")) {
			request.setRequestId(requestId);
			responseDto.setMessage("Request "+ requestId +" Saved Successfully");
		}
		sessionFactory.getCurrentSession().saveOrUpdate(request);
		responseDto.setMessage("Request "+request.getRequestId()+" Updated Successfully");
		responseDto.setStatus("OK");
		return responseDto;
	}

	
	@Override
	public List<StatusResponseDto> getStatusDetails(StatusRequestDto requestDto) {
		
		StringBuilder queryString = new StringBuilder();
		queryString.append("SELECT r, o FROM Request r, ReturnOrder o WHERE r.requestId = o.returnOrderData.requestId ");
		
		if (requestDto.getRequestId() != null && !(requestDto.getRequestId().equals(""))) {
			queryString.append(" AND r.requestId=:requestId");
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
			queryString.append(" AND o.requestPendingWith=:requestPendingWith");
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

		String constraint = "";
		List<StatusResponseDto> list = new ArrayList<>();
		Request request = null;
		ReturnOrder returnOrder = null;
		Set<ReturnOrder> setReturnOrder = new HashSet<>();
		StatusResponseDto statusResponseDto = new StatusResponseDto();
		@SuppressWarnings("unchecked")
		List<Object[]> objectsList = query.list();
		for (Object[] objects : objectsList) {
			request = (Request) objects[0];
			returnOrder = (ReturnOrder) objects[1];
			
			if(constraint.equalsIgnoreCase("requestId")) {
				statusResponseDto.setRequestDto(importExportUtil.exportRequestDto(request));
				break;
			} else {
				setReturnOrder.add(returnOrder);
				request.setSetReturnOrder(setReturnOrder);
				statusResponseDto.setRequestDto(importExportUtil.exportRequestDto(request));
			}
		}
		list.add(statusResponseDto);
		return list;
	}

	@Override
	public Request getRequestById(String id) {
		return (Request) sessionFactory.getCurrentSession().get(Request.class, id);
	}

}
