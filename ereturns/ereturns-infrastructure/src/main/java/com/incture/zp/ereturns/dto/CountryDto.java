package com.incture.zp.ereturns.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CountryDto {

	private String countryCode;
	
	private String countryName;

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	
}
