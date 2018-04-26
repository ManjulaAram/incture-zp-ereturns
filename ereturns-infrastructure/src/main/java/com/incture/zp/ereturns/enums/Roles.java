package com.incture.zp.ereturns.enums;

public enum Roles {
	SALES_REP("Sales_Representative"), 
	PRICIPAL("Principal"), 
	ZP_APPROVER("ZP_Approver"), 
	MAINTENANCE_ENGINEER("ZP_Maintenance_Engineer"), 
	CUSTOMER("Customer");
	
	String role;
	
	private Roles(String role) {
		this.role = role;
	}
	
	public String getRole() {
		return this.role;
	}
}
