package com.incture.zp.ereturns.repositories;

import java.util.List;

import com.incture.zp.ereturns.dto.CustomerDto;

public interface CustomerRepository {

	public List<CustomerDto> getCustomers();
	
	public List<CustomerDto> getCustomersBySalesRep(String salesRep);
}
