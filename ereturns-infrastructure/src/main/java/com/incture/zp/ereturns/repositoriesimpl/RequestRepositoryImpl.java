package com.incture.zp.ereturns.repositoriesimpl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.incture.zp.ereturns.dto.ReturnRequestDto;
import com.incture.zp.ereturns.dto.StatusRequestDto;
import com.incture.zp.ereturns.model.Request;
import com.incture.zp.ereturns.repositories.RequestRepository;

public class RequestRepositoryImpl implements RequestRepository {

	@Autowired
    private SessionFactory sessionFactory;
	
	@Override
	public void addRequest(Request request) {
		sessionFactory.getCurrentSession().saveOrUpdate(request);		
	}

	@Override
	public List<ReturnRequestDto> getStatusDetails(StatusRequestDto requestDto) {
		String queryString = "SELECT req FROM Request req WHERE req.requestId != null ";
		
		if (requestDto.getRequestId() != null) {
			queryString += " AND req.requestId=:requestId";
		}
		
		if (requestDto.getCreatedBy() != null) {
			queryString += " AND req.requestCreatedBy=:requestCreatedBy";
		}
		return null;
	}

}
