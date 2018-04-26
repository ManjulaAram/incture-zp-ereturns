package com.incture.zp.ereturns.repositoriesimpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.incture.zp.ereturns.dto.AttachmentDto;
import com.incture.zp.ereturns.dto.ResponseDto;
import com.incture.zp.ereturns.model.Attachment;
import com.incture.zp.ereturns.repositories.AttachmentRepository;
import com.incture.zp.ereturns.utils.GetReferenceData;
import com.incture.zp.ereturns.utils.ImportExportUtil;
import com.incture.zp.ereturns.utils.SequenceNumberGen;

public class AttachmentRepositoryImpl implements AttachmentRepository {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	ImportExportUtil importExportUtil;

	public String getNextSeqNumber(String referenceCode, int noOfDigits) {
		return SequenceNumberGen.getInstance().getNextSeqNumber(
				referenceCode, noOfDigits, sessionFactory.getCurrentSession());
	}

	@Override
	public Attachment getAttachmentByAttachmentId(String id) {
		return (Attachment) sessionFactory.getCurrentSession().get(Attachment.class, id);	}

	@Override
	public ResponseDto addAttachment(Attachment attachment) {
		ResponseDto responseDto = new ResponseDto();
		String attachmentId = getNextSeqNumber(new GetReferenceData().execute("A"), 6);
		if(attachment.getAttachmentId() == null || attachment.getAttachmentId().equals("")) {
			attachment.setAttachmentId(attachmentId);
			responseDto.setMessage("Attachment "+ attachmentId +" Saved Successfully");
		}
		sessionFactory.getCurrentSession().saveOrUpdate(attachment);
		responseDto.setMessage("Attachment "+attachment.getAttachmentId()+" Updated Successfully");
		responseDto.setStatus("OK");
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
			responseDto.setStatus("OK");
		} else {
			responseDto.setMessage("Delete Unsuccessfully");
			responseDto.setStatus("ERROR");
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
			responseDto.setStatus("OK");
		} else {
			responseDto.setMessage("Delete Unsuccessfully");
			responseDto.setStatus("ERROR");
		}
		return responseDto;
	}

	@Override
	public ResponseDto deleteAttachmentByAttachmentId(String id) {
		ResponseDto responseDto = new ResponseDto();
	    Attachment attachment = (Attachment)sessionFactory.getCurrentSession().load(Attachment.class,id);
	    sessionFactory.getCurrentSession().delete(attachment);
	    responseDto.setMessage("Attachment "+id+" Deleted Successfully");
		responseDto.setStatus("OK");
		return responseDto;
	}

	@Override
	public Set<AttachmentDto> getAttachmentsById(String id) {
		Set<AttachmentDto> attachmentDtos = new HashSet<>();
		StringBuilder queryString = new StringBuilder();
		if(id.contains("R")) {
			queryString.append("SELECT a FROM Attachment a WHERE a.requestId = "+id);
		} else {
			queryString.append("SELECT a FROM Attachment a WHERE a.itemCode = "+id);
		}

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
