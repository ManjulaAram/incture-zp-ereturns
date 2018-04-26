package com.incture.zp.ereturns.enums;

public enum RequestStatus {
	NEW("New"), 
	SUBMIT("Submitted"), 
	APPROVE("Approved"), 
	REJECT("Rejected");
	
	String status;
	
	private RequestStatus(String status) {
		this.status = status;
	}
	
	public String getReqStatus() {
		return this.status;
	}
}
