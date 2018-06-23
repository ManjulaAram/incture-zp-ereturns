package com.incture.zp.ereturns.services;

import java.util.List;

import com.incture.zp.ereturns.dto.ReasonDto;

public interface ReasonService {
	
	public List<ReasonDto> getAllReasons();

	public List<ReasonDto> getReasonByCountry(String country);
}
