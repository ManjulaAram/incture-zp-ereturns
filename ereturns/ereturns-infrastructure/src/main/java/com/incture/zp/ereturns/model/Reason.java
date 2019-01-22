package com.incture.zp.ereturns.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_REASON")
public class Reason {

	@Id
	@Column(name = "REASON_CODE", length = 10,nullable=false)
	private String reasonCode;

	@Column(name = "REASON_DESC", length = 200)
	private String reasonDesc;

	@Column(name = "REASON_NAME", length = 200)
	private String reasonName;

	@Column(name = "BUS_UNIT", length = 20)
	private String businessUnit;

	public String getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	public String getReasonDesc() {
		return reasonDesc;
	}

	public void setReasonDesc(String reasonDesc) {
		this.reasonDesc = reasonDesc;
	}

	public String getReasonName() {
		return reasonName;
	}

	public void setReasonName(String reasonName) {
		this.reasonName = reasonName;
	}

	public String getBusinessUnit() {
		return businessUnit;
	}

	public void setBusinessUnit(String businessUnit) {
		this.businessUnit = businessUnit;
	}

}
