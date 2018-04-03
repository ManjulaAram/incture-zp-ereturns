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
	
	@Column(name = "LOT_NO", length = 50)
	private String lotNo;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "headerData")
	private Set<Header> setHeader;
	
//	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "requestData")
//	private Set<Request> setRequest;
	
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
	public String getLotNo() {
		return lotNo;
	}
	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
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
	
	
//	public Set<Request> getSetRequest() {
//		return setRequest;
//	}
//	public void setSetRequest(Set<Request> setRequest) {
//		this.setRequest = setRequest;
//	}
	
}
