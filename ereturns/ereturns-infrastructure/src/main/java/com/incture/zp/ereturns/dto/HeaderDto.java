package com.incture.zp.ereturns.dto;

import java.util.Set;

public class HeaderDto {

	private Long headerId;
	
	private String invoiceNo;
	
	private String invoiceSeq;
	
	private String invoiceDate;
	
	private String documentType;
	
	private String salesOrder;
	
	private String salesOrg;

	private String deliveryNo;

	private String distrChan;

	private String division;

	private String currency;

	private String refDocCat;

	private String purchNoCust;

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

	public String getSalesOrg() {
		return salesOrg;
	}

	public void setSalesOrg(String salesOrg) {
		this.salesOrg = salesOrg;
	}

	public String getDeliveryNo() {
		return deliveryNo;
	}

	public void setDeliveryNo(String deliveryNo) {
		this.deliveryNo = deliveryNo;
	}

	public String getDistrChan() {
		return distrChan;
	}

	public void setDistrChan(String distrChan) {
		this.distrChan = distrChan;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getRefDocCat() {
		return refDocCat;
	}

	public void setRefDocCat(String refDocCat) {
		this.refDocCat = refDocCat;
	}

	public String getPurchNoCust() {
		return purchNoCust;
	}

	public void setPurchNoCust(String purchNoCust) {
		this.purchNoCust = purchNoCust;
	}

	public Long getHeaderId() {
		return headerId;
	}

	public void setHeaderId(Long headerId) {
		this.headerId = headerId;
	}

}
