package com.incture.zp.ereturns.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.incture.zp.ereturns.dto.HeaderDto;
import com.incture.zp.ereturns.dto.ItemDto;
import com.incture.zp.ereturns.dto.RequestDto;
import com.incture.zp.ereturns.dto.ReturnOrderDto;
import com.incture.zp.ereturns.dto.UserDto;
import com.incture.zp.ereturns.model.Header;
import com.incture.zp.ereturns.model.Item;
import com.incture.zp.ereturns.model.Request;
import com.incture.zp.ereturns.model.ReturnOrder;
import com.incture.zp.ereturns.model.User;

@Component
public class ImportExportUtil {

	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final Logger LOGGER = LoggerFactory.getLogger(ImportExportUtil.class);
	
	public User importUserDto(UserDto userDto) {
		User user = new User();
		user.setAddress(userDto.getAddress());
		user.setEmail(userDto.getEmail());
		user.setSciId(userDto.getSciId());
		user.setUserCode(userDto.getUserCode());
		user.setUserId(userDto.getUserId());
		user.setUserName(userDto.getUserName());
		
		Set<Header> headerSet = null;
		if (userDto.getHeaderSet() != null && userDto.getHeaderSet().size() > 0) {
			headerSet = setHeaderDetail(userDto.getHeaderSet(), user);
		}
		user.setSetHeader(headerSet);
		return user;
	}
	
	private Set<Header> setHeaderDetail(Set<HeaderDto> headerSet, User user) {
		Set<Header> setHeader = new HashSet<>();
		
		for(HeaderDto headerDto : headerSet) {
			setHeader.add(importHeaderDto(headerDto, user));
		}
		return setHeader;
	}

	private Set<ReturnOrder> setReturnOrderDetail(Set<ReturnOrderDto> returnOrderSet, Request request) {
		Set<ReturnOrder> setReturnOrder = new HashSet<>();
		
		for(ReturnOrderDto returnOrderDto : returnOrderSet) {
			setReturnOrder.add(importReturnOrderDto(returnOrderDto, request));
		}
		return setReturnOrder;
	}

	public UserDto exportUserDto(User user) {
		UserDto userDto = new UserDto();
		userDto.setAddress(user.getAddress());
		userDto.setEmail(user.getEmail());
		
		Set<Header> headers = user.getSetHeader();
		HeaderDto headerDto = null;
		Set<HeaderDto> headerSet = new HashSet<>();
		for(Header header : headers) {
			headerDto = exportHeaderDto(header);
			headerSet.add(headerDto);
		}
		userDto.setHeaderSet(headerSet);
		userDto.setSciId(user.getSciId());
		userDto.setUserCode(user.getUserCode());
		userDto.setUserId(user.getUserId());
		userDto.setUserName(user.getUserName());
		
		return userDto;
	}
	
	public Header importHeaderDto(HeaderDto headerDto, User user) {
		Header header = new Header();
		header.setAvailableQty(headerDto.getAvailableQty());
		if(headerDto.getExpiryDate() != null && !(headerDto.getExpiryDate().equals(""))) {
			header.setExpiryDate(convertStringToDate(headerDto.getExpiryDate()));
		}
		header.setHeaderData(user);
		if(headerDto.getInvoiceDate() != null && !(headerDto.getInvoiceDate().equals(""))) {
			header.setInvoiceDate(convertStringToDate(headerDto.getInvoiceDate()));
		}
		header.setInvoiceNo(headerDto.getInvoiceNo());
		header.setInvoiceSeq(headerDto.getInvoiceSeq());
		header.setNetValue(headerDto.getNetValue());
		header.setDocumentType(headerDto.getDocumentType());
		header.setSalesOrder(headerDto.getSalesOrder());
		Set<Item> itemSet = null;
		if (headerDto.getItemSet() != null && headerDto.getItemSet().size() > 0) {
			itemSet = setItemDetail(headerDto.getItemSet(), header);
		}
		header.setSetItem(itemSet);
		
		return header;
	}
	
