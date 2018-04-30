package com.incture.zp.ereturns.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_ATTACHMENT")
public class Attachment {

	@Id
	@Column(name = "ATTACHMENT_ID", nullable = false)
	private String attachmentId;
	
	@Column(name = "ATTACHMENT_NAME", length = 255)
	private String attachmentName;
	
	@Column(name = "ITEM_CODE", length = 50)
	private String itemCode;
	
	@Column(name = "INVOICE_NO", length = 50)
	private String invoiceNo;
	
	@Column(name = "REQUEST_ID", length = 100)
	private String requestId;
	
	public String getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(String attachmentId) {
		this.attachmentId = attachmentId;
	}

	public String getAttachmentName() {
		return attachmentName;
	}

	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	
}
