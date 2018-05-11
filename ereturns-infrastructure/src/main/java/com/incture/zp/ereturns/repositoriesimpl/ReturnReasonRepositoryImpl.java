package com.incture.zp.ereturns.repositoriesimpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.incture.zp.ereturns.dto.ReturnReasonDto;
import com.incture.zp.ereturns.model.ReturnReason;
import com.incture.zp.ereturns.repositories.ReturnReasonRepository;
import com.incture.zp.ereturns.utils.ImportExportUtil;

@Repository
public class ReturnReasonRepositoryImpl implements ReturnReasonRepository {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	ImportExportUtil importExportUtil;

	@SuppressWarnings("unchecked")
	@Override
	public List<ReturnReasonDto> getAllReasons() {

		List<ReturnReasonDto> reasonDtoList = new ArrayList<ReturnReasonDto>();
		List<ReturnReason> reasonList = new ArrayList<ReturnReason>();
		String queryStr = "select r from ReturnReason r";

		Query query = sessionFactory.getCurrentSession().createQuery(queryStr);

		reasonList = query.list();

		for (ReturnReason retRes : reasonList) {
			reasonDtoList.add(importExportUtil.exportDto(retRes));
		}

		return reasonDtoList;

	}

	
}
