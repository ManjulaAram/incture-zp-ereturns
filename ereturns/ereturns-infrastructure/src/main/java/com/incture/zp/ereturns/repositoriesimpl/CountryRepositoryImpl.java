package com.incture.zp.ereturns.repositoriesimpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.incture.zp.ereturns.dto.CountryDto;
import com.incture.zp.ereturns.model.Country;
import com.incture.zp.ereturns.repositories.CountryRepository;
import com.incture.zp.ereturns.utils.ImportExportUtil;

@Repository
public class CountryRepositoryImpl implements CountryRepository {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	ImportExportUtil importExportUtil;

	@SuppressWarnings("unchecked")
	@Override
	public List<CountryDto> getCountries() {
		List<CountryDto> countryDtoList = new ArrayList<CountryDto>();
		List<Country> countryList = new ArrayList<Country>();
		String queryStr = "select c from Country c ORDER BY countryCode";

		Query query = sessionFactory.getCurrentSession().createQuery(queryStr);
		countryList = query.list();
		for (Country countryRes : countryList) {
			countryDtoList.add(importExportUtil.exportCountryDto(countryRes));
		}
		return countryDtoList;
	}

}
