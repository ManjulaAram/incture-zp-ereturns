package com.incture.zp.ereturns.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StatusPendingDto implements Serializable {

	private static final long serialVersionUID = -7180769242575257939L;

	private int pending;
	private int approved;
	private int rejected;
	private List<String> pendingRequest;
	private List<String> approvedRequest;
	private List<String> rejectedRequest;
	
	public int getPending() {
		return pending;
	}
	public void setPending(int pending) {
		this.pending = pending;
	}
	public int getApproved() {
		return approved;
	}
	public void setApproved(int approved) {
		this.approved = approved;
	}
	public int getRejected() {
		return rejected;
	}
	public void setRejected(int rejected) {
		this.rejected = rejected;
	}
	public List<String> getPendingRequest() {
		return pendingRequest;
	}
	public void setPendingRequest(List<String> pendingRequest) {
		this.pendingRequest = pendingRequest;
	}
	public List<String> getApprovedRequest() {
		return approvedRequest;
	}
	public void setApprovedRequest(List<String> approvedRequest) {
		this.approvedRequest = approvedRequest;
	}
	public List<String> getRejectedRequest() {
		return rejectedRequest;
	}
	public void setRejectedRequest(List<String> rejectedRequest) {
		this.rejectedRequest = rejectedRequest;
	}
	
}
