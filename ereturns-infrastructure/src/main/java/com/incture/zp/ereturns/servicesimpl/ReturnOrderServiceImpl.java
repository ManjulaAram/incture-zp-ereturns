package com.incture.zp.ereturns.servicesimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incture.zp.ereturns.dto.ResponseDto;
import com.incture.zp.ereturns.dto.ReturnOrderDto;
import com.incture.zp.ereturns.dto.StatusPendingDto;
import com.incture.zp.ereturns.model.ReturnOrder;
import com.incture.zp.ereturns.repositories.ReturnOrderRepository;
import com.incture.zp.ereturns.services.ReturnOrderService;

@Service
@Transactional
public class ReturnOrderServiceImpl implements ReturnOrderService {

	@Autowired
	ReturnOrderRepository returnOrderRepository;
	
	@Override
	public ReturnOrder getReturnOrderById(String id) {
		return returnOrderRepository.getReturnOrderById(id);
	}

	@Override
	public List<ReturnOrderDto> getReturnOrderByRequestId(String requestId) {
		return returnOrderRepository.getReturnOrderByRequestId(requestId);
	}

	@Override
	public ResponseDto deleteReturnOrderByItemCode(String itemCode) {
		return returnOrderRepository.deleteReturnOrderByItemCode(itemCode);
	}

	@Override
	public ResponseDto deleteReturnOrderByInvoiceNo(String invoiceNo) {
		return returnOrderRepository.deleteReturnOrderByInvoiceNo(invoiceNo);
	}

	@Override
	public StatusPendingDto getRequestStatusByUserId(String userId) {
		return returnOrderRepository.getRequestStatusByUserId(userId);
	}

}
