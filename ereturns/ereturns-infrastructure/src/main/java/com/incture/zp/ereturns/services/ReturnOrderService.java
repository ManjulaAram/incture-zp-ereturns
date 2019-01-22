package com.incture.zp.ereturns.services;

import java.util.List;

import com.incture.zp.ereturns.dto.PriceOverrideDto;
import com.incture.zp.ereturns.dto.ResponseDto;
import com.incture.zp.ereturns.dto.ReturnOrderDto;
import com.incture.zp.ereturns.dto.StatusPendingDto;
import com.incture.zp.ereturns.dto.StatusResponseDto;
import com.incture.zp.ereturns.model.ReturnOrder;

public interface ReturnOrderService {

	public ReturnOrder getReturnOrderById(String id);
	
	public List<ReturnOrderDto> getReturnOrderByRequestId(String requestId);
	
	public ResponseDto deleteReturnOrderByItemCode(String itemCode);
	
	public ResponseDto deleteReturnOrderByInvoiceNo(String invoiceNo);
	
	public StatusPendingDto getRequestStatusByUserId(String userId);
	
	public List<StatusResponseDto> getRequestorDashboardList(String createdBy, String status);
	
	public int updatePriceOverride(PriceOverrideDto priceOverrideDto);

}
