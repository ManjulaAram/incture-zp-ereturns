package com.incture.zp.ereturns.dto;

public class RequestHistoryDto {

	private Long requestHistoryId;
	
	private String requestCreatedBy;
	
	private String requestCreatedDate;
	
	private String requestStatus;
	
	private String requestUpdatedBy;
	
	private String requestUpdatedDate;

	private String requestApprovedBy;
	
	private String requestApprovedDate;
	
	private String requestPendingWith;

	private String requestId;
	
	private String invoiceNo;  
	
	private String itemCode;  
	
	private String material; 

	private String customer; 
	
	private String requestorComments;

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

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getRequestorComments() {
		return requestorComments;
	}

	public void setRequestorComments(String requestorComments) {
		this.requestorComments = requestorComments;
	}

	public Long getRequestHistoryId() {
		return requestHistoryId;
	}

	public void setRequestHistoryId(Long requestHistoryId) {
		this.requestHistoryId = requestHistoryId;
	} 

}
