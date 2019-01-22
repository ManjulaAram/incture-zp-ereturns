package com.incture.zp.ereturns.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class EmailRequestDto implements Serializable {


	private static final long serialVersionUID = 8772589652007467898L;

	private List<String> emailIds;
	
	private String requestId;
	private String customerName;
	private String invoice;
	private String material;
	
	private String action;
	private String subject;
	
	private String tomailIds;
	private String sflag;
	
	public List<String> getEmailIds() {
		return emailIds;
	}

	public void setEmailIds(List<String> emailIds) {
		this.emailIds = emailIds;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getInvoice() {
		return invoice;
	}

	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTomailIds() {
		return tomailIds;
	}

	public void setTomailIds(String tomailIds) {
		this.tomailIds = tomailIds;
	}

	public String getSflag() {
		return sflag;
	}

	public void setSflag(String sflag) {
		this.sflag = sflag;
	}

	
}
