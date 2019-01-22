package com.incture.zp.ereturns.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CustomerDto implements Serializable {

	private static final long serialVersionUID = 5750773549012394706L;

	private String customerCode;
	
	private String customerName;

	private String saleRep;

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

	public String getSaleRep() {
		return saleRep;
	}

	public void setSaleRep(String saleRep) {
		this.saleRep = saleRep;
	}

}
