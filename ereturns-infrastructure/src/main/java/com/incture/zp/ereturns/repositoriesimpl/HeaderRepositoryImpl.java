package com.incture.zp.ereturns.repositoriesimpl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.incture.zp.ereturns.model.Header;
import com.incture.zp.ereturns.repositories.HeaderRepository;

@Repository
public class HeaderRepositoryImpl implements HeaderRepository {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Header getHeaderById(Long id) {
		return (Header) sessionFactory.getCurrentSession().get(Header.class, id);
	}

}
