package com.incture.zp.ereturns.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class EmailDto {

	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
