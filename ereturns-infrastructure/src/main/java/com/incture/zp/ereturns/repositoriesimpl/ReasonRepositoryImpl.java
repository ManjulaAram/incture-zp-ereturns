package com.incture.zp.ereturns.repositoriesimpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.incture.zp.ereturns.dto.ReasonDto;
import com.incture.zp.ereturns.model.Reason;
import com.incture.zp.ereturns.repositories.ReasonRepository;
import com.incture.zp.ereturns.utils.ImportExportUtil;

@Repository
public class ReasonRepositoryImpl implements ReasonRepository {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	ImportExportUtil importExportUtil;

	@SuppressWarnings("unchecked")
	@Override
	public List<ReasonDto> getAllReasons() {

		List<ReasonDto> reasonDtoList = new ArrayList<ReasonDto>();
		List<Reason> reasonList = new ArrayList<Reason>();
		String queryStr = "select r from Reason r";

		Query query = sessionFactory.getCurrentSession().createQuery(queryStr);

		reasonList = query.list();

		for (Reason retRes : reasonList) {
			reasonDtoList.add(importExportUtil.exportReasonDto(retRes));
		}

		return reasonDtoList;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReasonDto> getReasonByCountry(String country) {

		List<ReasonDto> reasonDtoList = new ArrayList<ReasonDto>();
		String queryStr = "select r from Reason r where r.businessUnit=:businessUnit";

		Query query = sessionFactory.getCurrentSession().createQuery(queryStr);
		query.setParameter("businessUnit", country);

		List<Reason> reasonList = query.list();

		for (Reason retRes : reasonList) {
			reasonDtoList.add(importExportUtil.exportReasonDto(retRes));
		}

		return reasonDtoList;

	}
	@Override
	public String getReasonById(String id) {

		String queryStr = "select r from Reason r where r.reasonCode=:reasonCode";

		Query query = sessionFactory.getCurrentSession().createQuery(queryStr);
		query.setParameter("reasonCode", id);

		@SuppressWarnings("unchecked")
		List<Reason> reasonList = (List<Reason>) query.list();
		String reasonDesc = "";
		for (Reason retRes : reasonList) {
			reasonDesc = retRes.getReasonDesc();
		}

		return reasonDesc;

	}

	
}
