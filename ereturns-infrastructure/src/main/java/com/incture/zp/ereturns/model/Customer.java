package com.incture.zp.ereturns.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_CUSTOMER")
public class Customer {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;

	@Column(name = "CUSTOMER_CODE", length = 50, nullable = false)
	private String customerCode;
	
	@Column(name = "CUSTOMER_NAME")
	private String customerName;

	@Column(name = "SALES_REP", length = 50)
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
