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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
import com.incture.zp.ereturns.dto.EmailRequestDto;
import com.incture.zp.ereturns.dto.EmailResponseDto;
import com.incture.zp.ereturns.dto.IdpUserDetailsDto;
import com.incture.zp.ereturns.dto.ItemDto;
import com.incture.zp.ereturns.dto.PriceOverrideDto;
import com.incture.zp.ereturns.dto.RequestDto;
import com.incture.zp.ereturns.dto.RequestHistoryDto;
import com.incture.zp.ereturns.dto.ResponseDto;
import com.incture.zp.ereturns.dto.ReturnOrderDto;
import com.incture.zp.ereturns.dto.UpdateDto;
import com.incture.zp.ereturns.repositories.RequestRepository;
import com.incture.zp.ereturns.repositories.ReturnOrderRepository;
import com.incture.zp.ereturns.services.EmailService;
import com.incture.zp.ereturns.services.HciMappingEccService;
import com.incture.zp.ereturns.services.NotificationService;
import com.incture.zp.ereturns.services.RequestHistoryService;
import com.incture.zp.ereturns.services.RequestService;
import com.incture.zp.ereturns.services.ReturnOrderService;
import com.incture.zp.ereturns.services.UserService;
import com.incture.zp.ereturns.services.WorkFlowService;
import com.incture.zp.ereturns.services.WorkflowTriggerService;
import com.incture.zp.ereturns.utils.RestInvoker;
import com.incture.zp.ereturns.utils.ServiceUtil;
import com.sap.core.connectivity.api.configuration.DestinationConfiguration;

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
	ReturnOrderRepository returnOrderRepository;

	@Autowired
	ReturnOrderService returnOrderService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	RequestHistoryService requestHistoryService;
	
