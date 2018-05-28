package com.incture.zp.ereturns.constants;

public interface EReturnConstants {

	public static final String BASIC = "Basic ";

	public static final String AUTH = "Authorization";
	public static final String POST = "POST";
	public static final String GET = "GET";
	public static final String CONTENT_TYPE = "Content-Type";
	public static final String CONTENT_APPLICATION = "application/json";
	
	public static final String INPROGRESS = "INPROGRESS";
	public static final String NEW = "NEW";

	public static final String COLON = ":";

	public static final String ECM_LOOKUP_NAME = "java:comp/env/EcmService";
	public static final String ECM_UNIQUE_NAME = "ZP_ERETURNS_REPO";
	public static final String ECM_SECRET_KEY = "ZPEReturnsRepo";
	public static final String ECM_CONNECT_FAIL_REASON = "Connect to ECM service failed with reason: ";

	public static final String ECM_CMIS_FOLDER = "cmis:folder";
	public static final String ECM_ERETURNS_FOLDER = "E_Returns";
	public static final String ECM_CMIS_DOCUMENT = "cmis:document";

	public static final String SERVER_KEY = "AIzaSyDyQzSX_J3W4vuaoiqpnc5MO4AbRqewJSA";

	public static final String COMPLETE = "COMPLETED";
	public static final String REJECT = "REJECTED";
	public static final String DUPLICATE = "DUPLICATE";
	public static final String SUCCESS = "SUCCESS";
}
