package com.incture.zp.ereturns.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ReturnResponseDto implements Serializable {

	private static final long serialVersionUID = 8805105117679036663L;

	private String status;
	
	private String message;
	
	private RequestDto requestDto;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public RequestDto getRequestDto() {
		return requestDto;
	}

	public void setRequestDto(RequestDto requestDto) {
		this.requestDto = requestDto;
	}
	
}
