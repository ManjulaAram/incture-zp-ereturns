package com.incture.zp.ereturns.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
	
	@Column(name = "LOCATION", length = 50)
	private String location;
	
	@Column(name = "BOX_QTY", length = 10)
	private String boxQty;
	
	@Column(name = "LOT_NO", length = 50)
	private String lotNo;
	
	@Column(name = "SALES_PERSON", length = 50)
	private String salesPerson;

	@Column(name = "UN_REFERENCED", length = 5)
	private String unRef;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "returnOrderData")
	private Set<ReturnOrder> setReturnOrder;

	public Set<ReturnOrder> getSetReturnOrder() {
		return setReturnOrder;
	}

	public void setSetReturnOrder(Set<ReturnOrder> setReturnOrder) {
		this.setReturnOrder = setReturnOrder;
	}

	public String getSalesPerson() {
		return salesPerson;
	}

	public void setSalesPerson(String salesPerson) {
		this.salesPerson = salesPerson;
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

	public String getLotNo() {
		return lotNo;
	}
	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}

	public String getUnRef() {
		return unRef;
	}

	public void setUnRef(String unRef) {
		this.unRef = unRef;
	}

}
