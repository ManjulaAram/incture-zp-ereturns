package com.incture.zp.ereturns.repositoriesimpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.incture.zp.ereturns.dto.TaxClassificationDto;
import com.incture.zp.ereturns.model.TaxClassification;
import com.incture.zp.ereturns.repositories.TaxClassificationRepository;
import com.incture.zp.ereturns.utils.ImportExportUtil;

@Repository
public class TaxClassificationRepositoryImpl implements TaxClassificationRepository {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	ImportExportUtil importExportUtil;

	@SuppressWarnings("unchecked")
	@Override
	public List<TaxClassificationDto> getAllTaxClassifications() {
		List<TaxClassificationDto> taxDtoList = new ArrayList<TaxClassificationDto>();
		List<TaxClassification> taxList = new ArrayList<TaxClassification>();
		String queryStr = "select tc from TaxClassification tc ORDER BY taxDesc";

		Query query = sessionFactory.getCurrentSession().createQuery(queryStr);

		taxList = query.list();

		for (TaxClassification tax : taxList) {
			taxDtoList.add(importExportUtil.exportTaxClassificationDto(tax));
		}
		return taxDtoList;
	}

	@Override
	public String getTaxClassificationById(String id) {
		String queryStr = "select tc from TaxClassification tc where tc.taxCode=:taxCode";

		Query query = sessionFactory.getCurrentSession().createQuery(queryStr);
		query.setParameter("taxCode", id);

		@SuppressWarnings("unchecked")
		List<TaxClassification> taxList = (List<TaxClassification>) query.list();
		String taxDesc = "";
		for (TaxClassification retRes : taxList) {
			taxDesc = retRes.getTaxDesc();
		}

		return taxDesc;
	}

}
