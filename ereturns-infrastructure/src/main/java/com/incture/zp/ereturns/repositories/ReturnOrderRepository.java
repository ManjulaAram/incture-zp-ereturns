package com.incture.zp.ereturns.repositories;

import java.util.List;

import com.incture.zp.ereturns.dto.ResponseDto;
import com.incture.zp.ereturns.dto.ReturnOrderDto;
import com.incture.zp.ereturns.model.ReturnOrder;

public interface ReturnOrderRepository {

	public ReturnOrder getReturnOrderById(String id);
	
	public List<ReturnOrderDto> getReturnOrderByRequestId(String requestId);
	
	public ResponseDto deleteReturnOrderByItemCode(String itemCode);
	
	public ResponseDto deleteReturnOrderByInvoiceNo(String invoiceNo);
}
