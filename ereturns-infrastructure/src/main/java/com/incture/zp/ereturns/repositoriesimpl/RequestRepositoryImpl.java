package com.incture.zp.ereturns.repositoriesimpl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.incture.zp.ereturns.constants.EReturnConstants;
import com.incture.zp.ereturns.dto.RequestDto;
import com.incture.zp.ereturns.dto.ResponseDto;
import com.incture.zp.ereturns.dto.StatusRequestDto;
import com.incture.zp.ereturns.dto.StatusResponseDto;
import com.incture.zp.ereturns.model.Item;
import com.incture.zp.ereturns.model.Request;
import com.incture.zp.ereturns.model.ReturnOrder;
import com.incture.zp.ereturns.repositories.RequestRepository;
import com.incture.zp.ereturns.utils.GetReferenceData;
import com.incture.zp.ereturns.utils.ImportExportUtil;

@Repository
public class RequestRepositoryImpl implements RequestRepository {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	ImportExportUtil importExportUtil;

	@Autowired
	GetReferenceData getReferenceData;

	private static final Logger LOGGER = LoggerFactory.getLogger(RequestRepositoryImpl.class);

	@Override
	public ResponseDto addRequest(Request request) {
		ResponseDto responseDto = new ResponseDto();
		String requestId = getReferenceData.getNextSeqNumber(getReferenceData.execute("R"), 6, sessionFactory);
		if (request.getRequestId() == null || request.getRequestId().equals("")) {
			request.setRequestId(requestId);
			responseDto.setMessage("Request " + requestId + " Saved Successfully");
		}
		sessionFactory.getCurrentSession().saveOrUpdate(request);
		responseDto.setMessage("Request " + request.getRequestId() + " Created Successfully");
		responseDto.setStatus(EReturnConstants.SUCCESS_STATUS);
		responseDto.setCode(EReturnConstants.SUCCESS_STATUS_CODE);
		return responseDto;
	}

