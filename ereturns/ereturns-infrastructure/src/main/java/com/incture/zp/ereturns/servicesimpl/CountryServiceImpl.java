package com.incture.zp.ereturns.servicesimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incture.zp.ereturns.dto.CountryDto;
import com.incture.zp.ereturns.repositories.CountryRepository;
import com.incture.zp.ereturns.services.CountryService;

@Service
@Transactional
public class CountryServiceImpl implements CountryService {

	@Autowired
	CountryRepository countryRepository;
	
	@Override
	public List<CountryDto> getCountries() {
		return countryRepository.getCountries();
	}

}
