package com.incture.zp.ereturns.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_WORK_FLOW_INS")
public class WorkFlow {

	@Id
	@Column(name = "REQUEST_ID", nullable = false, length = 20)
	private String requestId;
	@Column(name = "WORK_FLOW_INS_ID", length = 100)
	private String workFlowInstanceId;
	@Column(name = "TASK_INS_ID", length = 100)
	private String taskInstanceId;
	@Column(name = "MAT_CODE", length = 20)
	private String materialCode;
	@Column(name = "PRINCIPAL", length = 50)
	private String principal;

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
