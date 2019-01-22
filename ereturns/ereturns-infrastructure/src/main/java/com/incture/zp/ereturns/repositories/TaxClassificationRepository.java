package com.incture.zp.ereturns.repositories;

import java.util.List;

import com.incture.zp.ereturns.dto.TaxClassificationDto;

public interface TaxClassificationRepository {

	public List<TaxClassificationDto> getAllTaxClassifications();
	
	public String getTaxClassificationById(String id);
}
