package com.incture.zp.ereturns.controller;

import java.io.IOException;

import org.apache.chemistry.opencmis.client.api.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.incture.zp.ereturns.services.AttachmentService;
import com.incture.zp.ereturns.services.EcmDocumentService;

@RestController
@CrossOrigin
@ComponentScan("com.incture.zp.ereturns")
@RequestMapping(value = "/document", produces = "application/json")
public class EReturnsDocumentController {

	@Autowired
	EcmDocumentService ecmDocumentService;
	
	@Autowired
	AttachmentService attachmentService;
	
	@RequestMapping(path = "/download/{id}", method = RequestMethod.GET)
	public ResponseEntity<Resource> download(@PathVariable String id) throws IOException {

		Document document = ecmDocumentService.getAttachmentById(id);
	    InputStreamResource resource = new InputStreamResource(document.getContentStream().getStream());
	    HttpHeaders header = new HttpHeaders();
	    header.set(HttpHeaders.CONTENT_DISPOSITION,
	                   "attachment; filename=" + document.getContentStream().getFileName());
	    header.setContentLength(document.getContentStream().getLength());
	    return ResponseEntity.ok()
	            .headers(header)
	    		.contentType(MediaType.parseMediaType(document.getContentStream().getMimeType()))
	            .body(resource);
	}
	
	@RequestMapping(path = "/downloadLink/{id}", method = RequestMethod.GET)
	public String downloadLink(@PathVariable String id) throws IOException {
		Document document = ecmDocumentService.getAttachmentById(id);
		String link = document.getContentUrl();
		return link;
	}


}
