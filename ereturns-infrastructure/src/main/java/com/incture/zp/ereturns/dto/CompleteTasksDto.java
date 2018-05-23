package com.incture.zp.ereturns.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CompleteTasksDto implements Serializable {

	private static final long serialVersionUID = -456106637876141499L;

	private List<CompleteTaskRequestDto> completeRequestDto;

	public List<CompleteTaskRequestDto> getCompleteRequestDto() {
		return completeRequestDto;
	}

	public void setCompleteRequestDto(List<CompleteTaskRequestDto> completeRequestDto) {
		this.completeRequestDto = completeRequestDto;
	}
	
}
