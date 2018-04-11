package com.incture.zp.ereturns.repositoriesimpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.incture.zp.ereturns.dto.ResponseDto;
import com.incture.zp.ereturns.dto.ReturnOrderDto;
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
		
		sb.append("SELECT r FROM ReturnOrder r WHERE r.requestId = " + requestId);
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());
		LOGGER.error("Query String1:"+ query.getQueryString());

		@SuppressWarnings("unchecked")
		List<ReturnOrder> list = query.list();
		for(ReturnOrder returnOrder : list) {
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
		if(result > 0) {
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
		if(result > 0) {
			responseDto.setMessage("Deleted Successfully");
			responseDto.setStatus("OK");
		} else {
			responseDto.setMessage("Delete Unsuccessfully");
			responseDto.setStatus("ERROR");
		}
		return responseDto;
	}
	
	

}
