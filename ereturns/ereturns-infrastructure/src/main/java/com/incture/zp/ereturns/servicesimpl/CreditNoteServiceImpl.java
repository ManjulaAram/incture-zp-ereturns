package com.incture.zp.ereturns.servicesimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incture.zp.ereturns.dto.CreditNoteStatusDto;
import com.incture.zp.ereturns.services.CreditNoteService;
import com.incture.zp.ereturns.utils.CreditNoteUtil;

@Service
@Transactional
public class CreditNoteServiceImpl implements CreditNoteService {

	@Autowired
	CreditNoteUtil creditNoteUtil;
	
	@Override
	public List<CreditNoteStatusDto> getCreditNoteStatus(String sdNo) {
		return creditNoteUtil.getCreditNoteStatus(sdNo);
	}

	
}
