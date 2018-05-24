package com.incture.zp.ereturns.dto;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserDto {

	private String userId;
	
	private String userName;
	
	private String email;
	
	private String sciId;
	
	private String mobileToken;
	
	private String webToken;

	private Set<RoleDto> setRole;
	
	private String phone;
	
	private String iosToken;

	private String sapAccessToken;

	private String sapRefreshToken;

	public String getIosToken() {
		return iosToken;
	}

	public void setIosToken(String iosToken) {
		this.iosToken = iosToken;
	}

	public String getSapAccessToken() {
		return sapAccessToken;
	}

	public void setSapAccessToken(String sapAccessToken) {
		this.sapAccessToken = sapAccessToken;
	}

	public String getSapRefreshToken() {
		return sapRefreshToken;
	}

	public void setSapRefreshToken(String sapRefreshToken) {
		this.sapRefreshToken = sapRefreshToken;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getSciId() {
		return sciId;
	}

	public void setSciId(String sciId) {
		this.sciId = sciId;
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

	public Set<RoleDto> getSetRole() {
		return setRole;
	}

	public void setSetRole(Set<RoleDto> setRole) {
		this.setRole = setRole;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
