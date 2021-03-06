package com.incture.zp.ereturns.repositoriesimpl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.incture.zp.ereturns.constants.EReturnConstants;
import com.incture.zp.ereturns.dto.RequestHistoryDto;
import com.incture.zp.ereturns.dto.ResponseDto;
import com.incture.zp.ereturns.model.RequestHistory;
import com.incture.zp.ereturns.repositories.RequestHistoryRepository;
import com.incture.zp.ereturns.utils.ImportExportUtil;

@Repository
public class RequestHistoryRepositoryImpl implements RequestHistoryRepository {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	ImportExportUtil importExportUtil;
	
	@Override
	public List<RequestHistoryDto> getRequestHistory(String requestId, String itemCode) {
		
		List<RequestHistoryDto> requestHistoryDtos = new ArrayList<RequestHistoryDto>();
		
		String queryStr = "select rh from RequestHistory rh where rh.requestId=:requestId and rh.itemCode=:itemCode";
		Query query = sessionFactory.getCurrentSession().createQuery(queryStr);
		query.setParameter("requestId", requestId);
		query.setParameter("itemCode", itemCode);
		@SuppressWarnings("unchecked")
		List<RequestHistory> reqList = query.list();
		for (RequestHistory requestHistory : reqList) {
			requestHistoryDtos.add(importExportUtil.exportRequestHistoryDto(requestHistory));
		}
		return requestHistoryDtos;
	}

	public List<RequestHistoryDto> getApprovedBy(String user) {
		
		List<RequestHistoryDto> requestHistoryDtos = new ArrayList<RequestHistoryDto>();
		
		String queryStr = "select rh from RequestHistory rh where rh.requestApprovedBy=:requestApprovedBy";
		Query query = sessionFactory.getCurrentSession().createQuery(queryStr);
		query.setParameter("requestApprovedBy", user);
		@SuppressWarnings("unchecked")
		List<RequestHistory> reqList = query.list();
		for (RequestHistory requestHistory : reqList) {
			requestHistoryDtos.add(importExportUtil.exportRequestHistoryDto(requestHistory));
		}
		
		return requestHistoryDtos;
	}
	
	@Override
	@Transactional(value = TxType.REQUIRES_NEW) 
	public ResponseDto addRequestHistory(RequestHistory requestHistory) {
		ResponseDto responseDto = new ResponseDto();
		sessionFactory.getCurrentSession().saveOrUpdate(requestHistory);
		responseDto.setMessage("created successfully");
		responseDto.setStatus(EReturnConstants.SUCCESS_STATUS);
		responseDto.setCode(EReturnConstants.SUCCESS_STATUS_CODE);
		return responseDto;
	}
	
}
