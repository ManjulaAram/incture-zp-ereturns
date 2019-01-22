package com.incture.zp.ereturns.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ApproverDto implements Serializable {

	private static final long serialVersionUID = 2136467634690190341L;
	private String status;
	private String approverName;
	private String approvalDate;
	private String commentsByApprover;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getApproverName() {
		return approverName;
	}
	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}
	public String getApprovalDate() {
		return approvalDate;
	}
	public void setApprovalDate(String approvalDate) {
		this.approvalDate = approvalDate;
	}
	public String getCommentsByApprover() {
		return commentsByApprover;
	}
	public void setCommentsByApprover(String commentsByApprover) {
		this.commentsByApprover = commentsByApprover;
	}
	
}
