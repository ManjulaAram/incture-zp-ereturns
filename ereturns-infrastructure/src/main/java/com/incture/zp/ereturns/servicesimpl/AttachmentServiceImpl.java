package com.incture.zp.ereturns.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incture.zp.ereturns.dto.AttachmentDto;
import com.incture.zp.ereturns.dto.ResponseDto;
import com.incture.zp.ereturns.repositories.AttachmentRepository;
import com.incture.zp.ereturns.services.AttachmentService;
import com.incture.zp.ereturns.utils.ImportExportUtil;

@Service
@Transactional
public class AttachmentServiceImpl implements AttachmentService {

	@Autowired
	AttachmentRepository attachmentRepository;

	@Autowired
	ImportExportUtil importExportUtil;

	@Override
	public ResponseDto addAttachment(AttachmentDto attachmentDto) {
		return attachmentRepository.addAttachment(importExportUtil.importAttachmentDto(attachmentDto));
	}

	@Override
	public AttachmentDto getAttachmentByAttachmentId(String id) {
		return importExportUtil.exportAttachmentDto(attachmentRepository.getAttachmentByAttachmentId(id));
	}

	@Override
	public ResponseDto deleteAttachmentByInvoiceNo(String invoiceNo) {
		return attachmentRepository.deleteAttachmentByInvoiceNo(invoiceNo);
	}

	@Override
	public ResponseDto deleteAttachmentByItemCode(String itemCode) {
		return attachmentRepository.deleteAttachmentByItemCode(itemCode);
	}

	@Override
	public ResponseDto deleteAttachmentByAttachmentId(String id) {
		return attachmentRepository.deleteAttachmentByAttachmentId(id);
	}

}
