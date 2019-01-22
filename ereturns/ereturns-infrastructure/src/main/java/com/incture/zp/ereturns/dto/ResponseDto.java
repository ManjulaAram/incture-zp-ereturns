package com.incture.zp.ereturns.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResponseDto {

	private String code;
	
	private String message;
	
	private String status;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