	public HeaderDto exportHeaderDto(Header header) {
		HeaderDto headerDto = new HeaderDto();
		headerDto.setAvailableQty(header.getAvailableQty());
		if(header.getExpiryDate() != null && !(header.getExpiryDate().equals(""))) {
			headerDto.setExpiryDate(convertDateToString(header.getExpiryDate()));
		}
		LOGGER.error("Date coming from DB:"+header.getExpiryDate());
		if(header.getInvoiceDate() != null && !(header.getInvoiceDate().equals(""))) {
			headerDto.setInvoiceDate(convertDateToString(header.getInvoiceDate()));
		}
		headerDto.setInvoiceNo(header.getInvoiceNo());
		headerDto.setInvoiceSeq(header.getInvoiceSeq());
		Set<Item> items = header.getSetItem();
		Set<ItemDto> setItemsDto = new HashSet<>();
		ItemDto itemDto = null;
		for(Item item : items) {
			itemDto = exportItemDto(item);
			setItemsDto.add(itemDto);
		}
		headerDto.setItemSet(setItemsDto);
		headerDto.setNetValue(header.getNetValue());
		headerDto.setDocumentType(header.getDocumentType());
		headerDto.setSalesOrder(header.getSalesOrder());
		
		return headerDto;
	}
	
	private Set<Item> setItemDetail(Set<ItemDto> itemSet, Header header) {
		Set<Item> setItem = new HashSet<>();
		
		for(ItemDto itemDto : itemSet) {
			setItem.add(importItemDto(itemDto, header));
		}
		return setItem;
	}
	
	public Item importItemDto(ItemDto itemDto, Header header) {
		Item item = new Item();
		item.setAvailableQty(itemDto.getAvailableQty());
		if(itemDto.getExpiryDate() != null && !(itemDto.getExpiryDate().equals(""))) {
			item.setExpiryDate(convertStringToDate(itemDto.getExpiryDate()));
		}
		item.setItemCode(itemDto.getItemCode());
		item.setItemData(header);
		item.setItemDescription(itemDto.getItemDescription());
		item.setItemName(itemDto.getItemName());
		item.setNetValue(itemDto.getNetValue());
		if(itemDto.getDeliveryDate() != null && !(itemDto.getDeliveryDate().equals(""))) {
			item.setDeliveryDate(convertStringToDate(itemDto.getDeliveryDate()));
		}
		
		return item;
	}
	
	public ItemDto exportItemDto(Item item) {
		ItemDto itemDto = new ItemDto();
		itemDto.setAvailableQty(item.getAvailableQty());
		if(item.getExpiryDate() != null && !(item.getExpiryDate().equals(""))) {
			itemDto.setExpiryDate(convertDateToString(item.getExpiryDate()));
		}
		itemDto.setItemCode(item.getItemCode());
		itemDto.setItemDescription(item.getItemDescription());
		itemDto.setItemName(item.getItemName());
		itemDto.setNetValue(item.getNetValue());
		if(item.getDeliveryDate() != null && !(item.getDeliveryDate().equals(""))) {
			itemDto.setDeliveryDate(convertDateToString(item.getDeliveryDate()));
		}
		
		return itemDto;
	}

	public Request importRequestDto(RequestDto requestDto) {
		Request request = new Request();
		request.setBoxQty(requestDto.getBoxQty());
		request.setLocation(requestDto.getLocation());
		request.setRequestApprovedBy(requestDto.getRequestApprovedBy());
		if(requestDto.getRequestApprovedDate() != null && !(requestDto.getRequestApprovedDate().equals(""))) {
			request.setRequestApprovedDate(convertStringToDate(requestDto.getRequestApprovedDate()));
		}
		request.setRequestCreatedBy(requestDto.getRequestCreatedBy());
		
		if(requestDto.getRequestCreatedDate() != null && !(requestDto.getRequestCreatedDate().equals(""))) {
			request.setRequestCreatedDate(convertStringToDate(requestDto.getRequestCreatedDate()));
		}
		request.setRequestId(requestDto.getRequestId());
		// define rule based on reason and return value
		request.setRequestPendingWith(requestDto.getRequestPendingWith());
		request.setRequestStatus(requestDto.getRequestStatus());
		request.setRequestUpdatedBy(requestDto.getRequestUpdatedBy());
		
		if(requestDto.getRequestUpdatedDate() != null && !(requestDto.getRequestUpdatedDate().equals(""))) {
			request.setRequestUpdatedDate(convertStringToDate(requestDto.getRequestUpdatedDate()));
		}
		request.setLotNo(requestDto.getLotNo());
		request.setSalesPerson(requestDto.getSalesPerson());
		
		Set<ReturnOrder> returnOrderSet = null;
		if (requestDto.getSetReturnOrderDto() != null && requestDto.getSetReturnOrderDto().size() > 0) {
			returnOrderSet = setReturnOrderDetail(requestDto.getSetReturnOrderDto(), request);
		}

		request.setUnRef(requestDto.getUnRef());
		request.setSetReturnOrder(returnOrderSet);
		return request;
	}
	
