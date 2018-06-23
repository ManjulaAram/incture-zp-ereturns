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

import com.incture.zp.ereturns.dto.AttachmentDto;
import com.incture.zp.ereturns.dto.CustomerDto;
import com.incture.zp.ereturns.dto.HeaderDto;
import com.incture.zp.ereturns.dto.ItemDto;
import com.incture.zp.ereturns.dto.RequestDto;
import com.incture.zp.ereturns.dto.ReturnOrderDto;
import com.incture.zp.ereturns.dto.ReasonDto;
import com.incture.zp.ereturns.dto.UserDto;
import com.incture.zp.ereturns.dto.WorkFlowDto;
import com.incture.zp.ereturns.model.Attachment;
import com.incture.zp.ereturns.model.Customer;
import com.incture.zp.ereturns.model.Header;
import com.incture.zp.ereturns.model.Item;
import com.incture.zp.ereturns.model.Request;
import com.incture.zp.ereturns.model.ReturnOrder;
import com.incture.zp.ereturns.model.Reason;
import com.incture.zp.ereturns.model.User;
import com.incture.zp.ereturns.model.WorkFlow;

@Component
public class ImportExportUtil {

	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final Logger LOGGER = LoggerFactory.getLogger(ImportExportUtil.class);

	public User importUserDto(UserDto userDto) {
		User user = new User();
		user.setSciUserId(userDto.getSciUserId());
		user.setIdpUserId(userDto.getIdpUserId());
		user.setMobileToken(userDto.getMobileToken());
		return user;
	}

	private Set<ReturnOrder> setReturnOrderDetail(Set<ReturnOrderDto> returnOrderSet, Request request) {
		Set<ReturnOrder> setReturnOrder = new HashSet<>();

		for (ReturnOrderDto returnOrderDto : returnOrderSet) {
			setReturnOrder.add(importReturnOrderDto(returnOrderDto, request));
		}
		return setReturnOrder;
	}

	public UserDto exportUserDto(User user) {
		UserDto userDto = new UserDto();
		userDto.setIdpUserId(user.getIdpUserId());
		userDto.setSciUserId(user.getSciUserId());
		userDto.setMobileToken(user.getMobileToken());
		return userDto;
	}

