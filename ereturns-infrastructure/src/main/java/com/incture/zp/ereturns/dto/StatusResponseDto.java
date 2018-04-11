package com.incture.zp.ereturns.dto;

import java.io.Serializable;

public class StatusResponseDto implements Serializable {

	private static final long serialVersionUID = 3030045408794478627L;

	private RequestDto requestDto;

	public RequestDto getRequestDto() {
		return requestDto;
	}

	public void setRequestDto(RequestDto requestDto) {
		this.requestDto = requestDto;
	}
	
}
