package com.incture.zp.ereturns.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.incture.zp.ereturns.dto.AttachmentDto;
import com.incture.zp.ereturns.dto.ResponseDto;
import com.incture.zp.ereturns.services.AttachmentService;
import com.incture.zp.ereturns.services.EcmDocumentService;
import com.incture.zp.ereturns.services.UserService;

@RestController
@CrossOrigin
@ComponentScan("com.incture.zp.ereturns")
@RequestMapping(value = "/document", produces = "application/json")
public class EReturnsDocumentController {

	@Autowired
	EcmDocumentService ecmDocumentService;
	
	@Autowired
	AttachmentService attachmentService;
	
	@Autowired
	UserService userService;
	
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
	
	@RequestMapping(path = "/upload", method = RequestMethod.POST)
	public ResponseDto upload(@RequestParam("name") String[] names,
			@RequestParam("file") MultipartFile[] files, @RequestParam("type") String[] fileType, 
			@RequestParam("requestId") String requestId, @RequestParam("invoiceNo") String invoiceNo, 
			@RequestParam("itemCode") String itemCode) throws IOException {

		ResponseDto responseDto = new ResponseDto();
		if (files.length != names.length) {
			responseDto.setCode("01");
			responseDto.setMessage("Mandatory information missing");
			responseDto.setStatus("ERROR");	
		}
		AttachmentDto attachmentDto = null;
		List<String> docIds = new ArrayList<>();
		for (int i = 0; i < files.length; i++) {
			MultipartFile file = files[i];
			String name = names[i];
			String type = fileType[i];
			try {
				byte[] bytes = file.getBytes();
				String docName = ecmDocumentService.uploadAttachment(bytes, name, type);
				attachmentDto = new AttachmentDto();
				attachmentDto.setAttachmentId("");
				attachmentDto.setAttachmentName(docName);
				attachmentDto.setAttachmentType(type);
				attachmentDto.setContent("");
				attachmentDto.setInvoiceNo(invoiceNo);
				attachmentDto.setItemCode(itemCode);
				attachmentDto.setRequestId(requestId);
				
				responseDto = attachmentService.addAttachment(attachmentDto);
				responseDto.setCode("00");
				responseDto.setMessage(responseDto.getMessage());
				responseDto.setStatus("SUCCESS");
				docIds.add(responseDto.getMessage());
				
			} catch (Exception e) {
				responseDto.setCode("01");
				responseDto.setMessage("You failed to upload " + name + " => " + e.getMessage());
				responseDto.setStatus("ERROR");
			}
		}
		responseDto.setDocIds(docIds);
		return responseDto;
	}
	
	@RequestMapping(path = "/uploadUserExcel", method = RequestMethod.POST)
	public ResponseDto uploadUserExcel(@RequestParam("file") MultipartFile file) throws IOException {

		ResponseDto responseDto = new ResponseDto();
		
			try {
				InputStream in = file.getInputStream();
			    File currDir = new File(".");
			    String path = currDir.getAbsolutePath();
			    String fileLocation = path.substring(0, path.length() - 1) + file.getOriginalFilename();
			    FileOutputStream f = new FileOutputStream(fileLocation);
			    int ch = 0;
			    while ((ch = in.read()) != -1) {
			        f.write(ch);
			    }
			    f.flush();
			    f.close();
				userService.createUserFromExcel(fileLocation);
				responseDto.setCode("00");
				responseDto.setMessage("You successfully uploaded file=" + file.getOriginalFilename());
				responseDto.setStatus("SUCCESS");
				
			} catch (Exception e) {
				responseDto.setCode("01");
				responseDto.setMessage("You failed to upload " + file.getOriginalFilename() + " => " + e.getMessage());
				responseDto.setStatus("ERROR");
			}
		return responseDto;
	}

	@RequestMapping(path = "/uploadGroupExcel", method = RequestMethod.POST)
	public ResponseDto uploadGroupExcel(@RequestParam("file") MultipartFile file) throws IOException {

		ResponseDto responseDto = new ResponseDto();
		
			try {
				InputStream in = file.getInputStream();
			    File currDir = new File(".");
			    String path = currDir.getAbsolutePath();
			    String fileLocation = path.substring(0, path.length() - 1) + file.getOriginalFilename();
			    FileOutputStream f = new FileOutputStream(fileLocation);
			    int ch = 0;
			    while ((ch = in.read()) != -1) {
			        f.write(ch);
			    }
			    f.flush();
			    f.close();
				userService.createGroupFromExcel(fileLocation);
				responseDto.setCode("00");
				responseDto.setMessage("You successfully uploaded file=" + file.getOriginalFilename());
				responseDto.setStatus("SUCCESS");
				
			} catch (Exception e) {
				responseDto.setCode("01");
				responseDto.setMessage("You failed to upload " + file.getOriginalFilename() + " => " + e.getMessage());
				responseDto.setStatus("ERROR");
			}
		return responseDto;
	}


}
