package com.incture.zp.ereturns.dto;

import java.io.Serializable;

public class ItemDto implements Serializable {

	private static final long serialVersionUID = 5035424512341634171L;

	private int itemCode;
	
	private String itemName;
	
	private String itemDescription;
	
	private double netValue;
	
	private double availableQty;

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

	public double getNetValue() {
		return netValue;
	}

	public void setNetValue(double netValue) {
		this.netValue = netValue;
	}

	public double getAvailableQty() {
		return availableQty;
	}

	public void setAvailableQty(double availableQty) {
		this.availableQty = availableQty;
	}
	
}
