package com.incture.zp.ereturns.servicesimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incture.zp.ereturns.dto.ReasonDto;
import com.incture.zp.ereturns.repositories.ReasonRepository;
import com.incture.zp.ereturns.services.ReasonService;

@Service
@Transactional
public class ReasonServiceImpl implements ReasonService{

	@Autowired
	ReasonRepository returnReasonRepository;
	
	@Override
	public List<ReasonDto> getAllReasons() {
		
		return returnReasonRepository.getAllReasons();
	}

}
