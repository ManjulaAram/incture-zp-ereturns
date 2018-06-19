package com.incture.zp.ereturns.constants;

public class EReturnsWorkflowConstants {

	public static final String GET_XCSRF_TOKEN_ENDPOINT =  "/rest/v1/xsrf-token";
	public static final String START_WF_ENDPOINT = "/rest/v1/workflow-instances";
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
	public static final String WORKFLOW_DESTINATION = "ZP_ERETURNS_WORKFLOW";
//	public static final String WORKFLOW_DESTINATION_URL = "URL";
	public static final String WORKFLOW_DESTINATION_URL = "https://bpmworkflowruntimecbbe88bff-ce7231891.ap1.hana.ondemand.com/workflow-service";
//	public static final String WORKFLOW_DESTINATION_USER = "User";
//	public static final String WORKFLOW_DESTINATION_PWD = "Password";
	
	public static final String WORKFLOW_DESTINATION_USER = "S0019321680";
	public static final String WORKFLOW_DESTINATION_PWD = "0WV1]2{Q";
	
	public static final String APPROVAL_XCSRF_TOKEN="/rest/v1/xsrf-token";
	public static final String APPROVAL_URL="/rest/v1/task-instances/";
	public static final String GET_WORK_FLOW_INSTANCE="/rest/v1/task-instances?workflowInstanceId=";
	public static final String PATCH="PATCH";
	
	public static final String REQUEST_ID = "requestId";
	public static final String CONTEXT = "context";
	public static final String DEFINITION_ID = "definitionId";
	public static final String DEFINITION_VALUE = "Ereturns";
	public static final String ITEM_CODE = "Itemcode";
	public static final String INITIATOR ="Initiator";
	public static final String INVOICE ="invoice";
	public static final String MATERIAL ="material";
	
	public static final String WORKFLOW_INSTANCE_ID = "id";
	public static final String WORKFLOW_REST_API="/rest/";
	public static final String COMPLETED="COMPLETED";
	public static final String IN_PROGRESS="INPROGRESS";
	
	public static final String UPDATE_CONTENT = "UpdateContent";
	public static final String STATUS = "Status";
	public static final String READY = "READY";
	public static final String WORKFLOW_STATUS = "status";

	public static final String STATUS_APPROVED = "Approved";
	public static final String STATUS_REJECTED = "Rejected";
	
	public static final String ACTION = "Action";
	public static final String TIMESTAMP = "timestamp";
	public static final String TYPE = "type";
	public static final String TASK_ID = "taskId";
	public static final String USER_ID = "userId";
	

	public static final String WORKFLOW_MODIFIERS = "modifiers";
	public static final String WORKFLOW_METHODS = "methods";
	
	public static final String WORKFLOW_COMPLETED = "completed";
	public static final String WORKFLOW_LOGIN_USER = "loginUser";
	public static final String WORKFLOW_A = "A";
	public static final String WORKFLOW_R = "R";
	public static final String WORKFLOW_PATCH = "PATCH";

}
