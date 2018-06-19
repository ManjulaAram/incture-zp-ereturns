package com.incture.zp.ereturns.servicesimpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incture.zp.ereturns.constants.EReturnConstants;
import com.incture.zp.ereturns.constants.EReturnsWorkflowConstants;
import com.incture.zp.ereturns.dto.CompleteTaskRequestDto;
import com.incture.zp.ereturns.dto.ItemDto;
import com.incture.zp.ereturns.dto.RequestDto;
import com.incture.zp.ereturns.dto.ResponseDto;
import com.incture.zp.ereturns.dto.WorkFlowDto;
import com.incture.zp.ereturns.repositories.RequestRepository;
import com.incture.zp.ereturns.services.HciMappingEccService;
import com.incture.zp.ereturns.services.NotificationService;
import com.incture.zp.ereturns.services.RequestService;
import com.incture.zp.ereturns.services.ReturnOrderService;
import com.incture.zp.ereturns.services.WorkFlowService;
import com.incture.zp.ereturns.services.WorkflowTriggerService;
import com.incture.zp.ereturns.utils.RestInvoker;

@Service
@Transactional
public class WorkflowTriggerServiceImpl implements WorkflowTriggerService {

	@Autowired
	WorkFlowService workFlowService;

	@Autowired
	HciMappingEccService hciMappingService;

	@Autowired
	RequestService requestService;
	
	@Autowired
	NotificationService notificationService;
	
	@Autowired
	RequestRepository requestRepository;

