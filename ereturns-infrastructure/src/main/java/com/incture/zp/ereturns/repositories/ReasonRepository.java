package com.incture.zp.ereturns.repositories;

import java.util.List;

import com.incture.zp.ereturns.dto.ReasonDto;

public interface ReasonRepository {
	public List<ReasonDto> getAllReasons();
	public List<ReasonDto> getReasonByCountry(String country);
	
	public String getReasonById(String id);
}
