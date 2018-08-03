package com.incture.zp.ereturns.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "T_RETURN_ORDER")
public class ReturnOrder {

	@Id
	@Column(name = "RETURN_ORDER_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long returnOrderId;
	
	@Column(name = "RETURN_QTY", length = 50)
	private String returnQty;
	
	@Column(name = "INVOICED_QTY", length = 50)
	private String invoicedQty;

	@Column(name = "REASON", length = 255)
	private String reason;
	
	@Column(name = "REMARK")
	private String remark;
	
	@Column(name = "RETURN_PRICE", length = 50)
	private String returnPrice;
	
	@Column(name = "RETURN_VALUE", length = 50)
	private String returnValue;

	@Column(name = "ITEM_CODE")
	private String itemCode; 
	
	@Column(name = "PAYMENT_TYPE")
	private String paymentType;
	
	@Column(name = "ORDER_CREATED_BY", length = 100)
	private String orderCreatedBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ORDER_CREATED_DATE")
	private Date orderCreatedDate;
	
	@Column(name = "ORDER_STATUS", length = 100)
	private String orderStatus;
	
	@Column(name = "ORDER_UPDATED_BY", length = 100)
	private String orderUpdatedBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ORDER_UPDATED_DATE")
	private Date orderUpdatedDate;

	@Column(name = "ORDER_APPROVED_BY", length = 100)
	private String orderApprovedBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ORDER_APPROVED_DATE")
	private Date orderApprovedDate;
	
	@Column(name = "ORDER_PENDING_WITH", length = 100)
	private String orderPendingWith;

	@Column(name = "ORDER_COMMENTS")
	private String orderComments;

	@ManyToOne
	@JoinColumn(name = "REQUEST_ID", nullable = false, updatable = false)
	private Request returnOrderData;
	
	public Long getReturnOrderId() {
		return returnOrderId;
	}

	public void setReturnOrderId(Long returnOrderId) {
		this.returnOrderId = returnOrderId;
	}

	public String getReturnQty() {
		return returnQty;
	}

	public void setReturnQty(String returnQty) {
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

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getReturnPrice() {
		return returnPrice;
	}

	public void setReturnPrice(String returnPrice) {
		this.returnPrice = returnPrice;
	}

	public String getReturnValue() {
		return returnValue;
	}

	public void setReturnValue(String returnValue) {
		this.returnValue = returnValue;
	}

	public Request getReturnOrderData() {
		return returnOrderData;
	}

	public void setReturnOrderData(Request returnOrderData) {
		this.returnOrderData = returnOrderData;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getOrderCreatedBy() {
		return orderCreatedBy;
	}

	public void setOrderCreatedBy(String orderCreatedBy) {
		this.orderCreatedBy = orderCreatedBy;
	}

	public Date getOrderCreatedDate() {
		return orderCreatedDate;
	}

	public void setOrderCreatedDate(Date orderCreatedDate) {
		this.orderCreatedDate = orderCreatedDate;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderUpdatedBy() {
		return orderUpdatedBy;
	}

	public void setOrderUpdatedBy(String orderUpdatedBy) {
		this.orderUpdatedBy = orderUpdatedBy;
	}

	public Date getOrderUpdatedDate() {
		return orderUpdatedDate;
	}

	public void setOrderUpdatedDate(Date orderUpdatedDate) {
		this.orderUpdatedDate = orderUpdatedDate;
	}

	public String getOrderApprovedBy() {
		return orderApprovedBy;
	}

	public void setOrderApprovedBy(String orderApprovedBy) {
		this.orderApprovedBy = orderApprovedBy;
	}

	public Date getOrderApprovedDate() {
		return orderApprovedDate;
	}

	public void setOrderApprovedDate(Date orderApprovedDate) {
		this.orderApprovedDate = orderApprovedDate;
	}

	public String getOrderPendingWith() {
		return orderPendingWith;
	}

	public void setOrderPendingWith(String orderPendingWith) {
		this.orderPendingWith = orderPendingWith;
	}

	public String getOrderComments() {
		return orderComments;
	}

	public void setOrderComments(String orderComments) {
		this.orderComments = orderComments;
	}

	public String getInvoicedQty() {
		return invoicedQty;
	}

	public void setInvoicedQty(String invoicedQty) {
		this.invoicedQty = invoicedQty;
	}

}
