package com.incture.zp.ereturns.repositoriesimpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.incture.zp.ereturns.dto.SearchDto;
import com.incture.zp.ereturns.dto.SearchResultDto;
import com.incture.zp.ereturns.model.Header;
import com.incture.zp.ereturns.model.Item;
import com.incture.zp.ereturns.model.User;
import com.incture.zp.ereturns.repositories.HeaderRepository;
import com.incture.zp.ereturns.utils.ImportExportUtil;

@Repository
public class HeaderRepositoryImpl implements HeaderRepository {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	ImportExportUtil importExportUtil;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HeaderRepositoryImpl.class);
	
	@Override
	public Header getInvoiceById(String id) {
		return (Header) sessionFactory.getCurrentSession().get(Header.class, id);
	}

	@Override
	public List<SearchResultDto> getSearchResult(SearchDto searchDto) {
		String constraint = null;
		List<SearchResultDto> searchResultDtos = new ArrayList<>();
		SearchResultDto searchResultDto = new SearchResultDto();
		
		StringBuilder queryString = new StringBuilder();
		
		queryString.append("SELECT u, h, i FROM User u, Header h, Item i "
				+ "WHERE u.userId = h.headerData.userId AND h.invoiceNo = i.itemData.invoiceNo ");
		
		if(searchDto.getCustomerCode() != null && !(searchDto.getCustomerCode().equals(""))) {
			queryString.append(" AND u.userCode=:userCode");
			constraint = "userCode";
		}
		
		if(searchDto.getCustomerName() != null && !(searchDto.getCustomerName().equals(""))) {
			queryString.append(" AND u.userName=:userName");
			constraint = "userName";
		}
		
		if(searchDto.getInvoiceNo() != null && !(searchDto.getInvoiceNo().equals(""))) {
			queryString.append(" AND h.invoiceNo=:invoiceNo");
			constraint = "invoiceNo";
		}
		
		if(searchDto.getSalesOrder() != null && !(searchDto.getSalesOrder().equals(""))) {
			queryString.append(" AND h.salesOrder=:salesOrder");
			constraint = "salesOrder";
		}
		
		Query query = sessionFactory.getCurrentSession().createQuery(queryString.toString());
		LOGGER.error("Query String1:"+ query.getQueryString());
		
		if(searchDto.getCustomerCode() != null && !(searchDto.getCustomerCode().equals(""))) {
			query.setParameter("userCode", searchDto.getCustomerCode());
		}
		
		if(searchDto.getCustomerName() != null && !(searchDto.getCustomerName().equals(""))) {
			query.setParameter("userName", searchDto.getCustomerName());
		}
		
		if(searchDto.getInvoiceNo() != null && !(searchDto.getInvoiceNo().equals(""))) {
			query.setParameter("invoiceNo", searchDto.getInvoiceNo());
		}
		
		if(searchDto.getSalesOrder() != null && !(searchDto.getSalesOrder().equals(""))) {
			query.setParameter("salesOrder", searchDto.getSalesOrder());
		}
		
		User user = null;
		Header header = null;
		Item item = null;
		Set<Header> setHeader = new HashSet<>();
		Set<Item> setItem = new HashSet<>();
		@SuppressWarnings("unchecked")
		List<Object[]> objectsList = query.list();
			for (Object[] objects : objectsList) {
			user = (User) objects[0];
			header = (Header) objects[1];
			item = (Item) objects[2];
			
			if(constraint.equalsIgnoreCase("userCode") || constraint.equalsIgnoreCase("userName")) {
				searchResultDto.setUserDto(importExportUtil.exportUserDto(user));
			} else {
				setItem.add(item);
				header.setSetItem(setItem);
				setHeader.add(header);
				user.setSetHeader(setHeader);
				searchResultDto.setUserDto(importExportUtil.exportUserDto(user));
			}
		}
		searchResultDtos.add(searchResultDto);
		
		return searchResultDtos;
	}
	
}
