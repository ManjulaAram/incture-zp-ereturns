package com.incture.zp.ereturns.repositories;

import java.util.List;

import com.incture.zp.ereturns.dto.CountryDto;

public interface CountryRepository {

	public List<CountryDto> getCountries();
}
