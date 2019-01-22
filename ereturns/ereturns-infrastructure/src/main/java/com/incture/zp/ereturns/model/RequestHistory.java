package com.incture.zp.ereturns.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "T_REQUEST_HISTORY")
public class RequestHistory {

	@Id
	@Column(name = "REQUEST_HISTORY_ID")
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long requestHistoryId;
	
	@Column(name = "REQ_CREATED_BY")
	private String requestCreatedBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "REQ_CREATED_DATE")
	private Date requestCreatedDate;
	
	@Column(name = "REQ_STATUS")
	private String requestStatus;
	
	@Column(name = "REQ_UPDATED_BY")
	private String requestUpdatedBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "REQ_UPDATED_DATE")
	private Date requestUpdatedDate;

	@Column(name = "REQ_APPROVED_BY")
	private String requestApprovedBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "REQ_APPROVED_DATE")
	private Date requestApprovedDate;
	
	@Column(name = "REQ_PENDING_WITH")
	private String requestPendingWith;

	@Column(name = "REQUEST_ID")
	private String requestId;
	
	@Column(name = "INVOICE_NO")
	private String invoiceNo;  
	
	@Column(name = "ITEM_CODE")
	private String itemCode;  
	
	@Column(name = "MATERIAL")
	private String material; 

	@Column(name = "CUSTOMER")
	private String customer; 
	
	@Column(name = "REQUESTOR_COMMENTS")
	private String requestorComments; 
	
	public Long getRequestHistoryId() {
		return requestHistoryId;
	}

	public void setRequestHistoryId(Long requestHistoryId) {
		this.requestHistoryId = requestHistoryId;
	}

	public String getRequestCreatedBy() {
		return requestCreatedBy;
	}

	public void setRequestCreatedBy(String requestCreatedBy) {
		this.requestCreatedBy = requestCreatedBy;
	}

	public Date getRequestCreatedDate() {
		return requestCreatedDate;
	}

	public void setRequestCreatedDate(Date requestCreatedDate) {
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

	public Date getRequestUpdatedDate() {
		return requestUpdatedDate;
	}

	public void setRequestUpdatedDate(Date requestUpdatedDate) {
		this.requestUpdatedDate = requestUpdatedDate;
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

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getRequestorComments() {
		return requestorComments;
	}

	public void setRequestorComments(String requestorComments) {
		this.requestorComments = requestorComments;
	}

}