	public RequestDto exportRequestDto(Request request) {
		RequestDto requestDto = new RequestDto();
		requestDto.setBoxQty(request.getBoxQty());
		requestDto.setLocation(request.getLocation());
		requestDto.setLotNo(request.getLotNo());
		requestDto.setNoOfLine("");
		requestDto.setRequestApprovedBy(request.getRequestApprovedBy());
		if(request.getRequestApprovedDate() != null) {
			requestDto.setRequestApprovedDate(convertDateToString(request.getRequestApprovedDate()));
		} else {
			requestDto.setRequestApprovedDate("");
		}
		requestDto.setRequestCreatedBy(request.getRequestCreatedBy());
		if(request.getRequestCreatedDate() != null) {
			requestDto.setRequestCreatedDate(convertDateToString(request.getRequestCreatedDate()));
		} else {
			requestDto.setRequestCreatedDate("");
		}
		requestDto.setRequestId(request.getRequestId());
		requestDto.setRequestPendingWith(request.getRequestPendingWith());
		requestDto.setRequestStatus(request.getRequestStatus());
		if(request.getRequestUpdatedDate() != null) {
			requestDto.setRequestUpdatedDate(convertDateToString(request.getRequestUpdatedDate()));
		} else {
			requestDto.setRequestUpdatedDate("");
		}
		requestDto.setRequestUpdatedBy(request.getRequestUpdatedBy());
		requestDto.setSalesPerson("BOM");
		requestDto.setSalesPersonName("BOM");
		requestDto.setUnRef(request.getUnRef());
		
		Set<ReturnOrder> returnOrders = request.getSetReturnOrder();
		ReturnOrderDto returnOrderDto = null;
		Set<ReturnOrderDto> returnOrderSet = new HashSet<>();
		for(ReturnOrder returnOrder : returnOrders) {
			returnOrderDto = exportReturnOrderDto(returnOrder);
			returnOrderSet.add(returnOrderDto);
		}
		requestDto.setSetReturnOrderDto(returnOrderSet);
		
		return requestDto;
	}
	
	public ReturnOrder importReturnOrderDto(ReturnOrderDto returnOrderDto, Request request) {
		ReturnOrder returnOrder = new ReturnOrder();
		returnOrder.setReturnOrderData(request);
		returnOrder.setInvoiceNo(returnOrderDto.getInvoiceNo());
		returnOrder.setItemCode(returnOrderDto.getItemCode());
		returnOrder.setReason(returnOrderDto.getReason());
		returnOrder.setRemark(returnOrderDto.getRemark());
		
		returnOrder.setReturnEntireOrder(returnOrderDto.getReturnEntireOrder());
		returnOrder.setReturnOrderId(returnOrderDto.getReturnOrderId());
		returnOrder.setReturnPrice(returnOrderDto.getReturnPrice());
		returnOrder.setReturnQty(returnOrderDto.getReturnQty());
		returnOrder.setReturnValue(returnOrderDto.getReturnValue());
		
		returnOrder.setUserCode(returnOrderDto.getUserCode());
		
		return returnOrder;
	}
	
	public ReturnOrderDto exportReturnOrderDto(ReturnOrder returnOrder) {
		ReturnOrderDto returnOrderDto = new ReturnOrderDto();
		
		returnOrderDto.setInvoiceNo(returnOrder.getInvoiceNo());
		returnOrderDto.setItemCode(returnOrder.getItemCode());
		returnOrderDto.setReason(returnOrder.getReason());
		returnOrderDto.setRemark(returnOrder.getRemark());
		returnOrderDto.setReturnEntireOrder(returnOrder.getReturnEntireOrder());
		returnOrderDto.setReturnOrderId(returnOrder.getReturnOrderId());
		returnOrderDto.setReturnPrice(returnOrder.getReturnPrice());
		returnOrderDto.setReturnQty(returnOrder.getReturnQty());
		returnOrderDto.setReturnValue(returnOrder.getReturnValue());
		returnOrderDto.setUserCode(returnOrder.getUserCode());
		
		return returnOrderDto;
	}
	
	private String convertDateToString(Date input) {
		String output = dateFormat.format(input);
		return output;
	}
	
	private Date convertStringToDate(String input) {
		Date output = null;
		try {
			output = dateFormat.parse(input);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return output;
	}
}
