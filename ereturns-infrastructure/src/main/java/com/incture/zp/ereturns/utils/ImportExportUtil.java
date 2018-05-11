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
import com.incture.zp.ereturns.dto.HeaderDto;
import com.incture.zp.ereturns.dto.ItemDto;
import com.incture.zp.ereturns.dto.ReturnReasonDto;
import com.incture.zp.ereturns.dto.RequestDto;
import com.incture.zp.ereturns.dto.ReturnOrderDto;
import com.incture.zp.ereturns.dto.RoleDto;
import com.incture.zp.ereturns.dto.UserDto;
import com.incture.zp.ereturns.model.Attachment;
import com.incture.zp.ereturns.model.Header;
import com.incture.zp.ereturns.model.Item;
import com.incture.zp.ereturns.model.ReturnReason;
import com.incture.zp.ereturns.model.Request;
import com.incture.zp.ereturns.model.ReturnOrder;
import com.incture.zp.ereturns.model.Role;
import com.incture.zp.ereturns.model.User;

@Component
public class ImportExportUtil {

	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final Logger LOGGER = LoggerFactory.getLogger(ImportExportUtil.class);

	public User importUserDto(UserDto userDto) {
		User user = new User();
		user.setEmail(userDto.getEmail());
		user.setSciId(userDto.getSciId());
		user.setUserId(userDto.getUserId());
		user.setUserName(userDto.getUserName());
		user.setMobileToken(userDto.getMobileToken());
		user.setPhone(userDto.getPhone());
		user.setWebToken(userDto.getWebToken());

		Set<Role> roleSet = null;
		if (userDto.getSetRole() != null && userDto.getSetRole().size() > 0) {
			roleSet = setRoleDetail(userDto.getSetRole());
		}
		user.setRoleDetails(roleSet);
		return user;
	}

	private Set<Role> setRoleDetail(Set<RoleDto> roleSet) {
		Set<Role> setRole = new HashSet<>();

		for (RoleDto roleDto : roleSet) {
			setRole.add(importRoleDto(roleDto));
		}
		return setRole;
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
		userDto.setEmail(user.getEmail());
		userDto.setSciId(user.getSciId());
		userDto.setUserId(user.getUserId());
		userDto.setUserName(user.getUserName());
		userDto.setMobileToken(user.getMobileToken());
		userDto.setWebToken(user.getWebToken());
		return userDto;
	}

	public Header importHeaderDto(HeaderDto headerDto) {
		Header header = new Header();
		header.setAvailableQty(headerDto.getAvailableQty());
		if (headerDto.getExpiryDate() != null && !(headerDto.getExpiryDate().equals(""))) {
			header.setExpiryDate(convertStringToDate(headerDto.getExpiryDate()));
		}
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
		headerDto.setAvailableQty(header.getAvailableQty());
		if (header.getExpiryDate() != null && !(header.getExpiryDate().equals(""))) {
			headerDto.setExpiryDate(convertDateToString(header.getExpiryDate()));
		}
		LOGGER.error("Date coming from DB:" + header.getExpiryDate());
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
		item.setPricipal(item.getPricipal());
		item.setPricipalCode(itemDto.getPricipalCode());
		item.setMaterialGroup(itemDto.getMaterialGroup());

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
		itemDto.setPricipal(item.getPricipal());
		itemDto.setPricipalCode(item.getPricipalCode());

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

		return returnOrderDto;
	}

	public RoleDto exportRoleDto(Role role) {
		RoleDto roleDto = new RoleDto();

		roleDto.setRoleId(role.getRoleId());
		roleDto.setRoleName(role.getRoleName());

		return roleDto;
	}

	public Role importRoleDto(RoleDto roleDto) {
		Role role = new Role();

		role.setRoleId(roleDto.getRoleId());
		role.setRoleName(roleDto.getRoleName());
		return role;
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

	public ReturnReasonDto exportDto(ReturnReason returnReason) {
		ReturnReasonDto returnReasonDto = new ReturnReasonDto();
		returnReasonDto.setBusinessUnit(returnReason.getBusinessUnit());
		returnReasonDto.setReasonCode(returnReason.getReasonCode());
		returnReasonDto.setReasonDesc(returnReason.getReasonDesc());
		returnReasonDto.setReasonName(returnReason.getReasonName());

		return returnReasonDto;

	}

	public ReturnReason importDto(ReturnReasonDto returnReasonDto) {
		ReturnReason returnReason = new ReturnReason();
		returnReason.setBusinessUnit(returnReasonDto.getBusinessUnit());
		returnReason.setReasonCode(returnReasonDto.getReasonCode());
		returnReason.setReasonDesc(returnReasonDto.getReasonDesc());
		returnReason.setReasonName(returnReasonDto.getReasonName());

		return returnReason;
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
