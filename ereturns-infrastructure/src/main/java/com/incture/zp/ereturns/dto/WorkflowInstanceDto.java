package com.incture.zp.ereturns.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WorkflowInstanceDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6262133189534334957L;
	
	private String materialCode;
	private String requestId;
	private String createdBy;
	private String createdAt;
	private String pendingWith;
	private String status;
	private Map<String,String> approvedBy;
	private String completedAt;
	private List<String> receipents;
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
	public Map<String, String> getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(Map<String, String> approvedBy) {
		this.approvedBy = approvedBy;
	}
	public String getCompletedAt() {
		return completedAt;
	}
	public void setCompletedAt(String completedAt) {
		this.completedAt = completedAt;
	}
	
	
	
		
}
