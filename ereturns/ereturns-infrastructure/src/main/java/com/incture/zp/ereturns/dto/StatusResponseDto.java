package com.incture.zp.ereturns.dto;

import java.io.Serializable;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StatusResponseDto implements Serializable {

	private static final long serialVersionUID = 3030045408794478627L;

	private String message;
	
	private String status;
	
	private String requestId;

	private String invoiceNo;

	private String materialCode;
	
	private String itemCode;

	private String materialDescription;

	private String customerCode;

	private String customerName;

	private String soldTo;

	private String shipTo;

	private String returnType;

	private String createdOn;

	private String createdBy;

	private String remark;

	private String returnQuantity;

	private String salesPerson;

	private String netValue;

	private String netQuantity;

	private String returnValue;

	private String requestStatus;

	private String returnReason;
	
	private String orderComments;
	
	private String reason;
	
	private String batch;
	
	private String expiryDate;
	
	private String eccResponse;
	
	private String principalCode;
	
	private String currency;
	
	private String overrideReturnValue;
	
	private String shipToName;
	
	private String shipToStreet;
	
	private String shipToSupply1;
	
	private String shipToSupply2;
	
	private String shipToCity;

	
	private Set<AttachmentDto> attachments;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getMaterialDescription() {
		return materialDescription;
	}

	public void setMaterialDescription(String materialDescription) {
		this.materialDescription = materialDescription;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getReturnQuantity() {
		return returnQuantity;
	}

	public void setReturnQuantity(String returnQuantity) {
		this.returnQuantity = returnQuantity;
	}

	public String getSalesPerson() {
		return salesPerson;
	}

	public void setSalesPerson(String salesPerson) {
		this.salesPerson = salesPerson;
	}

	public String getNetValue() {
		return netValue;
	}

	public void setNetValue(String netValue) {
		this.netValue = netValue;
	}

	public String getNetQuantity() {
		return netQuantity;
	}

	public void setNetQuantity(String netQuantity) {
		this.netQuantity = netQuantity;
	}

	public String getReturnValue() {
		return returnValue;
	}

	public void setReturnValue(String returnValue) {
		this.returnValue = returnValue;
	}

	public String getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}

	public String getReturnReason() {
		return returnReason;
	}

	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
	}

	public Set<AttachmentDto> getAttachments() {
		return attachments;
	}

	public void setAttachments(Set<AttachmentDto> attachments) {
		this.attachments = attachments;
	}

	public Object getId() {
		return null;
	}

	public String getOrderComments() {
		return orderComments;
	}

	public void setOrderComments(String orderComments) {
		this.orderComments = orderComments;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getEccResponse() {
		return eccResponse;
	}

	public void setEccResponse(String eccResponse) {
		this.eccResponse = eccResponse;
	}

	public String getPrincipalCode() {
		return principalCode;
	}

	public void setPrincipalCode(String principalCode) {
		this.principalCode = principalCode;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getOverrideReturnValue() {
		return overrideReturnValue;
	}

	public void setOverrideReturnValue(String overrideReturnValue) {
		this.overrideReturnValue = overrideReturnValue;
	}

	public String getShipToName() {
		return shipToName;
	}

	public void setShipToName(String shipToName) {
		this.shipToName = shipToName;
	}

	public String getShipToStreet() {
		return shipToStreet;
	}

	public void setShipToStreet(String shipToStreet) {
		this.shipToStreet = shipToStreet;
	}

	public String getShipToSupply1() {
		return shipToSupply1;
	}

	public void setShipToSupply1(String shipToSupply1) {
		this.shipToSupply1 = shipToSupply1;
	}

	public String getShipToSupply2() {
		return shipToSupply2;
	}

	public void setShipToSupply2(String shipToSupply2) {
		this.shipToSupply2 = shipToSupply2;
	}

	public String getShipToCity() {
		return shipToCity;
	}

	public void setShipToCity(String shipToCity) {
		this.shipToCity = shipToCity;
	}
	
}