	@Autowired
	ReturnOrderService returnOrderService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WorkflowTriggerServiceImpl.class);
	
	String destination;
	String user;
	String pwd;
	
	public WorkflowTriggerServiceImpl() {
//		DestinationConfiguration destConfiguration = ServiceUtil.getDest(EReturnsWorkflowConstants.WORKFLOW_DESTINATION);
//		destination = destConfiguration.getProperty(EReturnsWorkflowConstants.WORKFLOW_DESTINATION_URL);
//		user = destConfiguration.getProperty(EReturnsWorkflowConstants.WORKFLOW_DESTINATION_USER);
//		pwd = destConfiguration.getProperty(EReturnsWorkflowConstants.WORKFLOW_DESTINATION_PWD);
		
		destination = EReturnsWorkflowConstants.WORKFLOW_DESTINATION_URL;
		user = EReturnsWorkflowConstants.WORKFLOW_DESTINATION_USER;
		pwd = EReturnsWorkflowConstants.WORKFLOW_DESTINATION_PWD;

	}
	
	@Override
	public String triggerWorkflow(String payloadData) {
		String responseData = "";
		List<String> cookies = null;
		String csrfToken = "";

		try {
			URL getUrl = new URL(destination+EReturnsWorkflowConstants.GET_XCSRF_TOKEN_ENDPOINT);
			URL postUrl = new URL(destination+EReturnsWorkflowConstants.START_WF_ENDPOINT);

			HttpURLConnection urlConnection = (HttpURLConnection) getUrl.openConnection();

			String authString = user + EReturnConstants.COLON + pwd;
			String authStringEnc = new String(Base64.encodeBase64(authString.getBytes()));
			urlConnection.setRequestProperty(EReturnConstants.AUTH, (EReturnConstants.BASIC + authStringEnc));
			urlConnection.setRequestProperty(EReturnsWorkflowConstants.X_CSRF_TOKEN, EReturnsWorkflowConstants.FETCH);
			urlConnection.setRequestMethod(EReturnConstants.GET);

			urlConnection.connect();

			Map<String, List<String>> map = new HashMap<String, List<String>>();
			map = urlConnection.getHeaderFields();
			List<String> str = new ArrayList<String>();
			if (map.containsKey(EReturnsWorkflowConstants.X_CSRF_TOKEN)) {
				str.addAll(map.get(EReturnsWorkflowConstants.X_CSRF_TOKEN));
			}

			if (str.size() > 0) {
				csrfToken = str.get(0);
			}
			cookies = urlConnection.getHeaderFields().get(EReturnsWorkflowConstants.SET_COOKIE);

			HttpURLConnection postUrlConnection = (HttpURLConnection) postUrl.openConnection();

			postUrlConnection.setDoOutput(true);
			postUrlConnection.setDoInput(true);
			postUrlConnection.setUseCaches(false);
			postUrlConnection.setRequestProperty(EReturnConstants.AUTH, (EReturnConstants.BASIC + authStringEnc));
			postUrlConnection.setRequestProperty(EReturnsWorkflowConstants.X_CSRF_TOKEN, csrfToken);
			postUrlConnection.setRequestMethod(EReturnConstants.POST);
			postUrlConnection.setRequestProperty(EReturnConstants.CONTENT_TYPE, EReturnConstants.CONTENT_APPLICATION);
			postUrlConnection.setRequestProperty(EReturnsWorkflowConstants.ACCEPT,
					EReturnConstants.CONTENT_APPLICATION);
			postUrlConnection.setRequestProperty(EReturnsWorkflowConstants.DATA_SERVICE_VERSION, "2.0");
			postUrlConnection.setRequestProperty(EReturnsWorkflowConstants.X_REQUESTED_WITH,
					EReturnsWorkflowConstants.X_REQUEST_WITH_TYPE);
			postUrlConnection.setRequestProperty(EReturnsWorkflowConstants.ACCEPT_ENCODING,
					EReturnsWorkflowConstants.ACCEPT_ENCODING_TYPE);
			postUrlConnection.setRequestProperty(EReturnsWorkflowConstants.ACCEPT_CHARSET,
					EReturnsWorkflowConstants.UTF_8);
			postUrlConnection.setUseCaches(false);
			for (String cookie : cookies) {
				String tmp = cookie.split(";", 2)[0];
				postUrlConnection.addRequestProperty(EReturnsWorkflowConstants.COOKIE, tmp);
			}

			OutputStream os = postUrlConnection.getOutputStream();
			os.write(payloadData.getBytes());
			os.flush();

			postUrlConnection.connect();

			responseData = getDataFromStream(postUrlConnection.getInputStream());

		} catch (MalformedURLException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (IOException e) {
			e.printStackTrace();
			return e.getMessage();
		}
		return responseData;

	}

	@Override
	public ResponseDto completeTask(CompleteTaskRequestDto requestDto) {
		ResponseDto responseDto = new ResponseDto();
		boolean eccFlag = false;
		String requestId = requestDto.getRequestId();
		String workFlowInstanceId = workFlowService
				.getWorkFLowInstance(requestId, requestDto.getItemCode()).getWorkFlowInstanceId();
		String responseData = "";

		LOGGER.error("workflowInstanceId" + workFlowInstanceId);
		try {

			synchronized(this) {
				URL getUrl = new URL(destination+EReturnsWorkflowConstants.GET_WORK_FLOW_INSTANCE + workFlowInstanceId);
				HttpURLConnection urlConnection = (HttpURLConnection) getUrl.openConnection();
				
				String authString = user + EReturnConstants.COLON + pwd;
				String authStringEnc = new String(Base64.encodeBase64(authString.getBytes()));
				urlConnection.setRequestProperty(EReturnConstants.AUTH, (EReturnConstants.BASIC + authStringEnc));
				urlConnection.setRequestMethod(EReturnConstants.GET);
				urlConnection.setRequestProperty(EReturnConstants.CONTENT_TYPE, EReturnConstants.CONTENT_APPLICATION);
				
				urlConnection.connect();
				responseData = getDataFromStream(urlConnection.getInputStream());
			}
			
			String instanceId="";
			JSONArray jsonArray = new JSONArray(responseData);
			for (int counter = 0; counter < jsonArray.length(); counter++) {
				JSONObject instanceObject = jsonArray.getJSONObject(counter);
				if(instanceObject.get("status").equals("READY"))
				{
					instanceId=instanceObject.get("id").toString();
				}
			}
			LOGGER.error("taskInstance" + instanceId);
			if(instanceId!=null && instanceId!=""){
				responseDto = requestAction(instanceId, requestDto.getFlag(), requestDto.getLoginUser());
			}
			LOGGER.error(responseDto.getCode()+"taskInstance1" + responseDto.getStatus());
			RequestDto res = requestService.getRequestById(requestDto.getRequestId());
			if (responseDto.getCode().equals("204")) {
				Thread.sleep(5000);
				String status = updateOrderDetails(instanceId);
				if(status.equalsIgnoreCase(EReturnConstants.COMPLETE)) {
					responseDto = hciMappingService.pushDataToEcc(res);
					eccFlag = true;
					LOGGER.error("taskInstance5 coming inside" + responseDto.getMessage());
				}
			}
			if(responseDto != null && eccFlag) {
				if(responseDto.getStatus().equalsIgnoreCase("ECC_SUCCESS")) {
					notificationService.sendNotification(res.getRequestId(), res.getRequestPendingWith(), res.getRequestCreatedBy());
					requestRepository.updateEccReturnOrder(EReturnConstants.COMPLETE, responseDto.getMessage(), requestId);
				} else if(responseDto.getStatus().equalsIgnoreCase("ECC_ERROR")) {
					responseDto.setMessage(responseDto.getMessage());
					
					//Re-triggering the process
					workFlowService.deleteWorkflow(requestId);
					LOGGER.error("Re-Triggering workflow:" + responseDto.getMessage());
					for (ItemDto itemDto : res.getHeaderDto().getItemSet()) {
						String workflowInstanceId = "";
						WorkFlowDto workFlowDto = new WorkFlowDto();

						// start process
						JSONObject jsonObj = new JSONObject();
						jsonObj.put(EReturnsWorkflowConstants.REQUEST_ID, requestId);
						jsonObj.put(EReturnsWorkflowConstants.ITEM_CODE, itemDto.getItemCode());
						jsonObj.put(EReturnsWorkflowConstants.INITIATOR, res.getRequestCreatedBy());
						jsonObj.put(EReturnsWorkflowConstants.INVOICE, res.getHeaderDto().getInvoiceNo());
						jsonObj.put(EReturnsWorkflowConstants.MATERIAL, itemDto.getMaterialDesc());

						JSONObject obj = new JSONObject();
						obj.put(EReturnsWorkflowConstants.CONTEXT, jsonObj);
						obj.put(EReturnsWorkflowConstants.DEFINITION_ID, EReturnsWorkflowConstants.DEFINITION_VALUE);

						String payload = obj.toString();
						String output = triggerWorkflow(payload);
						JSONObject resultJsonObject = new JSONObject(output);
						workflowInstanceId = resultJsonObject.getString(EReturnsWorkflowConstants.WORKFLOW_INSTANCE_ID);

						workFlowDto.setRequestId(requestId);
						workFlowDto.setWorkFlowInstanceId(workflowInstanceId);

						workFlowDto.setMaterialCode(itemDto.getItemCode());
						workFlowDto.setPrincipal(itemDto.getPrincipalCode());
						workFlowDto.setTaskInstanceId("");
						workFlowService.addWorkflowInstance(workFlowDto);
						
						LOGGER.error("Process triggered successfully :" + output);
					}
				}
			} else {
				notificationService.sendNotification(res.getRequestId(), res.getRequestPendingWith(), res.getRequestCreatedBy());
			}
		} catch (MalformedURLException e) {
			responseDto.setCode(EReturnConstants.ERROR_STATUS_CODE);
			responseDto.setMessage("FAILURE" + e.getMessage());
			responseDto.setStatus(EReturnConstants.ERROR_STATUS);
			return responseDto;
		} catch (IOException e) {
			responseDto.setCode(EReturnConstants.ERROR_STATUS_CODE);
			responseDto.setMessage("FAILURE" + e.getMessage());
			responseDto.setStatus(EReturnConstants.ERROR_STATUS);
			return responseDto;
		} catch (InterruptedException e) {
			responseDto.setCode(EReturnConstants.ERROR_STATUS_CODE);
			responseDto.setMessage("FAILURE" + e.getMessage());
			responseDto.setStatus(EReturnConstants.ERROR_STATUS);
		}

		return responseDto;
	}

	public synchronized ResponseDto requestAction(String taskInstanceId, String reqStatus, String loginUser) {
		String responseData = "";
		List<String> cookies = null;
		String csrfToken = "";
		String payloadData;
		ResponseDto responseDto = new ResponseDto();

		String responseCode = "";
		payloadData = buildPayload(reqStatus, loginUser);
		LOGGER.error(payloadData);

		try {

			URL getUrl = new URL(destination+EReturnsWorkflowConstants.APPROVAL_XCSRF_TOKEN);
			URL postUrl = new URL(destination+EReturnsWorkflowConstants.APPROVAL_URL + taskInstanceId);

			HttpURLConnection urlConnection = (HttpURLConnection) getUrl.openConnection();

			String authString = user + EReturnConstants.COLON + pwd;
			String authStringEnc = new String(Base64.encodeBase64(authString.getBytes()));
			urlConnection.setRequestProperty(EReturnConstants.AUTH, (EReturnConstants.BASIC + authStringEnc));
			urlConnection.setRequestProperty(EReturnsWorkflowConstants.X_CSRF_TOKEN, EReturnsWorkflowConstants.FETCH);
			urlConnection.setRequestMethod(EReturnConstants.GET);

			urlConnection.connect();

			Map<String, List<String>> map = new HashMap<String, List<String>>();
			map = urlConnection.getHeaderFields();
			List<String> str = new ArrayList<String>();
			if (map.containsKey(EReturnsWorkflowConstants.X_CSRF_TOKEN)) {
				str.addAll(map.get(EReturnsWorkflowConstants.X_CSRF_TOKEN));
			}

			if (str.size() > 0) {
				csrfToken = str.get(0);
			}
			LOGGER.error(csrfToken);
			cookies = urlConnection.getHeaderFields().get(EReturnsWorkflowConstants.SET_COOKIE);

			allowMethods("PATCH");
			HttpURLConnection postUrlConnection = (HttpURLConnection) postUrl.openConnection();

			postUrlConnection.setDoOutput(true);
			postUrlConnection.setDoInput(true);
			postUrlConnection.setUseCaches(false);
			postUrlConnection.setRequestProperty(EReturnConstants.AUTH, (EReturnConstants.BASIC + authStringEnc));
			postUrlConnection.setRequestProperty(EReturnsWorkflowConstants.X_CSRF_TOKEN, csrfToken);
			postUrlConnection.setRequestMethod(EReturnsWorkflowConstants.PATCH);

			postUrlConnection.setRequestProperty(EReturnConstants.CONTENT_TYPE, EReturnConstants.CONTENT_APPLICATION);
			postUrlConnection.setRequestProperty(EReturnsWorkflowConstants.ACCEPT,
					EReturnConstants.CONTENT_APPLICATION);
			postUrlConnection.setRequestProperty(EReturnsWorkflowConstants.DATA_SERVICE_VERSION, "2.0");
			postUrlConnection.setRequestProperty(EReturnsWorkflowConstants.X_REQUESTED_WITH,
					EReturnsWorkflowConstants.X_REQUEST_WITH_TYPE);
			postUrlConnection.setRequestProperty(EReturnsWorkflowConstants.ACCEPT_ENCODING,
					EReturnsWorkflowConstants.ACCEPT_ENCODING_TYPE);
			postUrlConnection.setRequestProperty(EReturnsWorkflowConstants.ACCEPT_CHARSET,
					EReturnsWorkflowConstants.UTF_8);
			postUrlConnection.setUseCaches(false);
			for (String cookie : cookies) {
				String tmp = cookie.split(";", 2)[0];
				postUrlConnection.addRequestProperty(EReturnsWorkflowConstants.COOKIE, tmp);
			}

			OutputStream os = postUrlConnection.getOutputStream();
			os.write(payloadData.getBytes());
			os.flush();

			postUrlConnection.connect();
			responseData = getDataFromStream(postUrlConnection.getInputStream());
			responseCode = postUrlConnection.getResponseCode() + "";
			LOGGER.error(responseCode+":response data after complete workflow:" + responseData.toString());
			responseDto.setStatus(EReturnConstants.SUCCESS_STATUS);
			responseDto.setCode(responseCode);
		} catch (MalformedURLException e) {
			responseDto.setCode(EReturnConstants.ERROR_STATUS_CODE);
			responseDto.setMessage(e.getMessage());
			responseDto.setStatus(EReturnConstants.ERROR_STATUS);
			return responseDto;
		} catch (IOException e) {
			responseDto.setCode(EReturnConstants.ERROR_STATUS_CODE);
			responseDto.setMessage(e.getMessage());
			responseDto.setStatus(EReturnConstants.ERROR_STATUS);
			return responseDto;
		}
		return responseDto;

	}

	public String buildPayload(String reqStatus, String loginUser) {
		String payloadData = "";
		if (reqStatus.equals("Approved")) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("status", "completed");
			JSONObject obj = new JSONObject();
			obj.put("Action", "A");
			obj.put("loginUser", loginUser);
			jsonObj.put("context", obj);

			payloadData = jsonObj.toString();
		} else if (reqStatus.equals("Rejected")) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("status", "completed");
			JSONObject obj = new JSONObject();
			obj.put("Action", "R");
			obj.put("loginUser", loginUser);
			jsonObj.put("context", obj);

			payloadData = jsonObj.toString();
		}
		return payloadData;
	}

	private static void allowMethods(String... methods) {
		try {
			Field methodsField = HttpURLConnection.class.getDeclaredField("methods");

			Field modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			modifiersField.setInt(methodsField, methodsField.getModifiers() & ~Modifier.FINAL);

			methodsField.setAccessible(true);

			String[] oldMethods = (String[]) methodsField.get(null);
			Set<String> methodsSet = new LinkedHashSet<>(Arrays.asList(oldMethods));
			methodsSet.addAll(Arrays.asList(methods));
			String[] newMethods = methodsSet.toArray(new String[0]);

			methodsField.set(null/* static field */, newMethods);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			throw new IllegalStateException(e);
		}
	}

	private String getDataFromStream(InputStream stream) throws IOException {
		StringBuffer dataBuffer = new StringBuffer();
		BufferedReader inStream = new BufferedReader(new InputStreamReader(stream));
		String data = "";

		while ((data = inStream.readLine()) != null) {
			dataBuffer.append(data);
		}
		inStream.close();

		return dataBuffer.toString();
	}

	private String updateOrderDetails(String taskInstanceId) {
		String url = destination+EReturnsWorkflowConstants.WORKFLOW_REST_API;

		RestInvoker restInvoker = new RestInvoker(url, user, pwd);
		String response = restInvoker.getData("v1/task-instances/" + taskInstanceId + "/context");
		JSONObject updateObject = new JSONObject(response);

		JSONObject updateContent = new JSONObject();
		updateContent = updateObject.getJSONObject(EReturnsWorkflowConstants.UPDATE_CONTENT);
		String status = updateContent.getString(EReturnsWorkflowConstants.STATUS).toString();
		LOGGER.error("Status from context:"+status);
		return status;

	}

}
