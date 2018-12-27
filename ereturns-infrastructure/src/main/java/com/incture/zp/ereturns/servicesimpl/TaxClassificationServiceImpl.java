package com.incture.zp.ereturns.servicesimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incture.zp.ereturns.dto.TaxClassificationDto;
import com.incture.zp.ereturns.repositories.TaxClassificationRepository;
import com.incture.zp.ereturns.services.TaxClassificationService;

@Service
@Transactional
public class TaxClassificationServiceImpl implements TaxClassificationService {

	@Autowired
	TaxClassificationRepository taxClassificationRepository;
	
	@Override
	public List<TaxClassificationDto> getAllTaxClassifications() {
		return taxClassificationRepository.getAllTaxClassifications();
	}

	@Override
	public String getTaxClassificationById(String id) {
		return taxClassificationRepository.getTaxClassificationById(id);
	}

}
