package com.incture.zp.ereturns.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_USER")
public class User {

	@Id
	@Column(name = "IDP_USER_ID", nullable = false)
	private String idpUserId;
	
	@Column(name = "SCI_USER_ID", nullable = false)
	private String sciUserId;

	@Column(name = "MOBILE_TOKEN", length = 255)
	private String mobileToken;
	
	public String getSciUserId() {
		return sciUserId;
	}
	public void setSciUserId(String sciUserId) {
		this.sciUserId = sciUserId;
	}
	public String getIdpUserId() {
		return idpUserId;
	}
	public void setIdpUserId(String idpUserId) {
		this.idpUserId = idpUserId;
	}
	public String getMobileToken() {
		return mobileToken;
	}
	public void setMobileToken(String mobileToken) {
		this.mobileToken = mobileToken;
	}
}
