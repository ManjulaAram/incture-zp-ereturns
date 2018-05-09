package com.incture.zp.ereturns.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StatusResponseDto implements Serializable {

	private static final long serialVersionUID = 3030045408794478627L;

	private String message;
	
	private String status;
	
	private List<RequestDto> requestDto;
	
	public List<RequestDto> getRequestDto() {
		return requestDto;
	}

	public void setRequestDto(List<RequestDto> requestDto) {
		this.requestDto = requestDto;
	}

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

}
