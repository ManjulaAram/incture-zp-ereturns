package com.incture.zp.ereturns.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class Item {

	@Column(name = "ITEM_CODE", nullable = false)
	private String itemCode;
	
	@Column(name = "ITEM_NAME", length = 100)
	private String itemName;
	
	@Column(name = "ITEM_DESC", length = 255)
	private String itemDescription;
	
	@Column(name = "AVAILABLE_QTY", length = 5)
	private String availableQty;

	@Column(name = "NET_VALUE", length = 5)
	private String netValue;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EXPIRY_DATE")
	private Date expiryDate;

	@ManyToOne
	@JoinColumn(name = "INVOICE_NO", nullable = false, updatable = false)
	private Header itemData;

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
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

	public String getAvailableQty() {
		return availableQty;
	}

	public void setAvailableQty(String availableQty) {
		this.availableQty = availableQty;
	}

	public String getNetValue() {
		return netValue;
	}

	public void setNetValue(String netValue) {
		this.netValue = netValue;
	}

	public Header getItemData() {
		return itemData;
	}

	public void setItemData(Header itemData) {
		this.itemData = itemData;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

}