	@Override
	public List<StatusResponseDto> getStatusDetails(StatusRequestDto requestDto) {

		StringBuilder queryString = new StringBuilder();
		queryString
				.append("SELECT r, o, h, i FROM Request r, ReturnOrder o, Header h, Item i WHERE r.requestId = o.returnOrderData.requestId "
						+ "AND r.requestHeader.headerId = h.headerId AND h.headerId = i.itemData.headerId AND o.itemCode = i.itemCode");

		if (requestDto.getRequestId() != null && !(requestDto.getRequestId().equals(""))) {
			queryString.append(" AND r.requestId like :requestId");
		}

		if (requestDto.getCreatedBy() != null && !(requestDto.getCreatedBy().equals(""))) {
			queryString.append(" AND r.requestCreatedBy like :requestCreatedBy");
		}

		if (requestDto.getCustomerCode() != null && !(requestDto.getCustomerCode().equals(""))) {
			queryString.append(" AND r.soldTo like :soldTo");
		}

		if (requestDto.getCustomerName() != null && !(requestDto.getCustomerName().equals(""))) {
			queryString.append(" AND r.customer like :customer");
		}
		if ((requestDto.getStartDate() != null && !(requestDto.getStartDate().equals("")))
				&& (requestDto.getEndDate() != null) && !(requestDto.getEndDate().equals(""))) {
			queryString.append(" AND r.requestCreatedDate BETWEEN :startDate AND :endDate");
		}

		if (requestDto.getPendingWith() != null && !(requestDto.getPendingWith().equals(""))) {
			queryString.append(" AND o.orderPendingWith like :orderPendingWith");
		}

		if (requestDto.getPrincipalCode() != null && !(requestDto.getPrincipalCode().equals(""))) {
			queryString.append(" AND i.principalCode like :principalCode");
		}
		
		LOGGER.error("Query for Status details:" + queryString.toString());
		Query query = sessionFactory.getCurrentSession().createQuery(queryString.toString());
		if (requestDto.getRequestId() != null && !(requestDto.getRequestId().equals(""))) {
			query.setParameter("requestId", requestDto.getRequestId()+"%");
		}

		if (requestDto.getCreatedBy() != null && !(requestDto.getCreatedBy().equals(""))) {
			query.setParameter("requestCreatedBy", requestDto.getCreatedBy()+"%");
		}

		if (requestDto.getCustomerCode() != null && !(requestDto.getCustomerCode().equals(""))) {
			query.setParameter("soldTo", requestDto.getCustomerCode()+"%");
		}
		if (requestDto.getCustomerName() != null && !(requestDto.getCustomerName().equals(""))) {
			query.setParameter("customer", requestDto.getCustomerName()+"%");
		}
		if (requestDto.getPendingWith() != null && !(requestDto.getPendingWith().equals(""))) {
			query.setParameter("orderPendingWith", requestDto.getPendingWith()+"%");
		}
		if ((requestDto.getStartDate() != null && !(requestDto.getStartDate().equals("")))
				&& (requestDto.getEndDate() != null) && !(requestDto.getEndDate().equals(""))) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			try {
				query.setParameter("startDate", sdf.parse(requestDto.getStartDate()));
				query.setParameter("endDate", sdf.parse(requestDto.getEndDate()));
			} catch (ParseException e) {
				e.printStackTrace();
			}

		}
		if (requestDto.getPrincipalCode() != null && !(requestDto.getPrincipalCode().equals(""))) {
			queryString.append("principalCode"+requestDto.getPrincipalCode());
		}
		Request request = null;
		@SuppressWarnings("unchecked")
		List<Object[]> objectsList = query.list();
		List<StatusResponseDto> reqList = new ArrayList<>();
		StatusResponseDto statusResponseDto = null;
		LOGGER.error("Results for Request:" + objectsList.size());
		for (Object[] objects : objectsList) {
			request = (Request) objects[0];
			
			for(ReturnOrder returnOrder2 : request.getSetReturnOrder()) {
				statusResponseDto = new StatusResponseDto();
				for(Item item2 : request.getRequestHeader().getSetItem()) {
					if(returnOrder2.getItemCode().equalsIgnoreCase(item2.getItemCode())) {
							statusResponseDto.setItemCode(item2.getItemCode());
							statusResponseDto.setCreatedBy(returnOrder2.getOrderCreatedBy());
							if (returnOrder2.getOrderCreatedDate() != null && !(returnOrder2.getOrderCreatedDate().equals(""))) {
								DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								String output = dateFormat.format(returnOrder2.getOrderCreatedDate());
								statusResponseDto.setCreatedOn(output);
							}
							statusResponseDto.setMaterialCode(item2.getMaterial());
							statusResponseDto.setMaterialDescription(item2.getMaterialDesc());
							statusResponseDto.setRequestStatus(returnOrder2.getOrderStatus());
							statusResponseDto.setNetQuantity(item2.getAvailableQty());
							statusResponseDto.setNetValue(item2.getNetValue());
							statusResponseDto.setReturnQuantity(returnOrder2.getReturnQty());
							statusResponseDto.setReturnReason(returnOrder2.getReason());
							statusResponseDto.setRemark(returnOrder2.getRemark());
							statusResponseDto.setReturnType(returnOrder2.getPaymentType());
							statusResponseDto.setReturnValue(returnOrder2.getReturnValue());
							statusResponseDto.setSalesPerson(EReturnConstants.SALES_PERSON);
							statusResponseDto.setSalesPerson(returnOrder2.getOrderComments());
	
							// Request Level
							statusResponseDto.setRequestId(request.getRequestId());
							statusResponseDto.setShipTo(request.getShipTo());
							statusResponseDto.setSoldTo(request.getSoldTo());
							statusResponseDto.setInvoiceNo(request.getRequestHeader().getInvoiceNo());
							statusResponseDto.setCustomerName(request.getCustomer());
							statusResponseDto.setCustomerCode(request.getCustomerNo());
	
							statusResponseDto.setMessage(EReturnConstants.SUCCESS_STATUS);
							statusResponseDto.setStatus(EReturnConstants.SUCCESS_STATUS);
							reqList.add(statusResponseDto);
					}
				}
			}
		}
		@SuppressWarnings({ "rawtypes", "unchecked" })
		Set<StatusResponseDto> set = new TreeSet(new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				if(((StatusResponseDto) o1).getRequestId().equalsIgnoreCase(((StatusResponseDto) o2).getRequestId()) &&
						((StatusResponseDto) o1).getItemCode().equalsIgnoreCase(((StatusResponseDto) o2).getItemCode())){
	        		return 0;
	        	}
	        	return 1;
			}
		});
		set.addAll(reqList);
		List<StatusResponseDto> finalList = new ArrayList<>();
		finalList.addAll(set);
		return finalList;
	}

	@Override
	public Request getRequestById(String id) {
		return (Request) sessionFactory.getCurrentSession().get(Request.class, id);
	}

	@Override
	public List<RequestDto> getAllRequests() {
		List<RequestDto> list = new ArrayList<>();
		StringBuilder queryString = new StringBuilder();
		queryString.append("SELECT r FROM Request r");

		Query query = sessionFactory.getCurrentSession().createQuery(queryString.toString());
		@SuppressWarnings("unchecked")
		List<Request> requestList = (List<Request>) query.list();
		for (Request request : requestList) {
			list.add(importExportUtil.exportRequestDto(request));
		}
		return list;
	}

	public int updateEccReturnOrder(String eccStatus, String eccNo, String requestId) {
		String queryStr = "UPDATE Request SET eccStatus=:eccStatus, eccReturnOrderNo=:eccReturnOrderNo "
				+ "WHERE requestId=:requestId";
		Query query = sessionFactory.getCurrentSession().createQuery(queryStr);
		query.setParameter("eccStatus", eccStatus);
		query.setParameter("eccReturnOrderNo", eccNo);
		query.setParameter("requestId", requestId);

		int result = query.executeUpdate();
		return result;

	}

}
