package com.incture.zp.ereturns.servicesimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incture.zp.ereturns.dto.ReturnReasonDto;
import com.incture.zp.ereturns.repositories.ReturnReasonRepository;
import com.incture.zp.ereturns.services.ReturnReasonService;

@Service
@Transactional
public class ReturnReasonServiceImpl implements ReturnReasonService{

	@Autowired
	ReturnReasonRepository returnReasonRepository;
	
	@Override
	public List<ReturnReasonDto> getAllReasons() {
		
		return returnReasonRepository.getAllReasons();
	}

}
