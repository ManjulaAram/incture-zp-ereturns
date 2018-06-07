package com.incture.zp.ereturns.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserMailDto implements Serializable{

	
	private static final long serialVersionUID = 8408716908639225277L;
	private List<String> listOfMailIds;

	public List<String> getListOfMailIds() {
		return listOfMailIds;
	}

	public void setListOfMailIds(List<String> listOfMailIds) {
		this.listOfMailIds = listOfMailIds;
	}
	
	
}
