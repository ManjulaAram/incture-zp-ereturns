package com.incture.zp.ereturns.services;

import java.util.List;

import com.incture.zp.ereturns.dto.HeaderDto;
import com.incture.zp.ereturns.dto.SearchDto;
import com.incture.zp.ereturns.dto.SearchResultDto;

public interface HeaderService {

	public HeaderDto getHeaderById(String id);
	
	public List<SearchResultDto> getSearchResult(SearchDto searchDto);
}
