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

	private String invoiceNo;  
	
	private String itemCode; 

	private String userCode;
	
	private String returnEntireOrder;
	
	public String getReturnEntireOrder() {
		return returnEntireOrder;
	}

	public void setReturnEntireOrder(String returnEntireOrder) {
		this.returnEntireOrder = returnEntireOrder;
	}

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

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

}
