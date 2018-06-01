package com.incture.zp.ereturns.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "T_REQUEST")
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
	
	@Column(name = "SOLD_TO", length = 100) // customer code
	private String soldTo;

	@Column(name = "SHIP_TO", length = 100)
	private String shipTo;
	
	@Column(name = "CUSTOMER", length = 255)
	private String customer;

	@Column(name = "CUSTOMER_NUMBER", length = 255)
	private String customerNo;

	@Column(name = "CUSTOMER_GROUP", length = 255)
	private String customerGroup;

	@Column(name = "BUSINESS_UNIT", length = 255)
	private String businessUnit;

	@OneToOne(cascade = CascadeType.ALL)
	private Header requestHeader; 
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "returnOrderData")
	private Set<ReturnOrder> setReturnOrder;

	public Set<ReturnOrder> getSetReturnOrder() {
		return setReturnOrder;
	}

	public void setSetReturnOrder(Set<ReturnOrder> setReturnOrder) {
		this.setReturnOrder = setReturnOrder;
	}

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

	public Header getRequestHeader() {
		return requestHeader;
	}

	public void setRequestHeader(Header requestHeader) {
		this.requestHeader = requestHeader;
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
	
}
