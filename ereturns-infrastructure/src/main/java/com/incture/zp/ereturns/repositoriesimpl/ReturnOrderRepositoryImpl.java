package com.incture.zp.ereturns.repositoriesimpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.incture.zp.ereturns.dto.UpdateDto;
import com.incture.zp.ereturns.model.ReturnOrder;
import com.incture.zp.ereturns.repositories.ReturnOrderRepository;
import com.incture.zp.ereturns.utils.ImportExportUtil;

@Repository
public class ReturnOrderRepositoryImpl implements ReturnOrderRepository {

	@Autowired
	SessionFactory sessionFactory;

	@Autowired
	ImportExportUtil importExportUtil;

	private static final Logger LOGGER = LoggerFactory.getLogger(HeaderRepositoryImpl.class);

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
			}
			if (returnOrder.getOrderStatus().equalsIgnoreCase(EReturnConstants.REJECT)) {
				rejected = rejected + 1;
			}
			if (returnOrder.getOrderStatus().equalsIgnoreCase(EReturnConstants.INPROGRESS)) {
				pending = pending + 1;
			}
		}
		statusPendingDto.setApproved(approved);
		statusPendingDto.setPending(pending);
		statusPendingDto.setRejected(rejected);
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


}
