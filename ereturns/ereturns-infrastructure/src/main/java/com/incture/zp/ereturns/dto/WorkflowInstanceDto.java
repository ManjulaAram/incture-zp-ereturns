package com.incture.zp.ereturns.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WorkflowInstanceDto implements Serializable {

	private static final long serialVersionUID = -6262133189534334957L;
	
	private String materialCode;
	private String requestId;
	private String createdBy;
	private String createdAt;
	private String pendingWith;
	private String status;
	private List<ApproverDto> approverList;
	private String completedAt;
	private List<String> receipents;
	private String eccResponse;
	
	private String eccStatus;
	
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	public String getPendingWith() {
		return pendingWith;
	}
	public void setPendingWith(String pendingWith) {
		this.pendingWith = pendingWith;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	
	public String getMaterialCode() {
		return materialCode;
	}
	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}
	public List<String> getReceipents() {
		return receipents;
	}
	public void setReceipents(List<String> receipents) {
		this.receipents = receipents;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	
	public List<ApproverDto> getApproverList() {
		return approverList;
	}
	public void setApproverList(List<ApproverDto> approverList) {
		this.approverList = approverList;
	}
	public String getCompletedAt() {
		return completedAt;
	}
	public void setCompletedAt(String completedAt) {
		this.completedAt = completedAt;
	}
	public String getEccResponse() {
		return eccResponse;
	}
	public void setEccResponse(String eccResponse) {
		this.eccResponse = eccResponse;
	}
	public String getEccStatus() {
		return eccStatus;
	}
	public void setEccStatus(String eccStatus) {
		this.eccStatus = eccStatus;
	}
	
}
