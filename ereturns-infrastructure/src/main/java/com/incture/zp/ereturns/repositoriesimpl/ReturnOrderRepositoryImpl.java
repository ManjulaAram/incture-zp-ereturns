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
import com.incture.zp.ereturns.dto.ResponseDto;
import com.incture.zp.ereturns.dto.ReturnOrderDto;
import com.incture.zp.ereturns.dto.StatusPendingDto;
import com.incture.zp.ereturns.dto.StatusResponseDto;
import com.incture.zp.ereturns.dto.UpdateDto;
import com.incture.zp.ereturns.model.Header;
import com.incture.zp.ereturns.model.Item;
import com.incture.zp.ereturns.model.Request;
import com.incture.zp.ereturns.model.ReturnOrder;
import com.incture.zp.ereturns.repositories.ReasonRepository;
import com.incture.zp.ereturns.repositories.ReturnOrderRepository;
import com.incture.zp.ereturns.utils.ImportExportUtil;

@Repository
public class ReturnOrderRepositoryImpl implements ReturnOrderRepository {

	@Autowired
	SessionFactory sessionFactory;

	@Autowired
	ImportExportUtil importExportUtil;

	private static final Logger LOGGER = LoggerFactory.getLogger(ReturnOrderRepositoryImpl.class);
	
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Autowired
	ReasonRepository reasonRepository;
	
	@Override
	public ReturnOrder getReturnOrderById(String id) {
		return (ReturnOrder) sessionFactory.getCurrentSession().get(ReturnOrder.class, id);
	}

	@Override
	public List<ReturnOrderDto> getReturnOrderByRequestId(String requestId) {
		List<ReturnOrderDto> returnOrderDtos = new ArrayList<>();
		StringBuilder sb = new StringBuilder();

		sb.append("SELECT r FROM ReturnOrder r WHERE r.returnOrderData.requestId=:requestId");
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());
		query.setParameter("requestId", requestId);
		LOGGER.error("Query String1:" + query.getQueryString());

		@SuppressWarnings("unchecked")
		List<ReturnOrder> list = query.list();
		for (ReturnOrder returnOrder : list) {
			returnOrderDtos.add(importExportUtil.exportReturnOrderDto(returnOrder));
		}

