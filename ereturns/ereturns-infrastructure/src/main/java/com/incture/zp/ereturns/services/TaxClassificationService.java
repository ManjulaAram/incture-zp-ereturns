package com.incture.zp.ereturns.services;

import java.util.List;

import com.incture.zp.ereturns.dto.TaxClassificationDto;

public interface TaxClassificationService {

	public List<TaxClassificationDto> getAllTaxClassifications();
	
	public String getTaxClassificationById(String id);

}
