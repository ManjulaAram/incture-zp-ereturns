package com.incture.zp.ereturns.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_WORKFLOW_INSTANCE")
public class WorkFlow {

	@Id
	@Column(name = "WORKFLOW_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long workflowId;
	@Column(name = "REQUEST_ID", length = 100)
	private String requestId;
	@Column(name = "WORKFLOW_INSTANCE_ID", length = 100)
	private String workFlowInstanceId;
	@Column(name = "TASK_INSTANCE_ID", length = 100)
	private String taskInstanceId;
	@Column(name = "MATERIAL_CODE", length = 20)
	private String materialCode;
	@Column(name = "PRINCIPAL", length = 50)
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
