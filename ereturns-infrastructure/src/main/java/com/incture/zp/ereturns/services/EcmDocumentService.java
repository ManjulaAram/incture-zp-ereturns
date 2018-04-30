package com.incture.zp.ereturns.services;

import org.apache.chemistry.opencmis.client.api.Document;

public interface EcmDocumentService {

	Document getAttachmentById(String id);

	String uploadAttachment(byte[] file, String fileName, String fileType);

}
