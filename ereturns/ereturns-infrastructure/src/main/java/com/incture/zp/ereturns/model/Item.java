package com.incture.zp.ereturns.model;

import javax.persistence.Column;

public class Item {

	private int itemCode;
	
	private String itemName;
	
	private String itemDescription;
	
	private double returnPrice;
	
	private double returnValue;
	
	@Column(name = "AVAILABLE_QTY")
	private double availableQty;

	private double netValue;
	
	private int invoiceNo; // foreign key

	public int getItemCode() {
		return itemCode;
	}

	public void setItemCode(int itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public double getReturnPrice() {
		return returnPrice;
	}

	public void setReturnPrice(double returnPrice) {
		this.returnPrice = returnPrice;
	}

	public double getReturnValue() {
		return returnValue;
	}

	public void setReturnValue(double returnValue) {
		this.returnValue = returnValue;
	}

	public double getAvailableQty() {
		return availableQty;
	}

	public void setAvailableQty(double availableQty) {
		this.availableQty = availableQty;
	}

	public double getNetValue() {
		return netValue;
	}

	public void setNetValue(double netValue) {
		this.netValue = netValue;
	}

	public int getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(int invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	
}
