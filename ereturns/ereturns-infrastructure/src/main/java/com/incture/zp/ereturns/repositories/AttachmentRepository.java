package com.incture.zp.ereturns.repositories;

import java.util.Set;

import com.incture.zp.ereturns.dto.AttachmentDto;
import com.incture.zp.ereturns.dto.RequestDto;
import com.incture.zp.ereturns.dto.ResponseDto;
import com.incture.zp.ereturns.model.Attachment;

public interface AttachmentRepository {

	public ResponseDto addAttachment(Attachment attachment);
	
	public Attachment getAttachmentByAttachmentId(String id);
	
	public ResponseDto deleteAttachmentByInvoiceNo(String invoiceNo);
	
	public ResponseDto deleteAttachmentByItemCode(String itemCode);
	
	public ResponseDto deleteAttachmentByAttachmentId(String id);
	
	public Set<AttachmentDto> getAttachmentsById(String id);
	
	public Set<AttachmentDto> getAttachmentURLsByIds(String requestId, String itemId);
	
	public int updateAttachment(RequestDto requestDto);
	
}
