package com.incture.zp.ereturns.services;

import java.util.List;

import com.incture.zp.ereturns.dto.CreditNoteStatusDto;

public interface CreditNoteService {

	public List<CreditNoteStatusDto> getCreditNoteStatus(String sdNo);
}
