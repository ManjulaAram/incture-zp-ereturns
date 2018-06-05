package com.incture.zp.ereturns.dto;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RequestDto {

	private String requestId;
	
	private String requestCreatedBy;
	
	private String requestCreatedDate;

	private String requestStatus;
	
	private String requestUpdatedBy;
	
	private String requestUpdatedDate;
	
	private String requestApprovedBy;
	
	private String requestApprovedDate;
	
	private String requestPendingWith;
	
	private HeaderDto headerDto;
	
	private Set<ReturnOrderDto> setReturnOrderDto;
	
	private Set<AttachmentDto> setAttachments;
	
	private String soldTo; //customer code
	
	private String shipTo;
	
	private String customerGroup;
	
	private String customer;

	private String customerNo;

	private String businessUnit;
	
	private String eccStatus;

	private String eccReturnOrderNo;

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getRequestCreatedBy() {
		return requestCreatedBy;
	}

	public void setRequestCreatedBy(String requestCreatedBy) {
		this.requestCreatedBy = requestCreatedBy;
	}

	public String getRequestCreatedDate() {
		return requestCreatedDate;
	}

	public void setRequestCreatedDate(String requestCreatedDate) {
		this.requestCreatedDate = requestCreatedDate;
	}

	public String getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}

	public String getRequestUpdatedBy() {
		return requestUpdatedBy;
	}

	public void setRequestUpdatedBy(String requestUpdatedBy) {
		this.requestUpdatedBy = requestUpdatedBy;
	}

	public String getRequestUpdatedDate() {
		return requestUpdatedDate;
	}

	public void setRequestUpdatedDate(String requestUpdatedDate) {
		this.requestUpdatedDate = requestUpdatedDate;
	}

	public String getRequestApprovedBy() {
		return requestApprovedBy;
	}

	public void setRequestApprovedBy(String requestApprovedBy) {
		this.requestApprovedBy = requestApprovedBy;
	}

	public String getRequestApprovedDate() {
		return requestApprovedDate;
	}

	public void setRequestApprovedDate(String requestApprovedDate) {
		this.requestApprovedDate = requestApprovedDate;
	}

	public String getRequestPendingWith() {
		return requestPendingWith;
	}

	public void setRequestPendingWith(String requestPendingWith) {
		this.requestPendingWith = requestPendingWith;
	}

	public Set<ReturnOrderDto> getSetReturnOrderDto() {
		return setReturnOrderDto;
	}

	public void setSetReturnOrderDto(Set<ReturnOrderDto> setReturnOrderDto) {
		this.setReturnOrderDto = setReturnOrderDto;
	}

	public String getSoldTo() {
		return soldTo;
	}

	public void setSoldTo(String soldTo) {
		this.soldTo = soldTo;
	}

	public String getShipTo() {
		return shipTo;
	}

	public void setShipTo(String shipTo) {
		this.shipTo = shipTo;
	}

	public Set<AttachmentDto> getSetAttachments() {
		return setAttachments;
	}

	public void setSetAttachments(Set<AttachmentDto> setAttachments) {
		this.setAttachments = setAttachments;
	}

	public HeaderDto getHeaderDto() {
		return headerDto;
	}

	public void setHeaderDto(HeaderDto headerDto) {
		this.headerDto = headerDto;
	}

	public String getCustomerGroup() {
		return customerGroup;
	}

	public void setCustomerGroup(String customerGroup) {
		this.customerGroup = customerGroup;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	public String getBusinessUnit() {
		return businessUnit;
	}

	public void setBusinessUnit(String businessUnit) {
		this.businessUnit = businessUnit;
	}

	public String getEccStatus() {
		return eccStatus;
	}

	public void setEccStatus(String eccStatus) {
		this.eccStatus = eccStatus;
	}

	public String getEccReturnOrderNo() {
		return eccReturnOrderNo;
	}

	public void setEccReturnOrderNo(String eccReturnOrderNo) {
		this.eccReturnOrderNo = eccReturnOrderNo;
	}

}
