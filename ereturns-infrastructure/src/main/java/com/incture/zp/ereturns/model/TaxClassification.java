package com.incture.zp.ereturns.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_TAX_CLASSIFICATION")
public class TaxClassification {

	@Id
	@Column(name = "TAX_CODE", nullable=false)
	private String taxCode;

	@Column(name = "TAX_DESC")
	private String taxDesc;

	public String getTaxCode() {
		return taxCode;
	}

	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}

	public String getTaxDesc() {
		return taxDesc;
	}

	public void setTaxDesc(String taxDesc) {
		this.taxDesc = taxDesc;
	}

}
