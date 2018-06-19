package com.incture.zp.ereturns.servicesimpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.incture.zp.ereturns.dto.CustomerDto;
import com.incture.zp.ereturns.repositories.CustomerRepository;
import com.incture.zp.ereturns.services.CustomerService;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository;
	
	@Override
	public List<CustomerDto> getCustomers() {
		return customerRepository.getCustomers();
	}

	@Override
	public List<CustomerDto> getCustomersBySalesRep(String salesRep) {
		return customerRepository.getCustomersBySalesRep(salesRep);
	}

}
