package com.incture.zp.ereturns.dto;

import java.io.Serializable;

public class RoleDto implements Serializable {

	private static final long serialVersionUID = -4521650913863709639L;

	private String roleId;
	
	private String roleName;

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
}
