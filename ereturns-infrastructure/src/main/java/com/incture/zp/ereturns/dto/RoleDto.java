package com.incture.zp.ereturns.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RoleDto implements Serializable {

	private static final long serialVersionUID = -4521650913863709639L;

	private List<String> role;
	private String userId;
	
	public List<String> getRole() {
		return role;
	}
	public void setRole(List<String> role) {
		this.role = role;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
