package com.incture.zp.ereturns.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ReturnRequestDto implements Serializable {

	private static final long serialVersionUID = 3882600876927486870L;
	
	private String requestId;
	
	private String requestCreatedBy;
	
	private Date requestCreatedDate;

	private String requestStatus;
	
	private String requestUpdatedBy;
	
	private Date requestUpdatedDate;
	
	private String requestApprovedBy;
	
	private Date requestApprovedDate;
	
	private String requestPendingWith;
	
	private String userCode;
	
	private String userName;
	
	private String email;
	
	private String address;
	
	private String lotNo;
	
	private String documentType;
	
	private String salesDocument;
	
	private String soldToParty;
	
	private String salesPerson;
	
	private String salesPersonName;
	
	private String noOfLine;
	
	private String boxQty;
	
	private String location;
	
	private String remarks;
	
	private String returnQty;
	
	private String returnValue;
	
	private String returnPrice;
	
	private String returnReason;
	
	private String returnEntireOrder;

	private List<HeaderDto> headerList;

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLotNo() {
		return lotNo;
	}

	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}

	public List<HeaderDto> getHeaderList() {
		return headerList;
	}

	public void setHeaderList(List<HeaderDto> headerList) {
		this.headerList = headerList;
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

	public Date getRequestCreatedDate() {
		return requestCreatedDate;
	}

	public void setRequestCreatedDate(Date requestCreatedDate) {
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

	public Date getRequestUpdatedDate() {
		return requestUpdatedDate;
	}

	public void setRequestUpdatedDate(Date requestUpdatedDate) {
		this.requestUpdatedDate = requestUpdatedDate;
	}

	public String getRequestApprovedBy() {
		return requestApprovedBy;
	}

	public void setRequestApprovedBy(String requestApprovedBy) {
		this.requestApprovedBy = requestApprovedBy;
	}

	public Date getRequestApprovedDate() {
		return requestApprovedDate;
	}

	public void setRequestApprovedDate(Date requestApprovedDate) {
		this.requestApprovedDate = requestApprovedDate;
	}

	public String getRequestPendingWith() {
		return requestPendingWith;
	}

	public void setRequestPendingWith(String requestPendingWith) {
		this.requestPendingWith = requestPendingWith;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getSalesDocument() {
		return salesDocument;
	}

	public void setSalesDocument(String salesDocument) {
		this.salesDocument = salesDocument;
	}

	public String getSoldToParty() {
		return soldToParty;
	}

	public void setSoldToParty(String soldToParty) {
		this.soldToParty = soldToParty;
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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getReturnQty() {
		return returnQty;
	}

	public void setReturnQty(String returnQty) {
		this.returnQty = returnQty;
	}

	public String getReturnValue() {
		return returnValue;
	}

	public void setReturnValue(String returnValue) {
		this.returnValue = returnValue;
	}

	public String getReturnPrice() {
		return returnPrice;
	}

	public void setReturnPrice(String returnPrice) {
		this.returnPrice = returnPrice;
	}

	public String getReturnReason() {
		return returnReason;
	}

	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
	}

	public String getReturnEntireOrder() {
		return returnEntireOrder;
	}

	public void setReturnEntireOrder(String returnEntireOrder) {
		this.returnEntireOrder = returnEntireOrder;
	}

}
