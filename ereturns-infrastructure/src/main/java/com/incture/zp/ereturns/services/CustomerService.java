package com.incture.zp.ereturns.services;

import java.util.List;

import com.incture.zp.ereturns.dto.CustomerDto;

public interface CustomerService {

	public List<CustomerDto> getCustomers();
	
	public List<CustomerDto> getCustomersBySalesRep(String salesRep);

}
