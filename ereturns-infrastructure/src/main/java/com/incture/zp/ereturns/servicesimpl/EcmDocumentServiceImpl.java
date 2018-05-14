package com.incture.zp.ereturns.servicesimpl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.data.RepositoryInfo;
import org.apache.chemistry.opencmis.commons.enums.VersioningState;
import org.apache.chemistry.opencmis.commons.exceptions.CmisNameConstraintViolationException;
import org.apache.chemistry.opencmis.commons.exceptions.CmisObjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incture.zp.ereturns.constants.EReturnConstants;
import com.incture.zp.ereturns.services.EcmDocumentService;
import com.incture.zp.ereturns.utils.ServiceUtil;
import com.sap.ecm.api.EcmService;
import com.sap.ecm.api.RepositoryOptions;
import com.sap.ecm.api.RepositoryOptions.Visibility;

@Service
@Transactional
public class EcmDocumentServiceImpl implements EcmDocumentService {

	private Session openCmisSession = null;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EcmDocumentServiceImpl.class);

	@Override
	public Document getAttachmentById(String id) {
		if(ServiceUtil.isEmpty(openCmisSession)){
			connectToEcm();
		}
		Document document = (Document) openCmisSession.getObject(id);
		return document;
	}

	@Override
	public String uploadAttachment(byte[] file, String fileName, String fileType) {
		try {
			connectToEcm();
			return createFolderAndDocument(file,fileName,fileType);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private void connectToEcm() {

		// Get a handle to the service by performing a JNDI lookup; EcmService must be a <resource-ref> in the web.xml
		EcmService ecmService = null;
		String ecmServiceLookupName = EReturnConstants.ECM_LOOKUP_NAME;
		try {
			InitialContext ctx = new InitialContext();
			ecmService = (EcmService) ctx.lookup(ecmServiceLookupName);
		} catch (NamingException e) {
			throw new RuntimeException("Lookup of ECM service " + ecmServiceLookupName
					+ " via JNDI failed with reason: " + e.getMessage());
		}

		// Create a key secured repository identified by a unique name and a
		// secret key (minimum length 10 characters)
		String uniqueName = EReturnConstants.ECM_UNIQUE_NAME;
		String secretKey = EReturnConstants.ECM_SECRET_KEY;
		try {
			// Connect to ECM service accessing the repository
			openCmisSession = ecmService.connect(uniqueName, secretKey);

			// Continue if connection was successful
		} catch (CmisObjectNotFoundException e) {
			// If the session couldn't be created the repository is missing so create it now
			RepositoryOptions options = new RepositoryOptions();
			options.setUniqueName(uniqueName);
			options.setRepositoryKey(secretKey);
			options.setVisibility(Visibility.PROTECTED);
			options.setMultiTenantCapable(true);
			ecmService.createRepository(options);

			// Now try again and connect to ECM service accessing the newly created repository
			openCmisSession = ecmService.connect(uniqueName, secretKey);
		} catch (Exception e) {
			throw new RuntimeException(EReturnConstants.ECM_CONNECT_FAIL_REASON + e.getMessage(), e);
		}

		// Print some information about the repository
		RepositoryInfo repositoryInfo = openCmisSession.getRepositoryInfo();
		if (repositoryInfo == null) {
			LOGGER.error("Failed to get repository info!");
		} else {
			LOGGER.error("Product Name: " + repositoryInfo.getProductName());
			LOGGER.error("Repository Id: " + repositoryInfo.getId());
			LOGGER.error("Root Folder Id: " + repositoryInfo.getRootFolderId());
		}
	}

	/**
	 * Create folder and document in root folder in sample repository if not yet done.
	 */
	private String createFolderAndDocument(byte[] file,String fileName,String fileType) throws IOException {
		// If we didn't get a session fail with an appropriate message
		if (openCmisSession == null) {
			return null;
		}

		// Get root folder from CMIS session
		Folder rootFolder = openCmisSession.getRootFolder();

		// Create new folder (requires at least type id and name of the folder)
		Map<String, String> folderProps = new HashMap<String, String>();
		folderProps.put(PropertyIds.OBJECT_TYPE_ID, EReturnConstants.ECM_CMIS_FOLDER);
		folderProps.put(PropertyIds.NAME, EReturnConstants.ECM_ERETURNS_FOLDER);
		try {
			rootFolder.createFolder(folderProps);
			LOGGER.error("A folder with name 'EReturns' was created.");
		} catch (CmisNameConstraintViolationException e) {
			// If the folder already exists, we get a CmisNameConstraintViolationException
			LOGGER.error("A folder with name 'EReturns' already exists, nothing created.");
		}

		// Create new document (requires at least type id and name of the document and some content)
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(PropertyIds.OBJECT_TYPE_ID, EReturnConstants.ECM_CMIS_DOCUMENT);
		properties.put(PropertyIds.NAME, fileName);
		
		try {
			InputStream stream = new ByteArrayInputStream(file);
			ContentStream contentStream = openCmisSession.getObjectFactory().createContentStream(fileName,
					file.length,fileType, stream);
			Document document= rootFolder.createDocument(properties, contentStream, VersioningState.NONE);
			LOGGER.error(document.getId()+"    "+ document.getContentStreamId());
			return document.getId();
		} catch (CmisNameConstraintViolationException e) {
			// If the document already exists, we get a CmisNameConstraintViolationException
			LOGGER.error("A document with name 'HelloWorld.txt' already exists, nothing created."+e.getMessage());
			return null;
		}
	}

}
