package com.incture.zp.ereturns.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SearchDto implements Serializable {
	
	private static final long serialVersionUID = -5817854547416608687L;

	private String customerCode;
	
	private String customerName;
	
	private String invoiceNo;
	
	private String salesOrder;
	
	private String barCode;
	
	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getSalesOrder() {
		return salesOrder;
	}

	public void setSalesOrder(String salesOrder) {
		this.salesOrder = salesOrder;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	
}
