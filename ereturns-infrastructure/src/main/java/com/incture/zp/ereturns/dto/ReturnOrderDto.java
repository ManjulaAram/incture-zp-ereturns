package com.incture.zp.ereturns.dto;

import java.io.Serializable;

public class ReturnOrderDto implements Serializable {

	private static final long serialVersionUID = 4877815216339084080L;

	private Long returnOrderId;
	
	private String returnQty;

	private String reason;
	
	private String remark;
	
	private String returnPrice;
	
	private String returnValue;

	private String itemCode; 
	
	private String paymentType;
	
	private String orderCreatedBy;
	
	private String orderCreatedDate;
	
	private String orderStatus;
	
	private String orderUpdatedBy;
	
	private String orderUpdatedDate;

	private String orderApprovedBy;
	
	private String orderApprovedDate;
	
	private String orderPendingWith;

	
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

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
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

	public String getOrderCreatedDate() {
		return orderCreatedDate;
	}

	public void setOrderCreatedDate(String orderCreatedDate) {
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

	public String getOrderUpdatedDate() {
		return orderUpdatedDate;
	}

	public void setOrderUpdatedDate(String orderUpdatedDate) {
		this.orderUpdatedDate = orderUpdatedDate;
	}

	public String getOrderApprovedBy() {
		return orderApprovedBy;
	}

	public void setOrderApprovedBy(String orderApprovedBy) {
		this.orderApprovedBy = orderApprovedBy;
	}

	public String getOrderApprovedDate() {
		return orderApprovedDate;
	}

	public void setOrderApprovedDate(String orderApprovedDate) {
		this.orderApprovedDate = orderApprovedDate;
	}

	public String getOrderPendingWith() {
		return orderPendingWith;
	}

	public void setOrderPendingWith(String orderPendingWith) {
		this.orderPendingWith = orderPendingWith;
	}

}
