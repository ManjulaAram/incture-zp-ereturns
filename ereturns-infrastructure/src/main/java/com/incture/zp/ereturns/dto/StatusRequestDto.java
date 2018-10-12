package com.incture.zp.ereturns.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StatusRequestDto implements Serializable {

	private static final long serialVersionUID = -5180005546856108985L;

	private String customerCode;
	
	private String customerName;
	
	private String createdBy;
	
	private String principalCode;
	
	private String requestId;
	
	private String startDate;
	
	private String endDate;
	
	private List<String> pendingWith;

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

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getPrincipalCode() {
		return principalCode;
	}

	public void setPrincipalCode(String principalCode) {
		this.principalCode = principalCode;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public List<String> getPendingWith() {
		return pendingWith;
	}

	public void setPendingWith(List<String> pendingWith) {
		this.pendingWith = pendingWith;
	}

}
