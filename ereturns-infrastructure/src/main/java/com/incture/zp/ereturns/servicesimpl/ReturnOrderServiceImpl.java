package com.incture.zp.ereturns.servicesimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incture.zp.ereturns.dto.ResponseDto;
import com.incture.zp.ereturns.dto.ReturnOrderDto;
import com.incture.zp.ereturns.model.ReturnOrder;
import com.incture.zp.ereturns.services.ReturnOrderService;

@Service
@Transactional
public class ReturnOrderServiceImpl implements ReturnOrderService {

	@Autowired
	ReturnOrderService returnOrderService;
	
	@Override
	public ReturnOrder getReturnOrderById(String id) {
		return returnOrderService.getReturnOrderById(id);
	}

	@Override
	public List<ReturnOrderDto> getReturnOrderByRequestId(String requestId) {
		return returnOrderService.getReturnOrderByRequestId(requestId);
	}

	@Override
	public ResponseDto deleteReturnOrderByItemCode(String itemCode) {
		return returnOrderService.deleteReturnOrderByItemCode(itemCode);
	}

	@Override
	public ResponseDto deleteReturnOrderByInvoiceNo(String invoiceNo) {
		return returnOrderService.deleteReturnOrderByInvoiceNo(invoiceNo);
	}

}
