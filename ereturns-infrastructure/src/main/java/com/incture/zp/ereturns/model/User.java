package com.incture.zp.ereturns.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "T_USER")
public class User {

	@Id
	@Column(name = "USER_ID", nullable = false)
	private String userId;
	
	@Column(name = "USER_CODE", length = 50)
	private String userCode;
	
	@Column(name = "USER_NAME", length = 100)
	private String userName;
	
	@Column(name = "EMAIL", length = 100)
	private String email;
	
	@Column(name = "ADDRESS", length = 255)
	private String address;
	
	@Column(name = "SCI_ID", length = 50)
	private String sciId;
	
	@Column(name = "MOBILE_TOKEN", length = 50)
	private String mobileToken;
	
	@Column(name = "WEB_TOKEN", length = 50)
	private String webToken;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "headerData")
	private Set<Header> setHeader;

	private String roleId;

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
	public Set<Header> getSetHeader() {
		return setHeader;
	}
	public void setSetHeader(Set<Header> setHeader) {
		this.setHeader = setHeader;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

}
