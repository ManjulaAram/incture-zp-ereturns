package com.incture.zp.ereturns.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CompleteTaskRequestDto implements Serializable {

	private static final long serialVersionUID = -9176163763906937576L;

	private String loginUser;
	private String requestId;
	private String itemCode;
	private String flag;
	private String orderComments;
	
	private String overridePrice;
	private String role;
	private String overrideRole;

	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getLoginUser() {
		return loginUser;
	}
	public void setLoginUser(String loginUser) {
		this.loginUser = loginUser;
	}
	public String getOrderComments() {
		return orderComments;
	}
	public void setOrderComments(String orderComments) {
		this.orderComments = orderComments;
	}
	public String getOverridePrice() {
		return overridePrice;
	}
	public void setOverridePrice(String overridePrice) {
		this.overridePrice = overridePrice;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getOverrideRole() {
		return overrideRole;
	}
	public void setOverrideRole(String overrideRole) {
		this.overrideRole = overrideRole;
	}
	
}
