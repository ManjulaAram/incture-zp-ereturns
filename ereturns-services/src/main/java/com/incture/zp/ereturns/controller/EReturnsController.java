package com.incture.zp.ereturns.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.incture.zp.ereturns.dto.HeaderDto;
import com.incture.zp.ereturns.dto.RequestDto;
import com.incture.zp.ereturns.dto.ResponseDto;
import com.incture.zp.ereturns.dto.SearchDto;
import com.incture.zp.ereturns.dto.SearchResultDto;
import com.incture.zp.ereturns.dto.StatusRequestDto;
import com.incture.zp.ereturns.dto.StatusResponseDto;
import com.incture.zp.ereturns.dto.UserDto;
import com.incture.zp.ereturns.services.HeaderService;
import com.incture.zp.ereturns.services.RequestService;
import com.incture.zp.ereturns.services.ReturnOrderService;
import com.incture.zp.ereturns.services.UserService;

@RestController
@CrossOrigin
@ComponentScan("com.incture.zp.ereturns")
public class EReturnsController {

	@Autowired
	UserService userService;
	
	@Autowired 
	HeaderService headerService;
	
	@Autowired
	RequestService requestService;

	@Autowired
	ReturnOrderService returnOrderService;

	@RequestMapping("/hello")
	public String welcome() {// Welcome page, non-rest
		return "Welcome to RestTemplate Example.";
	}
	
	@RequestMapping(value = "/addUser", method = RequestMethod.POST, consumes = { "application/JSON" }, produces = { "application/json" })
	@ResponseBody
	public void addUser(@RequestBody UserDto userDto) {
		userService.addUser(userDto);
	}
	
	@RequestMapping(value = "/getSearchResult", method = RequestMethod.POST, consumes = { "application/JSON" }, produces = { "application/json" })
	@ResponseBody
	public List<SearchResultDto> getSearchResult(@RequestBody SearchDto searchDto) {
		return headerService.getSearchResult(searchDto);
	}
	
	@RequestMapping(value = "/getInvoice/{id}", method = RequestMethod.GET, produces = { "application/json" })
	@ResponseBody
	public HeaderDto getInvoiceById(@PathVariable(value = "id") String id) {
		return headerService.getHeaderById(id);
	}
	
	@RequestMapping(value = "/addRequest", method = RequestMethod.POST, consumes = { "application/JSON" }, produces = { "application/json" })
	@ResponseBody
	public ResponseDto addRequest(@RequestBody RequestDto requestDto) {
		return requestService.addRequest(requestDto);
	}
	
	@RequestMapping(value = "/updateRequest", method = RequestMethod.POST, consumes = { "application/JSON" }, produces = { "application/json" })
	@ResponseBody
	public ResponseDto updateRequest(@RequestBody RequestDto requestDto) {
		return requestService.updateRequestStatus(requestDto);
	}
	
	@RequestMapping(value = "/getStatusDetails", method = RequestMethod.POST, consumes = { "application/JSON" }, produces = { "application/json" })
	@ResponseBody
	public List<StatusResponseDto> getStatusDetails(@RequestBody StatusRequestDto requestDto) {
		return requestService.getStatusDetails(requestDto);
	}
	
	@RequestMapping(value = "/deleteReturnOrderByItemCode/{itemCode}", method = RequestMethod.DELETE, produces = { "application/json" })
	@ResponseBody
	public ResponseDto deleteReturnOrderByItemCode(@PathVariable(value = "itemCode") String itemCode) {
		return returnOrderService.deleteReturnOrderByItemCode(itemCode);
	}
	
	@RequestMapping(value = "/deleteReturnOrderByInvoiceNo/{invoiceNo}", method = RequestMethod.DELETE, produces = { "application/json" })
	@ResponseBody
	public ResponseDto deleteReturnOrderByInvoiceNo(@PathVariable(value = "invoiceNo") String invoiceNo) {
		return returnOrderService.deleteReturnOrderByInvoiceNo(invoiceNo);
	}
}
