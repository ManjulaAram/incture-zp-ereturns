package com.incture.zp.ereturns.constants;

public interface EReturnConstants {

	public static final String BASIC = "Basic";

	public static final String AUTH = "Authorization";
	public static final String POST = "POST";
	public static final String GET = "GET";
	public static final String CONTENT_TYPE = "Content-Type";
	public static final String CONTENT_APPLICATION = "application/scim+json";
	
	public static final String COLON = ":";
	
	public static final String ECM_LOOKUP_NAME = "java:comp/env/EcmService";
	public static final String ECM_UNIQUE_NAME = "Zuelling Pharma E-Returns Repository";
	public static final String ECM_SECRET_KEY = "ZPEReturnsRepo";
	public static final String ECM_CONNECT_FAIL_REASON = "Connect to ECM service failed with reason: ";
	
	public static final String ECM_CMIS_FOLDER = "cmis:folder";
	public static final String ECM_ERETURNS_FOLDER = "E_Returns";
	public static final String ECM_CMIS_DOCUMENT = "cmis:document";

}
