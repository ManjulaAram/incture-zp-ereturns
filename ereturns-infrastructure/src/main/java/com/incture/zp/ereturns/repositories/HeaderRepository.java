package com.incture.zp.ereturns.repositories;

import com.incture.zp.ereturns.dto.SearchDto;
import com.incture.zp.ereturns.dto.SearchResultDto;
import com.incture.zp.ereturns.model.Header;

public interface HeaderRepository {

	public Header getInvoiceById(String id);
	
	public SearchResultDto getSearchResult(SearchDto searchDto);
}
