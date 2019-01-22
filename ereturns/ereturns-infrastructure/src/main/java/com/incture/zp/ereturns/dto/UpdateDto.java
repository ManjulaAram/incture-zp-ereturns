package com.incture.zp.ereturns.dto;

public class UpdateDto {

	private String pendingWith;
	
	private String status;
	
	private String approvedBy;
	
	private String approvedDate;
	
	private String comments;
	
	private String requestId;
	
	private String itemCode;
	
	private String eccStatus;
	
	private String eccNo;

	public String getPendingWith() {
		return pendingWith;
	}

	public void setPendingWith(String pendingWith) {
		this.pendingWith = pendingWith;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public String getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(String approvedDate) {
		this.approvedDate = approvedDate;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getEccStatus() {
		return eccStatus;
	}

	public void setEccStatus(String eccStatus) {
		this.eccStatus = eccStatus;
	}

	public String getEccNo() {
		return eccNo;
	}

	public void setEccNo(String eccNo) {
		this.eccNo = eccNo;
	}
	
}
