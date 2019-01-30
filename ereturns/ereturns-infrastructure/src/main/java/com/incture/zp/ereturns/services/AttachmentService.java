package com.incture.zp.ereturns.services;

import java.util.Set;

import com.incture.zp.ereturns.dto.AttachmentDto;
import com.incture.zp.ereturns.dto.RequestDto;
import com.incture.zp.ereturns.dto.ResponseDto;

public interface AttachmentService {

	public ResponseDto addAttachment(AttachmentDto attachmentDto);
	
	public AttachmentDto getAttachmentByAttachmentId(String id);
	
	public ResponseDto deleteAttachmentByInvoiceNo(String invoiceNo);
	
	public ResponseDto deleteAttachmentByItemCode(String itemCode);
	
	public ResponseDto deleteAttachmentByAttachmentId(String id);

	public Set<AttachmentDto> getAttachmentURLsByIds(String requestId, String itemId);
	
	public int updateAttachment(RequestDto requestDto);
}
