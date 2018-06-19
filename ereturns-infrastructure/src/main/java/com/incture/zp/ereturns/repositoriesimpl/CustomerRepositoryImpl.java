package com.incture.zp.ereturns.repositoriesimpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.incture.zp.ereturns.dto.CustomerDto;
import com.incture.zp.ereturns.model.Customer;
import com.incture.zp.ereturns.repositories.CustomerRepository;
import com.incture.zp.ereturns.utils.ImportExportUtil;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	ImportExportUtil importExportUtil;

	@Override
	public List<CustomerDto> getCustomers() {
		List<CustomerDto> customerList = new ArrayList<CustomerDto>();
		
		String queryStr = "select c from Customer c";
		Query query = sessionFactory.getCurrentSession().createQuery(queryStr);
		@SuppressWarnings("unchecked")
		List<Customer> custList = query.list();
		for (Customer customer : custList) {
			customerList.add(importExportUtil.exportCustomerDto(customer));
		}
		return customerList;
	}

	@Override
	public List<CustomerDto> getCustomersBySalesRep(String salesRep) {
		List<CustomerDto> customerList = new ArrayList<CustomerDto>();
		
		String queryStr = "select c from Customer c where c.saleRep=:saleRep";
		Query query = sessionFactory.getCurrentSession().createQuery(queryStr);
		query.setParameter("saleRep", salesRep);
		@SuppressWarnings("unchecked")
		List<Customer> custList = query.list();
		for (Customer customer : custList) {
			customerList.add(importExportUtil.exportCustomerDto(customer));
		}
		return customerList;

	}

}
