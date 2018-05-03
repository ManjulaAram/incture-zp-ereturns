package com.incture.zp.ereturns.repositories;

import com.incture.zp.ereturns.dto.ResponseDto;
import com.incture.zp.ereturns.model.Header;

public interface HeaderRepository {

	public Header getHeaderById(String id);
	
	public ResponseDto addHeader(Header header);
	
	public ResponseDto updateHeader(Header header);
	
	public ResponseDto deleteHeader(String id);
	

}
