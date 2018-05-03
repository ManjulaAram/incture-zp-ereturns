package com.incture.zp.ereturns.services;

import com.incture.zp.ereturns.dto.HeaderDto;
import com.incture.zp.ereturns.dto.ResponseDto;
import com.incture.zp.ereturns.model.Header;

public interface HeaderService {

	public HeaderDto getHeaderById(String id);
	
	public ResponseDto addHeader(Header header);
	
	public ResponseDto updateHeader(Header header);
	
	public ResponseDto deleteHeader(String id);

}
