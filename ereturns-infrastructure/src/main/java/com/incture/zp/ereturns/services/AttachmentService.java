package com.incture.zp.ereturns.services;

import com.incture.zp.ereturns.dto.AttachmentDto;
import com.incture.zp.ereturns.dto.ResponseDto;

public interface AttachmentService {

	public ResponseDto addAttachment(AttachmentDto attachmentDto);
	
	public AttachmentDto getAttachmentByAttachmentId(String id);
	
	public ResponseDto deleteAttachmentByInvoiceNo(String invoiceNo);
	
	public ResponseDto deleteAttachmentByItemCode(String itemCode);
	
	public ResponseDto deleteAttachmentByAttachmentId(String id);

}
