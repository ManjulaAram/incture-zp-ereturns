package com.incture.zp.ereturns.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "T_INVOICE_HEADER")
public class Header {
	
	@Id
	@Column(name = "HEADER_ID")
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long headerId;

	@Column(name = "INVOICE_NO", length = 50)
	private String invoiceNo;

	@Column(name = "INVOICE_SEQ", length = 50)
	private String invoiceSeq;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "INVOICE_DATE")
	private Date invoiceDate;
	
	@Column(name = "DOCUMENT_TYPE", length = 50)
	private String documentType;
	
	@Column(name = "SALES_ORDER", length = 50)
	private String salesOrder;

	@Column(name = "SALES_ORG", length = 50)
	private String salesOrg;

	@Column(name = "DELIVERY_NO", length = 50)
	private String deliveryNo;

	@Column(name = "DISTR_CHAN", length = 50)
	private String distrChan;

	@Column(name = "DIVISION", length = 50)
	private String division;

	@Column(name = "CURRENCY", length = 50)
	private String currency;

	@Column(name = "REF_DOC_CAT", length = 50)
	private String refDocCat;

	@Column(name = "PURCH_NO_C", length = 50)
	private String purchNoCust;

	@Column(name = "NET_VALUE", length = 50)
	private String netValue; // sum of all item level net value to be calculated

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "itemData")
	private Set<Item> setItem;
	
	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
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

	public String getNetValue() {
		return netValue;
	}

	public void setNetValue(String netValue) {
		this.netValue = netValue;
	}

	public Set<Item> getSetItem() {
		return setItem;
	}

	public void setSetItem(Set<Item> setItem) {
		this.setItem = setItem;
	}

	public String getInvoiceSeq() {
		return invoiceSeq;
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
