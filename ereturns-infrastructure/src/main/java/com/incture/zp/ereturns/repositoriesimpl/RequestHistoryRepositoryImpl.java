package com.incture.zp.ereturns.repositoriesimpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.incture.zp.ereturns.dto.RequestHistoryDto;
import com.incture.zp.ereturns.model.RequestHistory;
import com.incture.zp.ereturns.repositories.RequestHistoryRepository;
import com.incture.zp.ereturns.utils.ImportExportUtil;

public class RequestHistoryRepositoryImpl implements RequestHistoryRepository {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	ImportExportUtil importExportUtil;

	@Override
	public List<RequestHistoryDto> getRequestHistory(String requestId) {
		
		List<RequestHistoryDto> requestHistoryDtos = new ArrayList<RequestHistoryDto>();
		
		String queryStr = "select rh from RequestHistory rh where rh.requestId=:requestId";
		Query query = sessionFactory.getCurrentSession().createQuery(queryStr);
		query.setParameter("requestId", requestId);
		@SuppressWarnings("unchecked")
		List<RequestHistory> reqList = query.list();
		for (RequestHistory requestHistory : reqList) {
			requestHistoryDtos.add(importExportUtil.exportRequestHistoryDto(requestHistory));
		}
		return requestHistoryDtos;
	}

}
