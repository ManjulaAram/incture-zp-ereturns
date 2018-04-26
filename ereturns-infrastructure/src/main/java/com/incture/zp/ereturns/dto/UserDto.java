package com.incture.zp.ereturns.dto;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserDto {

	private String userId;
	
	private String userCode;
	
	private String userName;
	
	private String email;
	
	private String address;
	
	private String sciId;
	
	private String mobileToken;
	
	private String webToken;

	private Set<HeaderDto> headerSet;
	
	private Set<RoleDto> roleSet;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSciId() {
		return sciId;
	}

	public void setSciId(String sciId) {
		this.sciId = sciId;
	}

	public Set<HeaderDto> getHeaderSet() {
		return headerSet;
	}

	public void setHeaderSet(Set<HeaderDto> headerSet) {
		this.headerSet = headerSet;
	}

	public Set<RoleDto> getRoleSet() {
		return roleSet;
	}

	public void setRoleSet(Set<RoleDto> roleSet) {
		this.roleSet = roleSet;
	}

	public String getMobileToken() {
		return mobileToken;
	}

	public void setMobileToken(String mobileToken) {
		this.mobileToken = mobileToken;
	}

	public String getWebToken() {
		return webToken;
	}

	public void setWebToken(String webToken) {
		this.webToken = webToken;
	}

}
