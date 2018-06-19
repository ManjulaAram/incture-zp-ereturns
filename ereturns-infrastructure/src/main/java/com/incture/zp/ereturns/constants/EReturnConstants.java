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
	
	public static final String TECHNICAL_ERROR = "Technical Error from ECC";
	
	public static final String SUCCESS_STATUS_CODE = "00";
	public static final String ERROR_STATUS_CODE = "01";
	public static final String SUCCESS_STATUS = "SUCCESS";
	public static final String ERROR_STATUS = "ERROR";
	
	public static final String ECC_SUCCESS_STATUS = "ECC_SUCCESS";
	public static final String ECC_ERROR_STATUS = "ECC_ERROR";

	public static final String ECC_NO_DATA_STATUS = "No Data from ECC";
	public static final String ECC_500_ERROR = "Server returned HTTP response code: 500";
	public static final String ECC_RESPONSE = "rfc:BAPI_CUSTOMERRETURN_CREATE.Response";
	public static final String ECC_EXCEPTION = "rfc:BAPI_CUSTOMERRETURN_CREATE.Exception";
	public static final String ECC_HCI_URL = "/http/ro";
	public static final String ECC_DATE_FORMAT = "yyyyMMdd";
	public static final String ECC_SCHEDULE_LINE = "0001";
	
	public static final String DUPLICATE_CODE = "02";
	public static final String SALES_PERSON = "BOM";
	
	public static final String SCIM_DESTINATION = "ZP_ERETURNS_SCIM";
//	public static final String SCIM_DESTINATION_URL = "URL";
	public static final String SCIM_DESTINATION_URL = "https://auxes3rr8.accounts.ondemand.com/service/scim/Users";
//	public static final String SCIM_DESTINATION_USER = "User";
//	public static final String SCIM_DESTINATION_PWD = "Password";
	
	public static final String SCIM_DESTINATION_USER = "T000004";
	public static final String SCIM_DESTINATION_PWD = "Incture@123";


}