	public Header importHeaderDto(HeaderDto headerDto) {
		Header header = new Header();
		if (headerDto.getInvoiceDate() != null && !(headerDto.getInvoiceDate().equals(""))) {
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
		header.setCurrency(headerDto.getCurrency());
		header.setDeliveryNo(headerDto.getDeliveryNo());
		header.setDistrChan(headerDto.getDistrChan());
		header.setDivision(headerDto.getDivision());
		header.setSalesOrg(headerDto.getSalesOrg());
		header.setPurchNoCust(headerDto.getPurchNoCust());
		header.setRefDocCat(headerDto.getRefDocCat());
		header.setSalesOrg(headerDto.getSalesOrg());
		header.setHeaderId(headerDto.getHeaderId());

		return header;
	}

	public HeaderDto exportHeaderDto(Header header) {
		HeaderDto headerDto = new HeaderDto();
		if (header.getInvoiceDate() != null && !(header.getInvoiceDate().equals(""))) {
			headerDto.setInvoiceDate(convertDateToString(header.getInvoiceDate()));
		}
		headerDto.setInvoiceNo(header.getInvoiceNo());
		headerDto.setInvoiceSeq(header.getInvoiceSeq());
		Set<Item> items = header.getSetItem();
		Set<ItemDto> setItemsDto = new HashSet<>();
		ItemDto itemDto = null;
		for (Item item : items) {
			itemDto = exportItemDto(item);
			setItemsDto.add(itemDto);
		}
		headerDto.setItemSet(setItemsDto);
		headerDto.setNetValue(header.getNetValue());
		headerDto.setDocumentType(header.getDocumentType());
		headerDto.setSalesOrder(header.getSalesOrder());
		headerDto.setCurrency(header.getCurrency());
		headerDto.setDeliveryNo(header.getDeliveryNo());
		headerDto.setDivision(header.getDivision());
		headerDto.setDistrChan(header.getDistrChan());
		headerDto.setRefDocCat(header.getRefDocCat());
		headerDto.setPurchNoCust(header.getPurchNoCust());
		headerDto.setSalesOrg(header.getSalesOrg());
		headerDto.setHeaderId(header.getHeaderId());

		return headerDto;
	}

	private Set<Item> setItemDetail(Set<ItemDto> itemSet, Header header) {
		Set<Item> setItem = new HashSet<>();

		for (ItemDto itemDto : itemSet) {
			setItem.add(importItemDto(itemDto, header));
		}
		return setItem;
	}

	public Item importItemDto(ItemDto itemDto, Header header) {
		Item item = new Item();
		item.setAvailableQty(itemDto.getAvailableQty());
		if (itemDto.getExpiryDate() != null && !(itemDto.getExpiryDate().equals(""))) {
			LOGGER.error("Expiry date from UI"+itemDto.getExpiryDate());
			item.setExpiryDate(convertStringToDate(itemDto.getExpiryDate()));
		}
		item.setItemCode(itemDto.getItemCode());
		item.setItemData(header);
		item.setItemDescription(itemDto.getItemDescription());
		item.setItemName(itemDto.getItemName());
		item.setNetValue(itemDto.getNetValue());
		if (itemDto.getDeliveryDate() != null && !(itemDto.getDeliveryDate().equals(""))) {
			item.setDeliveryDate(convertStringToDate(itemDto.getDeliveryDate()));
		}

		item.setBatch(itemDto.getBatch());
		item.setDeliveryDocItem(itemDto.getDeliveryDocItem());
		item.setMaterial(itemDto.getMaterial());
		item.setMaterialDesc(itemDto.getMaterialDesc());
		item.setPlant(itemDto.getPlant());
		item.setSalesOrderItem(itemDto.getSalesOrderItem());
		item.setStoreLoc(itemDto.getStoreLoc());
		item.setItemId(itemDto.getItemId());
		item.setPrincipal(itemDto.getPrincipal());
		item.setPrincipalCode(itemDto.getPrincipalCode());
		item.setMaterialGroup(itemDto.getMaterialGroup());
		item.setPrincipalGroup(itemDto.getPrincipalGroup());
		item.setStoreType(itemDto.getStoreType());

		return item;
	}

	public ItemDto exportItemDto(Item item) {
		ItemDto itemDto = new ItemDto();
		itemDto.setAvailableQty(item.getAvailableQty());
		if (item.getExpiryDate() != null && !(item.getExpiryDate().equals(""))) {
			itemDto.setExpiryDate(convertDateToString(item.getExpiryDate()));
		}
		itemDto.setItemCode(item.getItemCode());
		itemDto.setItemDescription(item.getItemDescription());
		itemDto.setItemName(item.getItemName());
		itemDto.setNetValue(item.getNetValue());
		if (item.getDeliveryDate() != null && !(item.getDeliveryDate().equals(""))) {
			itemDto.setDeliveryDate(convertDateToString(item.getDeliveryDate()));
		}

		itemDto.setBatch(item.getBatch());
		itemDto.setDeliveryDocItem(item.getDeliveryDocItem());
		itemDto.setMaterial(item.getMaterial());
		itemDto.setMaterialDesc(item.getMaterialDesc());
		itemDto.setPlant(item.getPlant());
		itemDto.setSalesOrderItem(item.getSalesOrderItem());
		itemDto.setStoreLoc(item.getStoreLoc());
		itemDto.setItemId(item.getItemId());
		itemDto.setMaterialGroup(item.getMaterialGroup());
		itemDto.setPrincipal(item.getPrincipal());
		itemDto.setPrincipalCode(item.getPrincipalCode());
		
		itemDto.setPrincipalGroup(item.getPrincipalGroup());
		itemDto.setStoreType(item.getStoreType());


		return itemDto;
	}

	public Request importRequestDto(RequestDto requestDto) {
		Request request = new Request();
		request.setRequestApprovedBy(requestDto.getRequestApprovedBy());
		if (requestDto.getRequestApprovedDate() != null && !(requestDto.getRequestApprovedDate().equals(""))) {
			request.setRequestApprovedDate(convertStringToDate(requestDto.getRequestApprovedDate()));
		}
		request.setRequestCreatedBy(requestDto.getRequestCreatedBy());

		if (requestDto.getRequestCreatedDate() != null && !(requestDto.getRequestCreatedDate().equals(""))) {
			request.setRequestCreatedDate(convertStringToDate(requestDto.getRequestCreatedDate()));
		}
		request.setRequestId(requestDto.getRequestId());
		request.setRequestPendingWith(requestDto.getRequestPendingWith());
		request.setRequestStatus(requestDto.getRequestStatus());
		request.setRequestUpdatedBy(requestDto.getRequestUpdatedBy());
		request.setSoldTo(requestDto.getSoldTo());
		request.setShipTo(requestDto.getShipTo());

		if (requestDto.getRequestUpdatedDate() != null && !(requestDto.getRequestUpdatedDate().equals(""))) {
			request.setRequestUpdatedDate(convertStringToDate(requestDto.getRequestUpdatedDate()));
		}

		Set<ReturnOrder> returnOrderSet = null;
		if (requestDto.getSetReturnOrderDto() != null && requestDto.getSetReturnOrderDto().size() > 0) {
			returnOrderSet = setReturnOrderDetail(requestDto.getSetReturnOrderDto(), request);
		}

		request.setSetReturnOrder(returnOrderSet);
		request.setRequestHeader(importHeaderDto(requestDto.getHeaderDto()));
		request.setCustomerGroup(requestDto.getCustomerGroup());
		request.setCustomer(requestDto.getCustomer());
		request.setCustomerNo(requestDto.getCustomerNo());
		request.setBusinessUnit(requestDto.getBusinessUnit());
		request.setEccReturnOrderNo(requestDto.getEccReturnOrderNo());
		request.setEccStatus(requestDto.getEccStatus());

		return request;
	}

	public RequestDto exportRequestDto(Request request) {
		RequestDto requestDto = new RequestDto();
		requestDto.setRequestApprovedBy(request.getRequestApprovedBy());
		if (request.getRequestApprovedDate() != null) {
			requestDto.setRequestApprovedDate(convertDateToString(request.getRequestApprovedDate()));
		} else {
			requestDto.setRequestApprovedDate("");
		}
		requestDto.setRequestCreatedBy(request.getRequestCreatedBy());
		if (request.getRequestCreatedDate() != null) {
			requestDto.setRequestCreatedDate(convertDateToString(request.getRequestCreatedDate()));
		} else {
			requestDto.setRequestCreatedDate("");
		}
		requestDto.setRequestId(request.getRequestId());
		requestDto.setRequestPendingWith(request.getRequestPendingWith());
		requestDto.setRequestStatus(request.getRequestStatus());
		requestDto.setSoldTo(request.getSoldTo());
		requestDto.setShipTo(request.getShipTo());
		requestDto.setHeaderDto(exportHeaderDto(request.getRequestHeader()));
		if (request.getRequestUpdatedDate() != null) {
			requestDto.setRequestUpdatedDate(convertDateToString(request.getRequestUpdatedDate()));
		} else {
			requestDto.setRequestUpdatedDate("");
		}
		requestDto.setRequestUpdatedBy(request.getRequestUpdatedBy());

		Set<ReturnOrder> returnOrders = request.getSetReturnOrder();
		ReturnOrderDto returnOrderDto = null;
		Set<ReturnOrderDto> returnOrderSet = new HashSet<>();
		for (ReturnOrder returnOrder : returnOrders) {
			returnOrderDto = exportReturnOrderDto(returnOrder);
			returnOrderSet.add(returnOrderDto);
		}
		requestDto.setSetReturnOrderDto(returnOrderSet);
		requestDto.setHeaderDto(exportHeaderDto(request.getRequestHeader()));
		requestDto.setCustomerGroup(request.getCustomerGroup());
		requestDto.setCustomer(request.getCustomer());
		requestDto.setCustomerNo(request.getCustomerNo());
		requestDto.setBusinessUnit(request.getBusinessUnit());
		requestDto.setEccReturnOrderNo(request.getEccReturnOrderNo());
		requestDto.setEccStatus(request.getEccStatus());
		
		return requestDto;
	}

	public ReturnOrder importReturnOrderDto(ReturnOrderDto returnOrderDto, Request request) {
		ReturnOrder returnOrder = new ReturnOrder();
		returnOrder.setReturnOrderData(request);
		returnOrder.setItemCode(returnOrderDto.getItemCode());
		returnOrder.setReason(returnOrderDto.getReason());
		returnOrder.setRemark(returnOrderDto.getRemark());

		returnOrder.setReturnOrderId(returnOrderDto.getReturnOrderId());
		returnOrder.setReturnPrice(returnOrderDto.getReturnPrice());
		returnOrder.setReturnQty(returnOrderDto.getReturnQty());
		returnOrder.setReturnValue(returnOrderDto.getReturnValue());
		returnOrder.setPaymentType(returnOrderDto.getPaymentType());

		returnOrder.setOrderApprovedBy(returnOrderDto.getOrderApprovedBy());
		if(returnOrderDto.getOrderApprovedDate() != null && !(returnOrderDto.getOrderApprovedDate().equals(""))) {
			returnOrder.setOrderApprovedDate(convertStringToDate(returnOrderDto.getOrderApprovedDate()));
		} 
		returnOrder.setOrderCreatedBy(returnOrderDto.getOrderCreatedBy());
		if(returnOrderDto.getOrderCreatedDate() != null && !(returnOrderDto.getOrderCreatedDate().equals(""))) {
			returnOrder.setOrderCreatedDate(convertStringToDate(returnOrderDto.getOrderCreatedDate()));
		}
		returnOrder.setOrderPendingWith(returnOrderDto.getOrderPendingWith());
		returnOrder.setOrderStatus(returnOrderDto.getOrderStatus());
		returnOrder.setOrderUpdatedBy(returnOrderDto.getOrderUpdatedBy());
		if(returnOrderDto.getOrderUpdatedDate() != null && !(returnOrderDto.getOrderUpdatedDate().equals(""))) {
			returnOrder.setOrderUpdatedDate(convertStringToDate(returnOrderDto.getOrderUpdatedDate()));
		}
		returnOrder.setOrderComments(returnOrderDto.getOrderComments());
		return returnOrder;
	}

	public ReturnOrderDto exportReturnOrderDto(ReturnOrder returnOrder) {
		ReturnOrderDto returnOrderDto = new ReturnOrderDto();

		returnOrderDto.setItemCode(returnOrder.getItemCode());
		returnOrderDto.setReason(returnOrder.getReason());
		returnOrderDto.setRemark(returnOrder.getRemark());
		returnOrderDto.setReturnOrderId(returnOrder.getReturnOrderId());
		returnOrderDto.setReturnPrice(returnOrder.getReturnPrice());
		returnOrderDto.setReturnQty(returnOrder.getReturnQty());
		returnOrderDto.setReturnValue(returnOrder.getReturnValue());
		
		returnOrderDto.setOrderApprovedBy(returnOrder.getOrderApprovedBy());
		if(returnOrder.getOrderApprovedDate() != null) {
			returnOrderDto.setOrderApprovedDate(convertDateToString(returnOrder.getOrderApprovedDate()));
		} 
		returnOrderDto.setOrderCreatedBy(returnOrder.getOrderCreatedBy());
		if(returnOrder.getOrderCreatedDate() != null) {
			returnOrderDto.setOrderCreatedDate(convertDateToString(returnOrder.getOrderCreatedDate()));
		}
		returnOrderDto.setOrderPendingWith(returnOrder.getOrderPendingWith());
		returnOrderDto.setOrderStatus(returnOrder.getOrderStatus());
		returnOrderDto.setOrderUpdatedBy(returnOrder.getOrderUpdatedBy());
		if(returnOrder.getOrderUpdatedDate() != null) {
			returnOrderDto.setOrderUpdatedDate(convertDateToString(returnOrder.getOrderUpdatedDate()));
		}
		returnOrderDto.setPaymentType(returnOrder.getPaymentType());
		returnOrderDto.setRequestId(returnOrder.getReturnOrderData().getRequestId());
		returnOrderDto.setOrderComments(returnOrder.getOrderComments());
		return returnOrderDto;
	}

	public Attachment importAttachmentDto(AttachmentDto attachmentDto) {
		Attachment attachment = new Attachment();
		attachment.setAttachmentId(attachmentDto.getAttachmentId());
		attachment.setAttachmentName(attachmentDto.getAttachmentName());
		attachment.setInvoiceNo(attachmentDto.getInvoiceNo());
		attachment.setItemCode(attachmentDto.getItemCode());
		attachment.setRequestId(attachmentDto.getRequestId());

		return attachment;
	}

	public AttachmentDto exportAttachmentDto(Attachment attachment) {
		AttachmentDto attachmentDto = new AttachmentDto();
		attachmentDto.setAttachmentId(attachment.getAttachmentId());
		attachmentDto.setAttachmentName(attachment.getAttachmentName());
		attachmentDto.setInvoiceNo(attachment.getInvoiceNo());
		attachmentDto.setItemCode(attachment.getItemCode());
		attachmentDto.setRequestId(attachment.getRequestId());

		return attachmentDto;
	}

	public ReasonDto exportReasonDto(Reason returnReason) {
		ReasonDto returnReasonDto = new ReasonDto();
		returnReasonDto.setBusinessUnit(returnReason.getBusinessUnit());
		returnReasonDto.setReasonCode(returnReason.getReasonCode());
		returnReasonDto.setReasonDesc(returnReason.getReasonDesc());
		returnReasonDto.setReasonName(returnReason.getReasonName());

		return returnReasonDto;

	}

	public Reason importReasonDto(ReasonDto returnReasonDto) {
		Reason returnReason = new Reason();
		returnReason.setBusinessUnit(returnReasonDto.getBusinessUnit());
		returnReason.setReasonCode(returnReasonDto.getReasonCode());
		returnReason.setReasonDesc(returnReasonDto.getReasonDesc());
		returnReason.setReasonName(returnReasonDto.getReasonName());

		return returnReason;
	}

	public CustomerDto exportCustomerDto(Customer customer) {
		CustomerDto customerDto = new CustomerDto();
		customerDto.setCustomerCode(customer.getCustomerCode());
		customerDto.setCustomerName(customer.getCustomerName());
		customerDto.setSaleRep(customer.getSaleRep());
		return customerDto;
	}

	public Customer importCustomerDto(CustomerDto customerDto) {
		Customer customer = new Customer();
		customer.setCustomerCode(customerDto.getCustomerCode());
		customer.setCustomerName(customerDto.getCustomerName());
		customer.setId(null);
		customer.setSaleRep(customerDto.getSaleRep());
		return customer;
	}

	public WorkFlowDto exportWorkFlowDto(WorkFlow workFlowInstance) {
		WorkFlowDto workFlowDto = new WorkFlowDto();
		workFlowDto.setRequestId(workFlowInstance.getRequestId());
		workFlowDto.setWorkFlowInstanceId(workFlowInstance.getWorkFlowInstanceId());
		workFlowDto.setMaterialCode(workFlowInstance.getMaterialCode());
		workFlowDto.setPrincipal(workFlowInstance.getPrincipal());
		workFlowDto.setTaskInstanceId(workFlowInstance.getTaskInstanceId());
		workFlowDto.setWorkflowId(workFlowInstance.getWorkflowId());

		return workFlowDto;

	}

	public WorkFlow importWorkFlowDto(WorkFlowDto workFlowDto) {
		WorkFlow workFlow = new WorkFlow();
		workFlow.setRequestId(workFlowDto.getRequestId());
		workFlow.setWorkFlowInstanceId(workFlowDto.getWorkFlowInstanceId());
		workFlow.setMaterialCode(workFlowDto.getMaterialCode());
		workFlow.setPrincipal(workFlowDto.getPrincipal());
		workFlow.setTaskInstanceId(workFlowDto.getTaskInstanceId());
		workFlow.setWorkflowId(workFlowDto.getWorkflowId());

		return workFlow;

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
