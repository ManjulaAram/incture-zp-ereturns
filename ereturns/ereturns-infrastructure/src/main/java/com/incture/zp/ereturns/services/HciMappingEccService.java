package com.incture.zp.ereturns.services;

import com.incture.zp.ereturns.dto.RequestDto;
import com.incture.zp.ereturns.dto.ResponseDto;

public interface HciMappingEccService {

	public ResponseDto pushDataToEcc(RequestDto requestDto, String itemCode, String action);
}
