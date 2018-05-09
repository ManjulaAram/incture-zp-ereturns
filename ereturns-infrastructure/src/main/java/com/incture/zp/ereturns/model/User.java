package com.incture.zp.ereturns.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "T_USER")
public class User {

	@Id
	@Column(name = "USER_ID", nullable = false)
	private String userId;
	
	@Column(name = "USER_NAME", length = 100)
	private String userName;
	
	@Column(name = "EMAIL", length = 100)
	private String email;

	@Column(name = "PHONE", length = 100)
	private String phone;

	@Column(name = "SCI_ID", length = 50)
	private String sciId;
	
	@Column(name = "MOBILE_TOKEN", length = 255)
	private String mobileToken;
	
	@Column(name = "WEB_TOKEN", length = 255)
	private String webToken;
	
	@ManyToMany
	@JoinTable(name = "T_USER_ROLE", joinColumns = @JoinColumn(name = "USER_ID"), 
	inverseJoinColumns = @JoinColumn(name = "ROLE_ID") )
	private Set<Role> roleDetails = new HashSet<Role>(0);

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Set<Role> getRoleDetails() {
		return roleDetails;
	}
	public void setRoleDetails(Set<Role> roleDetails) {
		this.roleDetails = roleDetails;
	}
	
}