		return returnOrderDtos;
	}

	@Override
	public ResponseDto deleteReturnOrderByItemCode(String itemCode) {
		ResponseDto responseDto = new ResponseDto();
		String queryStr = "DELETE ReturnOrder WHERE itemCode=:itemCode";
		Query query = sessionFactory.getCurrentSession().createQuery(queryStr);
		query.setParameter("itemCode", itemCode);

		int result = query.executeUpdate();
		if (result > 0) {
			responseDto.setMessage("Deleted Successfully");
			responseDto.setStatus("OK");
		} else {
			responseDto.setMessage("Delete Unsuccessfully");
			responseDto.setStatus("ERROR");
		}
		return responseDto;
	}

	@Override
	public ResponseDto deleteReturnOrderByInvoiceNo(String invoiceNo) {
		ResponseDto responseDto = new ResponseDto();
		String queryStr = "DELETE ReturnOrder WHERE invoiceNo=:invoiceNo";
		Query query = sessionFactory.getCurrentSession().createQuery(queryStr);
		query.setParameter("invoiceNo", invoiceNo);

		int result = query.executeUpdate();
		if (result > 0) {
			responseDto.setMessage("Deleted Successfully");
			responseDto.setStatus("OK");
		} else {
			responseDto.setMessage("Delete Unsuccessfully");
			responseDto.setStatus("ERROR");
		}
		return responseDto;
	}

	@Override
	public StatusPendingDto getRequestStatusByUserId(String userId) {
		List<String> pendingRequest = new ArrayList<>();
		List<String> approvedRequest = new ArrayList<>();
		List<String> rejectedRequest = new ArrayList<>();
		StringBuilder queryString = new StringBuilder();
		queryString.append("SELECT o FROM ReturnOrder o where o.orderCreatedBy=:orderCreatedBy");
		StatusPendingDto statusPendingDto = new StatusPendingDto();
		int pending = 0;
		int approved = 0;
		int rejected = 0;
		Query query = sessionFactory.getCurrentSession().createQuery(queryString.toString());
		query.setParameter("orderCreatedBy", userId);
		@SuppressWarnings("unchecked")
		List<ReturnOrder> retList = (List<ReturnOrder>) query.list();
		for (ReturnOrder returnOrder : retList) {
			if (returnOrder.getOrderStatus().equalsIgnoreCase(EReturnConstants.COMPLETE)) {
				approved = approved + 1;
				approvedRequest.add(returnOrder.getReturnOrderData().getRequestId());
			}
			if (returnOrder.getOrderStatus().equalsIgnoreCase(EReturnConstants.REJECT)) {
				rejected = rejected + 1;
				rejectedRequest.add(returnOrder.getReturnOrderData().getRequestId());
			}
			if (returnOrder.getOrderStatus().equalsIgnoreCase(EReturnConstants.INPROGRESS)) {
				pending = pending + 1;
				pendingRequest.add(returnOrder.getReturnOrderData().getRequestId());
			}
		}
		statusPendingDto.setApproved(approved);
		statusPendingDto.setPending(pending);
		statusPendingDto.setRejected(rejected);
		
		statusPendingDto.setApprovedRequest(approvedRequest);
		statusPendingDto.setPendingRequest(pendingRequest);
		statusPendingDto.setRejectedRequest(rejectedRequest);
		return statusPendingDto;
	}
	
	public List<ReturnOrderDto> getPendingWith(String role) {
		
		List<ReturnOrderDto> returnOrderDtos = new ArrayList<ReturnOrderDto>();
		
		String queryStr = "SELECT o FROM ReturnOrder o where o.orderPendingWith=:orderPendingWith";
		Query query = sessionFactory.getCurrentSession().createQuery(queryStr);
		query.setParameter("orderPendingWith", role);
		@SuppressWarnings("unchecked")
		List<ReturnOrder> reqList = query.list();
		for (ReturnOrder returnOrder : reqList) {
			returnOrderDtos.add(importExportUtil.exportReturnOrderDto(returnOrder));
		}
		return returnOrderDtos;
	}
	
	public int updateReturnOrderTrigger(UpdateDto updateDto) {
		String queryStr = "UPDATE ReturnOrder SET orderPendingWith=:orderPendingWith, orderStatus=:orderStatus, orderApprovedBy=:orderApprovedBy, "
				+ "orderApprovedDate=:orderApprovedDate "
				+ "WHERE returnOrderData.requestId=:requestId AND itemCode=:itemCode";
		Query query = sessionFactory.getCurrentSession().createQuery(queryStr);
		query.setParameter("orderPendingWith", updateDto.getPendingWith());
		query.setParameter("orderStatus", updateDto.getStatus());
		query.setParameter("orderApprovedBy", updateDto.getApprovedBy());
		if(updateDto.getApprovedDate() != null && !(updateDto.getApprovedDate().equals(""))) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				Date d1 = (Date) sdf.parse(updateDto.getApprovedDate());
				query.setParameter("orderApprovedDate", d1);
			} catch (ParseException e) {
				LOGGER.error("Exception On Date format:" + e.getMessage());
			}
		} else {
			query.setParameter("orderApprovedDate", null);
		}
		query.setParameter("requestId", updateDto.getRequestId());
		query.setParameter("itemCode", updateDto.getItemCode());
		
		int result = query.executeUpdate();
		return result;

	}

	@Override
	public List<StatusResponseDto> getRequestorDashboardList(String createdBy, String status) {
		int pending = 0;
		int approved = 0;
		int rejected = 0;
		List<StatusResponseDto> list = getRequestorList(createdBy, "");
		List<StatusResponseDto> modifiedPending = new ArrayList<>();
		List<StatusResponseDto> modifiedApproved = new ArrayList<>();
		List<StatusResponseDto> modifiedRejected = new ArrayList<>();
		for(int i = 0 ; i < list.size() ; i++) {
			StatusResponseDto obj = list.get(i);
			if (obj.getRequestStatus().equalsIgnoreCase(EReturnConstants.COMPLETE)) {
				approved = approved + 1;
				modifiedApproved.add(obj);
			}
			if (obj.getRequestStatus().equalsIgnoreCase(EReturnConstants.REJECT)) {
				rejected = rejected + 1;
				modifiedRejected.add(obj);
			}
			if (obj.getRequestStatus().equalsIgnoreCase(EReturnConstants.INPROGRESS)) {
				pending = pending + 1;
				modifiedPending.add(obj);
			}
		}
		if(status.equalsIgnoreCase("PENDING")) {
			return modifiedPending;
		} else if(status.equalsIgnoreCase("APPROVED")) {
			return modifiedApproved;
		} else if(status.equalsIgnoreCase("REJECTED")) {
			return modifiedRejected;
		}
		return null;
	}


	public List<StatusResponseDto> getRequestorList(String createdBy, String role) {
		boolean flag = false;
		StringBuilder queryString = new StringBuilder();
		queryString
				.append("SELECT r, o, h, i FROM Request r, ReturnOrder o, Header h, Item i WHERE r.requestId = o.returnOrderData.requestId "
						+ "AND r.requestHeader.headerId = h.headerId AND h.headerId = i.itemData.headerId AND o.itemCode = i.itemCode");

		if (createdBy != null && !(createdBy.equals(""))) {
			queryString.append(" AND o.orderCreatedBy=:orderCreatedBy");
		}
		if (role != null && !(role.equals(""))) {
			queryString.append(" AND o.orderPendingWith=:orderPendingWith");
		}
		
		queryString.append(" ORDER BY r.requestId DESC");
		Query query = sessionFactory.getCurrentSession().createQuery(queryString.toString());
		if (createdBy != null && !(createdBy.equals(""))) {
			query.setParameter("orderCreatedBy", createdBy);
		}
		if (role != null && !(role.equals(""))) {
			query.setParameter("orderPendingWith", role);
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
							statusResponseDto.setRequestStatus(returnOrder2.getOrderStatus());
							statusResponseDto.setNetQuantity(item2.getAvailableQty());
							statusResponseDto.setNetValue(item2.getNetValue());
							statusResponseDto.setReturnQuantity(returnOrder2.getReturnQty());
							statusResponseDto.setReturnReason(item2.getItemName());// this will change
							statusResponseDto.setRemark(returnOrder2.getRemark());
							statusResponseDto.setReturnType(returnOrder2.getPaymentType());
							statusResponseDto.setReturnValue(returnOrder2.getReturnValue());
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

}
