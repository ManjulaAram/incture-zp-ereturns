package com.incture.zp.ereturns.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class HeaderDto implements Serializable {

	private static final long serialVersionUID = -8547234219630979516L;

	private int invoiceNo;
	
	private String invoiceSeq;
	
	private Date invoiceDate;
	
	private Date expiryDate;
	
	private double invoiceAvailableQty;
	
	private double invoiceNetValue;
	
	private int userCode;
	
	private List<ItemDto> itemList;

	public int getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(int invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getInvoiceSeq() {
		return invoiceSeq;
	}

	public void setInvoiceSeq(String invoiceSeq) {
		this.invoiceSeq = invoiceSeq;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public double getInvoiceAvailableQty() {
		return invoiceAvailableQty;
	}

	public void setInvoiceAvailableQty(double invoiceAvailableQty) {
		this.invoiceAvailableQty = invoiceAvailableQty;
	}

	public double getInvoiceNetValue() {
		return invoiceNetValue;
	}

	public void setInvoiceNetValue(double invoiceNetValue) {
		this.invoiceNetValue = invoiceNetValue;
	}

	public int getUserCode() {
		return userCode;
	}

	public void setUserCode(int userCode) {
		this.userCode = userCode;
	}

	public List<ItemDto> getItemList() {
		return itemList;
	}

	public void setItemList(List<ItemDto> itemList) {
		this.itemList = itemList;
	}
	
}
