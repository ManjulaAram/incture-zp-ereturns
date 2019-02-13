package com.incture.zp.ereturns.repositoriesimpl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
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
import com.incture.zp.ereturns.dto.UpdateDto;
import com.incture.zp.ereturns.model.Header;
import com.incture.zp.ereturns.model.Item;
import com.incture.zp.ereturns.model.Request;
import com.incture.zp.ereturns.model.ReturnOrder;
import com.incture.zp.ereturns.repositories.ReasonRepository;
import com.incture.zp.ereturns.repositories.RequestRepository;
import com.incture.zp.ereturns.utils.GetReferenceData;
import com.incture.zp.ereturns.utils.ImportExportUtil;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType; 

@Repository
public class RequestRepositoryImpl implements RequestRepository {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	ImportExportUtil importExportUtil;

	@Autowired
	GetReferenceData getReferenceData;
	
	@Autowired
	ReasonRepository reasonRepository;

	private static final Logger LOGGER = LoggerFactory.getLogger(RequestRepositoryImpl.class);

	
	@Override
	@Transactional(value=TxType.REQUIRES_NEW) 
	public ResponseDto addRequest(Request request) {
		ResponseDto responseDto = new ResponseDto();
		String requestId = getReferenceData.getNextSeqNumber(getReferenceData.execute("R"), 6, sessionFactory);
		if (request.getRequestId() == null || request.getRequestId().equals("")) {
			request.setRequestId(requestId);
			responseDto.setMessage("Request " + requestId + " Saved Successfully");
		}
		sessionFactory.getCurrentSession().saveOrUpdate(request);
		responseDto.setMessage("Request " + request.getRequestId() + " created successfully");
		responseDto.setStatus(EReturnConstants.SUCCESS_STATUS);
		responseDto.setCode(EReturnConstants.SUCCESS_STATUS_CODE);
		return responseDto;
	}

	@Override
	public List<StatusResponseDto> getStatusDetails(StatusRequestDto requestDto) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		boolean flag = false;
		StringBuilder queryString = new StringBuilder();
		queryString
				.append("SELECT r, o, h, i FROM Request r, ReturnOrder o, Header h, Item i WHERE r.requestId = o.returnOrderData.requestId "
						+ "AND r.requestHeader.headerId = h.headerId AND h.headerId = i.itemData.headerId AND o.itemCode = i.itemCode");

		if (requestDto.getRequestId() != null && !(requestDto.getRequestId().equals(""))) {
			queryString.append(" AND r.requestId=:requestId");
		}

		if (requestDto.getCreatedBy() != null && !(requestDto.getCreatedBy().equals(""))) {
			queryString.append(" AND r.requestCreatedBy=:requestCreatedBy");
		}

		if (requestDto.getCustomerCode() != null && !(requestDto.getCustomerCode().equals(""))) {
			queryString.append(" AND r.soldTo=:soldTo");
		}

		if (requestDto.getCustomerName() != null && !(requestDto.getCustomerName().equals(""))) {
			queryString.append(" AND r.customer like :customer");
		}
		if ((requestDto.getStartDate() != null && !(requestDto.getStartDate().equals("")))
				&& (requestDto.getEndDate() != null) && !(requestDto.getEndDate().equals(""))) {
			queryString.append(" AND r.requestCreatedDate BETWEEN :startDate AND :endDate");
		}

		if (requestDto.getPendingWith() != null && requestDto.getPendingWith().size() > 0) {
			queryString.append(" AND o.orderPendingWith in (:orderPendingWith)");
			queryString.append(" AND o.orderStatus=:orderStatus");
			flag = true;
		}

		if (requestDto.getPrincipalCode() != null && !(requestDto.getPrincipalCode().equals(""))) {
			queryString.append(" AND i.principalCode=:principalCode");
		}
		
		if (requestDto.getBatch() != null && !(requestDto.getBatch().equals(""))) {
			queryString.append(" AND i.batch=:batch");
		}
		
		if (requestDto.getInvoice() != null && !(requestDto.getInvoice().equals(""))) {
			queryString.append(" AND h.invoiceNo=:invoiceNo");
		}
		
		queryString.append(" ORDER BY r.requestId DESC");
//		LOGGER.error("Query for Status details:" + queryString);
		Query query = sessionFactory.getCurrentSession().createQuery(queryString.toString());
		if (requestDto.getRequestId() != null && !(requestDto.getRequestId().equals(""))) {
			query.setParameter("requestId", requestDto.getRequestId());
		}

		if (requestDto.getCreatedBy() != null && !(requestDto.getCreatedBy().equals(""))) {
			query.setParameter("requestCreatedBy", requestDto.getCreatedBy());
		}

