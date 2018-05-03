package com.incture.zp.ereturns.repositoriesimpl;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.incture.zp.ereturns.dto.ResponseDto;
import com.incture.zp.ereturns.model.Header;
import com.incture.zp.ereturns.repositories.HeaderRepository;
import com.incture.zp.ereturns.utils.GetReferenceData;
import com.incture.zp.ereturns.utils.ImportExportUtil;

@Repository
public class HeaderRepositoryImpl implements HeaderRepository {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	ImportExportUtil importExportUtil;
	
	@Autowired
	GetReferenceData getReferenceData;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HeaderRepositoryImpl.class);
	
	@Override
	public Header getHeaderById(String id) {
		return (Header) sessionFactory.getCurrentSession().get(Header.class, id);
	}

	@Override
	public ResponseDto addHeader(Header header) {
		ResponseDto responseDto = new ResponseDto();
		String headerId = getReferenceData.getNextSeqNumber(getReferenceData.execute("H"), 6, sessionFactory);
		if(header.getHeaderId() == null || header.getHeaderId().equals("")) {
			header.setHeaderId(headerId);
			responseDto.setMessage("Header "+ headerId +" Saved Successfully");
		}
		sessionFactory.getCurrentSession().saveOrUpdate(header);
		responseDto.setMessage("Header "+header.getHeaderId()+" Updated Successfully");
		responseDto.setStatus("SUCCESS");
		responseDto.setCode("00");
		LOGGER.error("");
		return responseDto;
	}

	@Override
	public ResponseDto updateHeader(Header header) {
		return null;
	}

	@Override
	public ResponseDto deleteHeader(String id) {
		return null;
	}
	
}
