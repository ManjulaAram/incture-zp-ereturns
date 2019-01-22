package com.incture.zp.ereturns.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserDto {

	private String sciUserId;
	
	private String idpUserId;

	private String mobileToken;
	
	public String getMobileToken() {
		return mobileToken;
	}

	public void setMobileToken(String mobileToken) {
		this.mobileToken = mobileToken;
	}

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

}
