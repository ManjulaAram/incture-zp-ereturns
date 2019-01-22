package com.incture.zp.ereturns.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WorkFlowDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3582711983158216234L;
	private Long workflowId;
	private String requestId;
	private String workFlowInstanceId;
	private String taskInstanceId;
	private String materialCode;
	private String principal;

	
	public Long getWorkflowId() {
		return workflowId;
	}

	public void setWorkflowId(Long workflowId) {
		this.workflowId = workflowId;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getWorkFlowInstanceId() {
		return workFlowInstanceId;
	}

	public void setWorkFlowInstanceId(String workFlowInstanceId) {
		this.workFlowInstanceId = workFlowInstanceId;
	}

	public String getTaskInstanceId() {
		return taskInstanceId;
	}

	public void setTaskInstanceId(String taskInstanceId) {
		this.taskInstanceId = taskInstanceId;
	}

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

}
