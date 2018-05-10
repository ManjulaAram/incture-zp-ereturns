package com.incture.zp.ereturns.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.incture.zp.ereturns.dto.RequestDto;
import com.incture.zp.ereturns.dto.ResponseDto;
import com.incture.zp.ereturns.dto.StatusRequestDto;
import com.incture.zp.ereturns.dto.StatusResponseDto;
import com.incture.zp.ereturns.services.RequestService;
import com.incture.zp.ereturns.services.ReturnOrderService;

@RestController
@CrossOrigin
@ComponentScan("com.incture.zp.ereturns")
@RequestMapping(value = "/request", produces = "application/json")
public class EReturnsRequestController {

	@Autowired
	RequestService requestService;

	@Autowired
	ReturnOrderService returnOrderService;

	@RequestMapping("/hello")
	public String welcome() {// Welcome page, non-rest
		return "Welcome to RestTemplate Example.";
	}
	
	@RequestMapping(value = "/addRequest", method = RequestMethod.POST, consumes = { "application/json" })
	@ResponseBody
	public ResponseDto addRequest(@RequestBody RequestDto requestDto) {
		return requestService.addRequest(requestDto);
	}
	
	@RequestMapping(value = "/updateRequest", method = RequestMethod.POST, consumes = { "application/json" })
	@ResponseBody
	public ResponseDto updateRequest(@RequestBody RequestDto requestDto) {
		return requestService.updateRequestStatus(requestDto);
	}
	
	@RequestMapping(value = "/getStatusDetails", method = RequestMethod.POST, consumes = { "application/json" })
	@ResponseBody
	public StatusResponseDto getStatusDetails(@RequestBody StatusRequestDto requestDto) {
		return requestService.getStatusDetails(requestDto);
	}
	
	@RequestMapping(value = "/deleteReturnOrderByItemCode/{itemCode}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseDto deleteReturnOrderByItemCode(@PathVariable(value = "itemCode") String itemCode) {
		return returnOrderService.deleteReturnOrderByItemCode(itemCode);
	}
	
	@RequestMapping(value = "/deleteReturnOrderByInvoiceNo/{invoiceNo}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseDto deleteReturnOrderByInvoiceNo(@PathVariable(value = "invoiceNo") String invoiceNo) {
		return returnOrderService.deleteReturnOrderByInvoiceNo(invoiceNo);
	}
	
	@RequestMapping(value = "/getRequestById/{requestId}", method = RequestMethod.GET)
	@ResponseBody
	public RequestDto getRequestById(@PathVariable(value = "requestId") String requestId) {
		return requestService.getRequestById(requestId);
	}
}
