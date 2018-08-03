package com.incture.zp.ereturns.repositories;

import java.util.List;

import com.incture.zp.ereturns.dto.ResponseDto;
import com.incture.zp.ereturns.dto.ReturnOrderDto;
import com.incture.zp.ereturns.dto.StatusPendingDto;
import com.incture.zp.ereturns.dto.StatusResponseDto;
import com.incture.zp.ereturns.dto.UpdateDto;
import com.incture.zp.ereturns.model.ReturnOrder;

public interface ReturnOrderRepository {

	public ReturnOrder getReturnOrderById(String id);
	
	public List<ReturnOrderDto> getReturnOrderByRequestId(String requestId);
	
	public ResponseDto deleteReturnOrderByItemCode(String itemCode);
	
	public ResponseDto deleteReturnOrderByInvoiceNo(String invoiceNo);
	
	public StatusPendingDto getRequestStatusByUserId(String userId);
	
	public List<ReturnOrderDto> getPendingWith(String role);
	
	public int updateReturnOrderTrigger(UpdateDto updateDto);
	
	//this is for dash board api's
	
	public List<StatusResponseDto> getRequestorDashboardList(String createdBy, String status);
	
	public List<StatusResponseDto> getRequestorList(String createdBy, String role);
	
}
