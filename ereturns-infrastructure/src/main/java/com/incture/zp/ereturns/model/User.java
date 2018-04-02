package com.incture.zp.ereturns.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_USER")
public class User {

	@Id
	@Column(name = "USER_ID", nullable=false)
	private String userId;
	
	@Column(name = "USER_CODE", length = 50)
	private String userCode;
	
	@Column(name = "FIRST_NAME", length = 100)
	private String firstName;
	
	@Column(name = "LAST_NAME", length = 100)
	private String lastName;
	
	@Column(name = "EMAIL", length = 100)
	private String email;
	
	@Column(name = "ADDRESS", length = 255)
	private String address;
	
	@Column(name = "SCI_ID", length = 50)
	private String sciId;
	
	@Column(name = "LOT_NO", length = 50)
	private int lotNo;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
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
}