//	@Autowired 
//	EmailServiceUtil emailService;
	
	@Autowired
	EmailService emailService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WorkflowTriggerServiceImpl.class);
	
	String destination;
	String user;
	String pwd;
	
	public WorkflowTriggerServiceImpl() {
		DestinationConfiguration destConfiguration = ServiceUtil.getDest(EReturnsWorkflowConstants.WORKFLOW_DESTINATION);
		destination = destConfiguration.getProperty(EReturnsWorkflowConstants.WORKFLOW_DESTINATION_URL);
		user = destConfiguration.getProperty(EReturnsWorkflowConstants.WORKFLOW_DESTINATION_USER);
		pwd = destConfiguration.getProperty(EReturnsWorkflowConstants.WORKFLOW_DESTINATION_PWD);
		
//		destination = EReturnsWorkflowConstants.WORKFLOW_DESTINATION_URL;
//		user = EReturnsWorkflowConstants.WORKFLOW_DESTINATION_USER;
//		pwd = EReturnsWorkflowConstants.WORKFLOW_DESTINATION_PWD;

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
			LOGGER.error("MalformedError:" + e.getMessage());
			return e.getMessage();
		} catch (IOException e) {
			LOGGER.error("IOError:" + e.getMessage());
			return e.getMessage();
		}
		return responseData;

	}

	@Override
	public ResponseDto completeTask(CompleteTaskRequestDto requestDto) {
		overridePrice(requestDto);
		ResponseDto responseDto = new ResponseDto();
		RequestDto res = requestService.getRequestById(requestDto.getRequestId());
		boolean eccFlag = false;
		boolean notification = false;
		String requestId = requestDto.getRequestId();
		if(res != null) {
			if(res.getEccStatus() != null && res.getRequestStatus() != null 
					&& res.getEccStatus().equalsIgnoreCase("ECC_ERROR") && res.getRequestStatus().equalsIgnoreCase("INPROGRESS")) {
				
				if(requestDto.getFlag().equalsIgnoreCase("Approved")) {
					LOGGER.error("Re-Triggering on failure of ECC");
					// post call to ECC
					responseDto = hciMappingService.pushDataToEcc(res, requestDto.getItemCode(), requestDto.getFlag());
					if(responseDto != null) {
						if(responseDto.getStatus().equalsIgnoreCase(EReturnConstants.ECC_SUCCESS_STATUS)) {
							// adding response dto on completion
							responseDto.setCode(EReturnConstants.SUCCESS_STATUS_CODE);
							responseDto.setStatus(EReturnConstants.ECC_SUCCESS_STATUS);
							responseDto.setMessage(responseDto.getMessage());
							// status update
							reTriggerProcess(res, requestDto.getLoginUser(), responseDto.getMessage(), true, requestDto.getOrderComments(), "", requestDto.getItemCode());
							sendingMailToCustomer(res, requestDto, "Approved");
						} else if(responseDto.getStatus().equalsIgnoreCase(EReturnConstants.ECC_ERROR_STATUS)) {
							// adding response dto on error
							responseDto.setMessage(responseDto.getMessage());
							responseDto.setCode(EReturnConstants.ERROR_STATUS_CODE);
							responseDto.setStatus(EReturnConstants.ECC_ERROR_STATUS);
							//update tables of request, return order and history for re-trigger
							reTriggerProcess(res, requestDto.getLoginUser(), responseDto.getMessage(), false, requestDto.getOrderComments(), "", requestDto.getItemCode());
						}
					} 
				} else if(requestDto.getFlag().equalsIgnoreCase("Rejected")) {
					// adding response dto
					responseDto.setCode(EReturnConstants.WORKFLOW_STATUS_CODE);
					responseDto.setMessage(EReturnConstants.SUCCESS_STATUS);
					responseDto.setStatus(EReturnConstants.SUCCESS_STATUS);
					// change to rejected status
					reTriggerProcess(res, requestDto.getLoginUser(), "", true, requestDto.getOrderComments(), "Rejected", requestDto.getItemCode());
					sendingMailToCustomer(res, requestDto, "Rejected");
				}
				
			} else {
				String workFlowInstanceId = workFlowService
						.getWorkFLowInstance(requestId, requestDto.getItemCode()).getWorkFlowInstanceId();
				String responseData = "";

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
						if(instanceObject.get(EReturnsWorkflowConstants.WORKFLOW_STATUS).equals(EReturnsWorkflowConstants.READY))
						{
							instanceId=instanceObject.get(EReturnConstants.IDP_ID).toString();
						}
					}
					LOGGER.error("Instance Id from workflow:" + instanceId);
					if(instanceId != null && !(instanceId.equals(""))){
						responseDto = requestAction(instanceId, requestDto.getFlag(), requestDto.getLoginUser(), requestDto.getOrderComments());
					}
					if(responseDto.getCode() != null) {
						
						if (responseDto.getCode().equals(EReturnConstants.WORKFLOW_STATUS_CODE)) {
//							res.setPurchaseOrder("ERP");
							Thread.sleep(5000);
							String status = updateOrderDetails(instanceId);
							if(status.equalsIgnoreCase(EReturnConstants.COMPLETE) || status.equalsIgnoreCase(EReturnConstants.REJECT)) {
								synchronized(this) {
									String client = res.getClient();
									if(client != null && !(client.equals("")) && client.equalsIgnoreCase("WEB")) {
										res.setPurchaseOrder("ERC");
									} else {
										res.setPurchaseOrder("ERS");
									}
									// checking for role of override update return order
//									overridePrice(requestDto);
									List<ReturnOrderDto> orderList = returnOrderRepository.getReturnOrderByRequestId(requestId);
//									Set<ReturnOrderDto> set = new HashSet<>(orderList);
//									res.setSetReturnOrderDto(set);
									boolean check = getStatusTrack(orderList, requestDto.getItemCode());
									if(!check) {
										responseDto = hciMappingService.pushDataToEcc(res, requestDto.getItemCode(), requestDto.getFlag());
										eccFlag = true;
									}
								}
							} else {
								notification = true;
//								overridePrice(requestDto);
							}
						}
						if(responseDto != null && eccFlag) {
							if(responseDto.getStatus().equalsIgnoreCase(EReturnConstants.ECC_SUCCESS_STATUS)) {
								notificationService.sendNotificationForRequestor(res.getRequestId(), res.getRequestCreatedBy(), "A");
								requestRepository.updateEccReturnOrder(EReturnConstants.COMPLETE, responseDto.getMessage(), requestId);
								sendingMailToCustomer(res, requestDto, "Approved");
							} else if(responseDto.getStatus().equalsIgnoreCase(EReturnConstants.ECC_ERROR_STATUS)) {
								responseDto.setMessage(responseDto.getMessage());
								//update tables of request, return order and history
								reTriggerProcess(res, requestDto.getLoginUser(), responseDto.getMessage(), false, "", "", requestDto.getItemCode());
							} else if(requestDto.getFlag().equalsIgnoreCase("REJECTED") && responseDto.getStatus().equalsIgnoreCase("SUCCESS")) {
								responseDto.setMessage(EReturnConstants.SUCCESS_STATUS);
								responseDto.setCode(EReturnConstants.WORKFLOW_STATUS_CODE);
								responseDto.setCode(EReturnConstants.SUCCESS_STATUS);
							}
						} 
						if(responseDto.getCode().equals(EReturnConstants.WORKFLOW_STATUS_CODE)) {
							if(requestDto.getFlag() != null) {
								if(requestDto.getFlag().equalsIgnoreCase(EReturnsWorkflowConstants.STATUS_REJECTED)) {
									notificationService.sendNotificationForRequestor(res.getRequestId(), res.getRequestCreatedBy(), EReturnsWorkflowConstants.WORKFLOW_R);
									sendingMailToCustomer(res, requestDto, "Rejected");
								} else if(requestDto.getFlag().equalsIgnoreCase(EReturnsWorkflowConstants.STATUS_APPROVED) && notification) {
									// ZP approver
									notificationService.sendNotificationForApprover(res.getRequestId(), "ZP-Approver");
								}
							}
						}
					} else {
						responseDto.setCode(EReturnConstants.ERROR_STATUS_CODE);
						responseDto.setMessage("No response from workflow.");
						responseDto.setStatus(EReturnConstants.ERROR_STATUS);
					}
				} catch (MalformedURLException e) {
					responseDto.setCode(EReturnConstants.ERROR_STATUS_CODE);
					responseDto.setMessage("FAILURE ON MALFORMED:" + e.getMessage());
					responseDto.setStatus(EReturnConstants.ERROR_STATUS);
					return responseDto;
				} catch (IOException e) {
					responseDto.setCode(EReturnConstants.ERROR_STATUS_CODE);
					responseDto.setMessage("FAILURE ON IO EXCEPTION:" + e.getMessage());
					responseDto.setStatus(EReturnConstants.ERROR_STATUS);
					return responseDto;
				} catch (InterruptedException e) {
					responseDto.setCode(EReturnConstants.ERROR_STATUS_CODE);
					responseDto.setMessage("FAILURE ON INTERRUPTED:" + e.getMessage());
					responseDto.setStatus(EReturnConstants.ERROR_STATUS);
				}
			}
		} else {
			responseDto.setCode(EReturnConstants.ERROR_STATUS_CODE);
			responseDto.setMessage("INVALID REQUEST: "+requestId);
			responseDto.setStatus(EReturnConstants.ERROR_STATUS);
		}
		return responseDto;
	}

	public synchronized ResponseDto requestAction(String taskInstanceId, String reqStatus, String loginUser, String comments) {
		
		List<String> cookies = null;
		String csrfToken = "";
		String payloadData;
		ResponseDto responseDto = new ResponseDto();

		String responseCode = "";
		payloadData = buildPayload(reqStatus, loginUser, comments);
		LOGGER.error("Payload for Approver action:"+payloadData);

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

			allowMethods(EReturnsWorkflowConstants.WORKFLOW_PATCH);
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
			String responseData = getDataFromStream(postUrlConnection.getInputStream());
			responseCode = postUrlConnection.getResponseCode() + "";
			responseDto.setStatus(EReturnConstants.SUCCESS_STATUS);
			responseDto.setCode(responseCode);
			responseDto.setMessage(responseData);
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

	public String buildPayload(String reqStatus, String loginUser, String comments) {
		String payloadData = "";
		if (reqStatus.equals(EReturnsWorkflowConstants.STATUS_APPROVED)) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put(EReturnsWorkflowConstants.WORKFLOW_STATUS, EReturnsWorkflowConstants.WORKFLOW_COMPLETED);
			JSONObject obj = new JSONObject();
			obj.put(EReturnsWorkflowConstants.ACTION, EReturnsWorkflowConstants.WORKFLOW_A);
			obj.put(EReturnsWorkflowConstants.WORKFLOW_LOGIN_USER, loginUser);
			obj.put(EReturnsWorkflowConstants.WORKFLOW_COMMENTS, comments);
			obj.put(EReturnsWorkflowConstants.WORKFLOW_FLAG, EReturnsWorkflowConstants.STATUS_APPROVED);
			jsonObj.put(EReturnsWorkflowConstants.CONTEXT, obj);

			payloadData = jsonObj.toString();
		} else if (reqStatus.equals(EReturnsWorkflowConstants.STATUS_REJECTED)) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put(EReturnsWorkflowConstants.WORKFLOW_STATUS, EReturnsWorkflowConstants.WORKFLOW_COMPLETED);
			JSONObject obj = new JSONObject();
			obj.put(EReturnsWorkflowConstants.ACTION, EReturnsWorkflowConstants.WORKFLOW_R);
			obj.put(EReturnsWorkflowConstants.WORKFLOW_LOGIN_USER, loginUser);
			obj.put(EReturnsWorkflowConstants.WORKFLOW_COMMENTS, comments);
			obj.put(EReturnsWorkflowConstants.WORKFLOW_FLAG, EReturnsWorkflowConstants.STATUS_REJECTED);
			jsonObj.put(EReturnsWorkflowConstants.CONTEXT, obj);

			payloadData = jsonObj.toString();
		}
		return payloadData;
	}

	private static void allowMethods(String... methods) {
		try {
			Field methodsField = HttpURLConnection.class.getDeclaredField(EReturnsWorkflowConstants.WORKFLOW_METHODS);

			Field modifiersField = Field.class.getDeclaredField(EReturnsWorkflowConstants.WORKFLOW_MODIFIERS);
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

	private void reTriggerProcess(RequestDto res, String loginUser, String eccResponse, boolean retry, String comments, String action, String itemCode) {
		//update tables of request, return order and history
		IdpUserDetailsDto pendingWith = userService.getIdpUserDetailsById(loginUser);
		UpdateDto updateDto = new UpdateDto();
		if(retry) {
			updateDto.setApprovedBy(loginUser);
			updateDto.setApprovedDate(getCurrentDate());
			updateDto.setEccNo(eccResponse);
			updateDto.setEccStatus(EReturnConstants.COMPLETE);
			updateDto.setPendingWith("");
			updateDto.setRequestId(res.getRequestId());
			if(action.equalsIgnoreCase("Rejected"))
				updateDto.setStatus(EReturnConstants.REJECTED);
			else
				updateDto.setStatus(EReturnConstants.COMPLETE);
			requestRepository.updateRequestTrigger(updateDto);
			updateDto.setItemCode(itemCode);
			returnOrderRepository.updateReturnOrderTrigger(updateDto);
			// History table insert
			RequestHistoryDto requestHistoryDto = new RequestHistoryDto();
			requestHistoryDto.setCustomer("");
			requestHistoryDto.setInvoiceNo("");
			requestHistoryDto.setMaterial("");
			requestHistoryDto.setItemCode(itemCode);
			requestHistoryDto.setRequestApprovedBy(loginUser);
			requestHistoryDto.setRequestApprovedDate("");
			requestHistoryDto.setRequestCreatedBy(res.getRequestCreatedBy());
			requestHistoryDto.setRequestCreatedDate(res.getRequestCreatedDate());
			requestHistoryDto.setRequestId(res.getRequestId());
			requestHistoryDto.setRequestorComments(comments);
			requestHistoryDto.setRequestPendingWith("");
			requestHistoryDto.setRequestStatus(EReturnConstants.COMPLETE);
			requestHistoryDto.setRequestUpdatedBy("APPLICATION");
			requestHistoryDto.setRequestUpdatedDate(getCurrentDate());
			requestHistoryService.addRequestHistory(requestHistoryDto);
		} else {
			updateDto.setApprovedBy("");
			updateDto.setApprovedDate("");
			updateDto.setEccNo(eccResponse);
			updateDto.setEccStatus(EReturnConstants.ECC_ERROR_STATUS);
			updateDto.setPendingWith(pendingWith.getRole());
			updateDto.setRequestId(res.getRequestId());
			updateDto.setStatus("INPROGRESS");
			requestRepository.updateRequestTrigger(updateDto);
			updateDto.setItemCode(itemCode);
			returnOrderRepository.updateReturnOrderTrigger(updateDto);
			// History table insert
			RequestHistoryDto requestHistoryDto = new RequestHistoryDto();
			requestHistoryDto.setCustomer("");
			requestHistoryDto.setInvoiceNo("");
			requestHistoryDto.setMaterial("");
			requestHistoryDto.setItemCode(itemCode);
			requestHistoryDto.setRequestApprovedBy("");
			requestHistoryDto.setRequestApprovedDate("");
			requestHistoryDto.setRequestCreatedBy(res.getRequestCreatedBy());
			requestHistoryDto.setRequestCreatedDate(res.getRequestCreatedDate());
			requestHistoryDto.setRequestId(res.getRequestId());
			requestHistoryDto.setRequestorComments(comments);
			requestHistoryDto.setRequestPendingWith(pendingWith.getRole());
			requestHistoryDto.setRequestStatus("INPROGRESS");
			requestHistoryDto.setRequestUpdatedBy("APPLICATION");
			requestHistoryDto.setRequestUpdatedDate("");
			requestHistoryService.addRequestHistory(requestHistoryDto);
		}
	}
	
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private String getCurrentDate() {
		String current = dateFormat.format(new Date());
		return current;
	}

	@Override
	public EmailResponseDto sendEmail(EmailRequestDto emailRequestDto) {
		return emailService.triggerEmail(emailRequestDto);
	}
	
	public void sendingMailToCustomer(RequestDto res, CompleteTaskRequestDto requestDto, String action) {
		IdpUserDetailsDto user = userService.getIdpUserDetailsById(res.getRequestCreatedBy());
		if(user != null && user.getEmail() != null && !(user.getEmail().equals(""))) {
			LOGGER.error("Created by for Mail:"+user.getEmail()+"...."+user.getRole()+"..."+user.getUserName());
			List<String> email = new ArrayList<>();
			
			EmailRequestDto emailRequestDto = new EmailRequestDto();
			if(user.getUserName() != null && !(user.getUserName().equals(""))) {
				emailRequestDto.setCustomerName(user.getUserName());
			} else {
				emailRequestDto.setCustomerName("Customer");
			}
//			email.add(user.getEmail());
			emailRequestDto.setEmailIds(email);
			emailRequestDto.setInvoice(res.getHeaderDto().getInvoiceNo());
			for(ItemDto	itemDto : res.getHeaderDto().getItemSet()) {
				if(itemDto.getItemCode().equalsIgnoreCase(requestDto.getItemCode())) {
					emailRequestDto.setMaterial(itemDto.getMaterialDesc());
				}
			}
			emailRequestDto.setRequestId(res.getRequestId());
			emailRequestDto.setAction(action);
			emailRequestDto.setTomailIds(user.getEmail());
			emailRequestDto.setSflag("");
			emailRequestDto.setSubject("");
			
			emailService.triggerEmail(emailRequestDto);
		}
	}
	
	public boolean getStatusTrack(List<ReturnOrderDto> orderList, String itemCode) {
		boolean flag = false;
		for(int i = 0 ; i < orderList.size() ; i++) {
			ReturnOrderDto returnOrderDto = orderList.get(i);
			if(returnOrderDto.getOrderStatus() != null && !(returnOrderDto.getOrderStatus().equals(""))) {
				if(!(itemCode.equalsIgnoreCase(returnOrderDto.getItemCode()))) {
					LOGGER.error("Check all itemcode status: "+returnOrderDto.getOrderStatus());
					if(returnOrderDto.getOrderStatus().equalsIgnoreCase("INPROGRESS")) {
						flag = true;
						break;
					}
				} 
			}
		}
		return flag;
		
	}
	
	public int overridePrice(CompleteTaskRequestDto requestDto) {
		int i = 0;
		if(requestDto.getOverrideRole() != null && !(requestDto.getOverrideRole().equals("")) &&
				requestDto.getOverrideRole().equalsIgnoreCase("PRINCIPAL_OVERRIDE")) {
			if(requestDto.getOverridePrice() != null && !(requestDto.getOverridePrice().equals(""))) {
				PriceOverrideDto priceOverrideDto = new PriceOverrideDto();
				priceOverrideDto.setOverridePrice(requestDto.getOverridePrice());
				priceOverrideDto.setItemCode(requestDto.getItemCode());
				priceOverrideDto.setRequestId(requestDto.getRequestId());
				i = returnOrderRepository.updatePriceOverride(priceOverrideDto);
			}
		}
		return i;
	}
	
}
