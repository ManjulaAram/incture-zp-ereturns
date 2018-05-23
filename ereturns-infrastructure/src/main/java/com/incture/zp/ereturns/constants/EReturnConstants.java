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

	public static final String GET_XCSRF_TOKEN_ENDPOINT =  "https://bpmworkflowruntimecbbe88bff-ce7231891.ap1.hana.ondemand.com/workflow-service/rest/v1/xsrf-token";
	public static final String START_WF_ENDPOINT = "https://bpmworkflowruntimecbbe88bff-ce7231891.ap1.hana.ondemand.com/workflow-service/rest/v1/workflow-instances";
	public static final String WF_INITIATOR_USER_NAME="S0019321680";
	public static final String WF_INITIATOR_PASSWORD="0WV1]2{Q";
	public static final String X_CSRF_TOKEN="X-CSRF-Token";
	public static final String ACCEPT="Accept";
	public static final String DATA_SERVICE_VERSION="DataServiceVersion";
	public static final String X_REQUESTED_WITH="X-Requested-With";
	public static final String ACCEPT_ENCODING="Accept-Encoding";
	public static final String ACCEPT_CHARSET="Accept-Charset";
	public static final String UTF_8="UTF-8";
	public static final String ACCEPT_ENCODING_TYPE="gzip, deflate";
	public static final String X_REQUEST_WITH_TYPE="XMLHttpRequest";
	public static final String COOKIE="Cookie";
	public static final String FETCH="Fetch";
	public static final String SET_COOKIE = "Set-Cookie";
	
	public static final String APPROVAL_XCSRF_TOKEN="https://bpmworkflowruntimecbbe88bff-ce7231891.ap1.hana.ondemand.com/workflow-service/rest/v1/xsrf-token";
	public static final String APPROVAL_URL="https://bpmworkflowruntimecbbe88bff-ce7231891.ap1.hana.ondemand.com/workflow-service/rest/v1/task-instances/";
	public static final String GET_WORK_FLOW_INSTANCE="https://bpmworkflowruntimecbbe88bff-ce7231891.ap1.hana.ondemand.com/workflow-service/rest/v1/task-instances?workflowInstanceId=";
	public static final String PATCH="PATCH";
	
	public static final String WORKFLOW_REST_API="https://bpmworkflowruntimecbbe88bff-ce7231891.ap1.hana.ondemand.com/workflow-service/rest/";
	public static final String COMPLETED="COMPLETED";
	public static final String IN_PROGRESS="IN PROGRESS";
}
