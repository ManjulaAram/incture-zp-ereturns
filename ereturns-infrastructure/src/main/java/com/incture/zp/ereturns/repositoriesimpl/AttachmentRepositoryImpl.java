package com.incture.zp.ereturns.repositoriesimpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.incture.zp.ereturns.constants.EReturnConstants;
import com.incture.zp.ereturns.dto.AttachmentDto;
import com.incture.zp.ereturns.dto.ResponseDto;
import com.incture.zp.ereturns.model.Attachment;
import com.incture.zp.ereturns.repositories.AttachmentRepository;
import com.incture.zp.ereturns.utils.GetReferenceData;
import com.incture.zp.ereturns.utils.ImportExportUtil;

@Repository
public class AttachmentRepositoryImpl implements AttachmentRepository {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	ImportExportUtil importExportUtil;
	
	@Autowired
	GetReferenceData getReferenceData;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AttachmentRepositoryImpl.class);

	@Override
	public Attachment getAttachmentByAttachmentId(String id) {
		return (Attachment) sessionFactory.getCurrentSession().get(Attachment.class, id);	}

	@Override
	public ResponseDto addAttachment(Attachment attachment) {
		ResponseDto responseDto = new ResponseDto();
		String attachmentId = getReferenceData.getNextSeqNumber(getReferenceData.execute("A"), 6, sessionFactory);
		if(attachment.getAttachmentId() == null || attachment.getAttachmentId().equals("")) {
			attachment.setAttachmentId(attachmentId);
		}
		sessionFactory.getCurrentSession().saveOrUpdate(attachment);
		responseDto.setMessage("Attachment "+attachment.getAttachmentId()+" Success");
		responseDto.setStatus(EReturnConstants.SUCCESS_STATUS);
		responseDto.setCode(EReturnConstants.SUCCESS_STATUS_CODE);
		return responseDto;
	}

	@Override
	public ResponseDto deleteAttachmentByInvoiceNo(String invoiceNo) {
		ResponseDto responseDto = new ResponseDto();
		String queryStr = "DELETE Attachment WHERE invoiceNo=:invoiceNo";
		Query query = sessionFactory.getCurrentSession().createQuery(queryStr);
		query.setParameter("invoiceNo", invoiceNo);
		 
		int result = query.executeUpdate();
		if(result > 0) {
			responseDto.setMessage("Deleted Successfully");
			responseDto.setStatus(EReturnConstants.SUCCESS_STATUS);
			responseDto.setCode(EReturnConstants.SUCCESS_STATUS_CODE);
		} else {
			responseDto.setMessage("Delete Unsuccessfully");
			responseDto.setStatus(EReturnConstants.ERROR_STATUS);
			responseDto.setCode(EReturnConstants.ERROR_STATUS_CODE);
		}
		return responseDto;
	}

	@Override
	public ResponseDto deleteAttachmentByItemCode(String itemCode) {
		ResponseDto responseDto = new ResponseDto();
		String queryStr = "DELETE Attachment WHERE itemCode=:itemCode";
		Query query = sessionFactory.getCurrentSession().createQuery(queryStr);
		query.setParameter("itemCode", itemCode);
		 
		int result = query.executeUpdate();
		if(result > 0) {
			responseDto.setMessage("Deleted Successfully");
			responseDto.setStatus(EReturnConstants.SUCCESS_STATUS);
			responseDto.setCode(EReturnConstants.SUCCESS_STATUS_CODE);
		} else {
			responseDto.setMessage("Delete Unsuccessfully");
			responseDto.setStatus(EReturnConstants.ERROR_STATUS);
			responseDto.setCode(EReturnConstants.ERROR_STATUS_CODE);
		}
		return responseDto;
	}

	@Override
	public ResponseDto deleteAttachmentByAttachmentId(String id) {
		ResponseDto responseDto = new ResponseDto();
	    Attachment attachment = (Attachment)sessionFactory.getCurrentSession().load(Attachment.class,id);
	    sessionFactory.getCurrentSession().delete(attachment);
	    responseDto.setMessage("Attachment "+id+" Deleted Successfully");
		responseDto.setStatus(EReturnConstants.SUCCESS_STATUS);
		responseDto.setCode(EReturnConstants.SUCCESS_STATUS_CODE);
		return responseDto;
	}

	@Override
	public Set<AttachmentDto> getAttachmentsById(String id) {
		Set<AttachmentDto> attachmentDtos = new HashSet<>();
		LOGGER.error("Find Attachment Id by RequestId:"+id);
		StringBuilder queryString = new StringBuilder();
		if(id.contains("R")) {
			queryString.append("SELECT a FROM Attachment a WHERE a.requestId =:id");
		} else {
			queryString.append("SELECT a FROM Attachment a WHERE a.itemCode =:id");
		}
		Query query = sessionFactory.getCurrentSession().createQuery(queryString.toString());
		query.setParameter("id", id);
		@SuppressWarnings("unchecked")
		List<Attachment> attachmentList = query.list();
		for(Attachment attachment : attachmentList) {
			AttachmentDto attachmentDto = importExportUtil.exportAttachmentDto(attachment);
			attachmentDtos.add(attachmentDto);
		}
		return attachmentDtos;
	}

	@Override
	public Set<AttachmentDto> getAttachmentURLsByIds(String requestId, String itemId) {
		Set<AttachmentDto> attachmentDtos = new HashSet<>();
		StringBuilder queryString = new StringBuilder();
		queryString.append("SELECT a FROM Attachment a WHERE a.itemCode = "+itemId+" AND a.requestId = "+requestId);

		Query query = sessionFactory.getCurrentSession().createQuery(queryString.toString());
		@SuppressWarnings("unchecked")
		List<Attachment> attachmentList = query.list();
		for(Attachment attachment : attachmentList) {
			AttachmentDto attachmentDto = importExportUtil.exportAttachmentDto(attachment);
			attachmentDtos.add(attachmentDto);
		}
		return attachmentDtos;
	}

}
