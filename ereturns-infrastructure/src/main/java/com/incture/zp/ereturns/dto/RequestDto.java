package com.incture.zp.ereturns.dto;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RequestDto {

	private String requestId;
	
	private String requestCreatedBy;
	
	private String requestCreatedDate;

	private String requestStatus;
	
	private String requestUpdatedBy;
	
	private String requestUpdatedDate;
	
	private String requestApprovedBy;
	
	private String requestApprovedDate;
	
	private String requestPendingWith;
	
	private String lotNo; // return order level
	
	private String salesPerson; // customer for actual sales order creator
	
	private String salesPersonName;
	
	private String noOfLine; //total no of line items
	
	private String boxQty; // return order level
	
	private String location;
	
	private Set<ReturnOrderDto> setReturnOrderDto;
	
	public String getLotNo() {
		return lotNo;
	}

	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getRequestCreatedBy() {
		return requestCreatedBy;
	}

	public void setRequestCreatedBy(String requestCreatedBy) {
		this.requestCreatedBy = requestCreatedBy;
	}

	public String getRequestCreatedDate() {
		return requestCreatedDate;
	}

	public void setRequestCreatedDate(String requestCreatedDate) {
		this.requestCreatedDate = requestCreatedDate;
	}

	public String getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}

	public String getRequestUpdatedBy() {
		return requestUpdatedBy;
	}

	public void setRequestUpdatedBy(String requestUpdatedBy) {
		this.requestUpdatedBy = requestUpdatedBy;
	}

	public String getRequestUpdatedDate() {
		return requestUpdatedDate;
	}

	public void setRequestUpdatedDate(String requestUpdatedDate) {
		this.requestUpdatedDate = requestUpdatedDate;
	}

	public String getRequestApprovedBy() {
		return requestApprovedBy;
	}

	public void setRequestApprovedBy(String requestApprovedBy) {
		this.requestApprovedBy = requestApprovedBy;
	}

	public String getRequestApprovedDate() {
		return requestApprovedDate;
	}

	public void setRequestApprovedDate(String requestApprovedDate) {
		this.requestApprovedDate = requestApprovedDate;
	}

	public String getRequestPendingWith() {
		return requestPendingWith;
	}

	public void setRequestPendingWith(String requestPendingWith) {
		this.requestPendingWith = requestPendingWith;
	}

	public String getSalesPerson() {
		return salesPerson;
	}

	public void setSalesPerson(String salesPerson) {
		this.salesPerson = salesPerson;
	}

	public String getSalesPersonName() {
		return salesPersonName;
	}

	public void setSalesPersonName(String salesPersonName) {
		this.salesPersonName = salesPersonName;
	}

	public String getNoOfLine() {
		return noOfLine;
	}

	public void setNoOfLine(String noOfLine) {
		this.noOfLine = noOfLine;
	}

	public String getBoxQty() {
		return boxQty;
	}

	public void setBoxQty(String boxQty) {
		this.boxQty = boxQty;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Set<ReturnOrderDto> getSetReturnOrderDto() {
		return setReturnOrderDto;
	}

	public void setSetReturnOrderDto(Set<ReturnOrderDto> setReturnOrderDto) {
		this.setReturnOrderDto = setReturnOrderDto;
	}

}