		if (requestDto.getCustomerCode() != null && !(requestDto.getCustomerCode().equals(""))) {
			String customerCode = paddingZeros(requestDto.getCustomerCode());
			query.setParameter("soldTo", customerCode);
		}
		if (requestDto.getCustomerName() != null && !(requestDto.getCustomerName().equals(""))) {
			query.setParameter("customer", requestDto.getCustomerName()+"%");
		}
		if (requestDto.getPendingWith() != null && !(requestDto.getPendingWith().equals(""))) {
			query.setParameterList("orderPendingWith", requestDto.getPendingWith());
			query.setParameter("orderStatus", "INPROGRESS");
		}
		if ((requestDto.getStartDate() != null && !(requestDto.getStartDate().equals("")))
				&& (requestDto.getEndDate() != null) && !(requestDto.getEndDate().equals(""))) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				Date d1 = (Date) sdf.parse(requestDto.getStartDate());
				Date d2 = (Date) sdf.parse(requestDto.getEndDate());
				query.setParameter("startDate", d1);
				query.setParameter("endDate", d2);
			} catch (ParseException e) {
				LOGGER.error("Exception On Date format:" + e.getMessage());
			}

		}
		if (requestDto.getPrincipalCode() != null && !(requestDto.getPrincipalCode().equals(""))) {
			String prinicipalCode = paddingZeros(requestDto.getPrincipalCode());
			query.setParameter("principalCode", prinicipalCode);
		}
		
		if (requestDto.getBatch() != null && !(requestDto.getBatch().equals(""))) {
			query.setParameter("batch", requestDto.getBatch());
		}
		
		if (requestDto.getInvoice() != null && !(requestDto.getInvoice().equals(""))) {
			query.setParameter("invoiceNo", requestDto.getInvoice());
		}

		Request request = null;
		@SuppressWarnings("unchecked")
		List<Object[]> objectsList = query.list();
		List<StatusResponseDto> reqList = new ArrayList<>();
		StatusResponseDto statusResponseDto = null;
		ReturnOrder returnOrder = null;
		@SuppressWarnings("unused")
		Header header = null;
		Item item = null;
	
		for (Object[] objects : objectsList) {
			request = (Request) objects[0];
			returnOrder = (ReturnOrder) objects[1];
			header = (Header) objects[2];
			item = (Item) objects[3];
			
			for(ReturnOrder returnOrder2 : request.getSetReturnOrder()) {
				statusResponseDto = new StatusResponseDto();
				for(Item item2 : request.getRequestHeader().getSetItem()) {
					if(returnOrder2.getItemCode().equalsIgnoreCase(item2.getItemCode()) 
							&& returnOrder2.getReturnOrderData().getRequestId().equalsIgnoreCase(request.getRequestId()) 
							&& returnOrder2.getItemCode().equals(returnOrder.getItemCode())
							&& returnOrder.getItemCode().equals(item2.getItemCode())
							&& returnOrder.getItemCode().equalsIgnoreCase(item.getItemCode())) {
							statusResponseDto.setItemCode(item2.getItemCode());
							statusResponseDto.setCreatedBy(returnOrder2.getOrderCreatedBy());
							if (returnOrder2.getOrderCreatedDate() != null && !(returnOrder2.getOrderCreatedDate().equals(""))) {
								
								String output = dateFormat.format(returnOrder2.getOrderCreatedDate());
								statusResponseDto.setCreatedOn(output);
							}
							statusResponseDto.setMaterialCode(item2.getMaterial());
							statusResponseDto.setMaterialDescription(item2.getMaterialDesc());
							if(returnOrder2.getOrderStatus() != null && !(returnOrder2.getOrderStatus().equals(""))
									&& returnOrder2.getOrderStatus().equalsIgnoreCase("COMPLETED")) {
								statusResponseDto.setRequestStatus("Order Posted");
							} else {
								statusResponseDto.setRequestStatus(returnOrder2.getOrderStatus());
							}
							statusResponseDto.setNetQuantity(item2.getAvailableQty());
							statusResponseDto.setNetValue(item2.getNetValue());
							statusResponseDto.setReturnQuantity(returnOrder2.getReturnQty());
							statusResponseDto.setReturnReason(item2.getItemName());// this will change
							statusResponseDto.setRemark(returnOrder2.getRemark());
							statusResponseDto.setReturnType(returnOrder2.getPaymentType());
							statusResponseDto.setReturnValue(returnOrder2.getReturnValue());
							statusResponseDto.setOverrideReturnValue(returnOrder2.getOverrideReturnValue());
							statusResponseDto.setSalesPerson(EReturnConstants.SALES_PERSON);
							statusResponseDto.setOrderComments(returnOrder2.getOrderComments());
							statusResponseDto.setPrincipalCode(item2.getPrincipalCode());
							if(returnOrder2.getReason() != null && !(returnOrder2.getReason().equals("")))
								statusResponseDto.setReason(reasonRepository.getReasonById(item2.getItemName())); // this will change
							else
								statusResponseDto.setReason("");
							statusResponseDto.setBatch(item2.getBatch());
							if(item2.getExpiryDate() != null && !(item2.getExpiryDate().equals("")))
								statusResponseDto.setExpiryDate(dateFormat.format(item2.getExpiryDate()));
							else
								statusResponseDto.setExpiryDate("");
	
							// Request Level
							statusResponseDto.setCurrency(request.getRequestHeader().getCurrency());
							statusResponseDto.setRequestId(request.getRequestId());
							statusResponseDto.setShipTo(request.getShipTo());
							statusResponseDto.setSoldTo(request.getSoldTo());
							statusResponseDto.setInvoiceNo(request.getRequestHeader().getInvoiceNo());
							statusResponseDto.setCustomerName(request.getCustomer());
							statusResponseDto.setCustomerCode(request.getCustomerNo());
							statusResponseDto.setShipToName(request.getShipToName());
							statusResponseDto.setShipToCity(request.getShipToCity());
							statusResponseDto.setShipToStreet(request.getShipToStreet());
							statusResponseDto.setShipToSupply1(request.getShipToSupply1());
							statusResponseDto.setShipToSupply2(request.getShipToSupply2());
							if(returnOrder2.getOrderStatus().equalsIgnoreCase(EReturnConstants.COMPLETE)) {
								statusResponseDto.setEccResponse(request.getEccReturnOrderNo());
							} else {
								statusResponseDto.setEccResponse("");
							}
	
							statusResponseDto.setMessage(EReturnConstants.SUCCESS_STATUS);
							statusResponseDto.setStatus(EReturnConstants.SUCCESS_STATUS);
							if(flag) { 
								if(returnOrder2.getOrderStatus() != null && returnOrder2.getOrderStatus().equalsIgnoreCase("INPROGRESS")) {
									reqList.add(statusResponseDto);
								}
							} else {
								reqList.add(statusResponseDto);
							}
					}
				}
			}
		}
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		Set<StatusResponseDto> set = new TreeSet(new Comparator<StatusResponseDto>() {
			@Override
			public int compare(StatusResponseDto o1, StatusResponseDto o2) {
				if((o1.getRequestId()).equalsIgnoreCase(o2.getRequestId()) &&
						(o1.getItemCode()).equalsIgnoreCase(o2.getItemCode())){
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
		queryString.append("SELECT r FROM Request r WHERE r.unref=:unref");

		Query query = sessionFactory.getCurrentSession().createQuery(queryString.toString());
		query.setParameter("unref", "FALSE");
		@SuppressWarnings("unchecked")
		List<Request> requestList = (List<Request>) query.list();
		for (Request request : requestList) {
			list.add(importExportUtil.exportRequestDto(request));
		}
		return list;
	}

	@Override
	public String getRequestStatus(String requestId) {
		String str = "SELECT r.requestStatus FROM Request r WHERE r.requestId=:requestId";
		Query query = sessionFactory.getCurrentSession().createQuery(str);
		query.setParameter("requestId", requestId);
		String status = (String) query.uniqueResult();
		LOGGER.error("Request status:" + status);
		return status;
	}

	public String getRequestClient(String requestId) {
		String str = "SELECT r.client FROM Request r WHERE r.requestId=:requestId";
		Query query = sessionFactory.getCurrentSession().createQuery(str);
		query.setParameter("requestId", requestId);
		String client = (String) query.uniqueResult();
		return client;
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

	public int updateRequestTrigger(UpdateDto updateDto) {
		String queryStr = "UPDATE Request SET requestPendingWith=:requestPendingWith, requestStatus=:requestStatus, requestApprovedBy=:requestApprovedBy, "
				+ "requestApprovedDate=:requestApprovedDate, eccStatus=:eccStatus, eccReturnOrderNo=:eccReturnOrderNo "
				+ "WHERE requestId=:requestId";
		Query query = sessionFactory.getCurrentSession().createQuery(queryStr);
		query.setParameter("requestPendingWith", updateDto.getPendingWith());
		query.setParameter("requestStatus", updateDto.getStatus());
		query.setParameter("requestApprovedBy", updateDto.getApprovedBy());
		
		if(updateDto.getApprovedDate() != null && !(updateDto.getApprovedDate().equals(""))) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				Date d1 = (Date) sdf.parse(updateDto.getApprovedDate());
				query.setParameter("requestApprovedDate", d1);
			} catch (ParseException e) {
				LOGGER.error("Exception On Date format:" + e.getMessage());
			}
		} else {
			query.setParameter("requestApprovedDate", null);
		}
		query.setParameter("eccStatus", updateDto.getEccStatus());
		query.setParameter("eccReturnOrderNo", updateDto.getEccNo());
		query.setParameter("requestId", updateDto.getRequestId());

		int result = query.executeUpdate();
		return result;

	}
	
	private String paddingZeros(String number) {
		long decimal = Long.parseLong(number);   
		String value = String.format("%010d", decimal);
		return value;
	}
	
	
}
