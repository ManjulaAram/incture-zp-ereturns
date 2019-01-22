package com.incture.zp.ereturns.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ReasonDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7182516163792695018L;
	private String reasonCode;
	private String reasonDesc;
	private String reasonName;
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
