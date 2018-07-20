package com.incture.zp.ereturns.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class IdpUserDetailsDto {

	private String email;
	private String userName;
	private String role;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
}
