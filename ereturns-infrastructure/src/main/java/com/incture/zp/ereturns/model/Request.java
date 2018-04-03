package com.incture.zp.ereturns.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class Request {

	@Id
	@Column(name = "REQUEST_ID", nullable = false)
	private String requestId; // eReturnNo / RCN# 
	
	@Column(name = "REQ_CREATED_BY", length = 100)
	private String requestCreatedBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "REQ_CREATED_DATE")
	private Date requestCreatedDate;
	
	@Column(name = "REQ_STATUS", length = 100)
	private String requestStatus;
	
	@Column(name = "REQ_UPDATED_BY", length = 100)
	private String requestUpdatedBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "REQ_UPDATED_DATE")
	private Date requestUpdatedDate;

	@Column(name = "REQ_APPROVED_BY", length = 100)
	private String requestApprovedBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "REQ_APPROVED_DATE")
	private Date requestApprovedDate;
	
	@Column(name = "REQ_PENDING_WITH", length = 100)
	private String requestPendingWith;
	
	@Column(name = "RETURN_QTY", precision = 8, scale = 2)
	private double returnQty;

	@Column(name = "REASON", length = 255)
	private String reason;
	
	@Column(name = "REMARK", length = 255)
	private String remark;
	
	@Column(name = "LOCATION", length = 50)
	private String location;
	
	@Column(name = "BOX_QTY", length = 10)
	private String boxQty;
	
	@Column(name = "RETURN_PRICE", precision = 8, scale = 2)
	private double returnPrice;
	
	@Column(name = "RETURN_VALUE", precision = 8, scale = 2)
	private double returnValue;

	@Column(name = "INVOICE_NO")
	private String invoiceNo;  
	
	@Column(name = "ITEM_CODE")
	private String itemCode; 
	
	@Column(name = "USER_ID")
	private String userId; 
	
	
//	@ManyToOne
//	@JoinColumn(name = "USER_ID", nullable = false, updatable = false)
//	private User requestData;


	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public Date getRequestCreatedDate() {
		return requestCreatedDate;
	}

	public void setRequestCreatedDate(Date requestCreatedDate) {
		this.requestCreatedDate = requestCreatedDate;
	}

	public String getRequestCreatedBy() {
		return requestCreatedBy;
	}

	public void setRequestCreatedBy(String requestCreatedBy) {
		this.requestCreatedBy = requestCreatedBy;
	}

	public String getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}

	public Date getRequestUpdatedDate() {
		return requestUpdatedDate;
	}

	public void setRequestUpdatedDate(Date requestUpdatedDate) {
		this.requestUpdatedDate = requestUpdatedDate;
	}

	public String getRequestUpdatedBy() {
		return requestUpdatedBy;
	}

	public void setRequestUpdatedBy(String requestUpdatedBy) {
		this.requestUpdatedBy = requestUpdatedBy;
	}

	public String getRequestApprovedBy() {
		return requestApprovedBy;
	}

	public void setRequestApprovedBy(String requestApprovedBy) {
		this.requestApprovedBy = requestApprovedBy;
	}

	public Date getRequestApprovedDate() {
		return requestApprovedDate;
	}

	public void setRequestApprovedDate(Date requestApprovedDate) {
		this.requestApprovedDate = requestApprovedDate;
	}

	public String getRequestPendingWith() {
		return requestPendingWith;
	}

	public void setRequestPendingWith(String requestPendingWith) {
		this.requestPendingWith = requestPendingWith;
	}

	public double getReturnQty() {
		return returnQty;
	}

	public void setReturnQty(double returnQty) {
		this.returnQty = returnQty;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getBoxQty() {
		return boxQty;
	}

	public void setBoxQty(String boxQty) {
		this.boxQty = boxQty;
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

}
