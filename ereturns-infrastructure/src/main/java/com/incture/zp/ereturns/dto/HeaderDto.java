package com.incture.zp.ereturns.dto;

import java.util.Set;

public class HeaderDto {

	private String invoiceNo;
	
	private String invoiceSeq;
	
	private String invoiceDate;
	
	private String expiryDate;
	
	private String availableQty;
	
	private String documentType;
	
	private String salesOrder;

	private String netValue; // sum of all item level net value to be calculated
	
	private Set<ItemDto> itemSet;
	
	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getInvoiceSeq() {
		return invoiceSeq;
	}

	public void setInvoiceSeq(String invoiceSeq) {
		this.invoiceSeq = invoiceSeq;
	}

	public String getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getNetValue() {
		return netValue;
	}

	public void setNetValue(String netValue) {
		this.netValue = netValue;
	}


	public Set<ItemDto> getItemSet() {
		return itemSet;
	}

	public void setItemSet(Set<ItemDto> itemSet) {
		this.itemSet = itemSet;
	}

	public String getAvailableQty() {
		return availableQty;
	}

	public void setAvailableQty(String availableQty) {
		this.availableQty = availableQty;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getSalesOrder() {
		return salesOrder;
	}

	public void setSalesOrder(String salesOrder) {
		this.salesOrder = salesOrder;
	}

}
