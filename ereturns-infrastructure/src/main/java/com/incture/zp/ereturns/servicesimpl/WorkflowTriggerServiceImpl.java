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

import com.incture.zp.ereturns.constants.EReturnConstants;
import com.incture.zp.ereturns.dto.RequestDto;
import com.incture.zp.ereturns.dto.ResponseDto;
import com.incture.zp.ereturns.services.HciMappingEccService;
import com.incture.zp.ereturns.services.RequestService;
import com.incture.zp.ereturns.services.WorkFlowService;
import com.incture.zp.ereturns.services.WorkflowTriggerService;
import com.incture.zp.ereturns.utils.ImportExportUtil;

@Service
public class WorkflowTriggerServiceImpl implements WorkflowTriggerService {

	@Autowired
	WorkFlowService workFlowService;

	@Autowired
	HciMappingEccService hciMappingService;
	
	@Autowired
	RequestService requestService;

	private static final Logger LOGGER = LoggerFactory.getLogger(ImportExportUtil.class);

	@Override
	public String triggerWorkflow(String payloadData) {
		String responseData = "";
		List<String> cookies = null;
		String csrfToken = "";

		try {
			URL getUrl = new URL(EReturnConstants.GET_XCSRF_TOKEN_ENDPOINT);
			URL postUrl = new URL(EReturnConstants.START_WF_ENDPOINT);

			HttpURLConnection urlConnection = (HttpURLConnection) getUrl.openConnection();

			String authString = EReturnConstants.WF_INITIATOR_USER_NAME + EReturnConstants.COLON
					+ EReturnConstants.WF_INITIATOR_PASSWORD;
			String authStringEnc = new String(Base64.encodeBase64(authString.getBytes()));
			urlConnection.setRequestProperty(EReturnConstants.AUTH, (EReturnConstants.BASIC + authStringEnc));
			urlConnection.setRequestProperty(EReturnConstants.X_CSRF_TOKEN, EReturnConstants.FETCH);
			urlConnection.setRequestMethod(EReturnConstants.GET);

			urlConnection.connect();

			Map<String, List<String>> map = new HashMap<String, List<String>>();
			map = urlConnection.getHeaderFields();
			List<String> str = new ArrayList<String>();
			if (map.containsKey(EReturnConstants.X_CSRF_TOKEN)) {
				str.addAll(map.get(EReturnConstants.X_CSRF_TOKEN));
			}

			if (str.size() > 0) {
				csrfToken = str.get(0);
			}
			cookies = urlConnection.getHeaderFields().get(EReturnConstants.SET_COOKIE);

			HttpURLConnection postUrlConnection = (HttpURLConnection) postUrl.openConnection();

			postUrlConnection.setDoOutput(true);
			postUrlConnection.setDoInput(true);
			postUrlConnection.setUseCaches(false);
			postUrlConnection.setRequestProperty(EReturnConstants.AUTH, (EReturnConstants.BASIC + authStringEnc));
			postUrlConnection.setRequestProperty(EReturnConstants.X_CSRF_TOKEN, csrfToken);
			postUrlConnection.setRequestMethod(EReturnConstants.POST);
			postUrlConnection.setRequestProperty(EReturnConstants.CONTENT_TYPE, EReturnConstants.CONTENT_APPLICATION);
			postUrlConnection.setRequestProperty(EReturnConstants.ACCEPT, EReturnConstants.CONTENT_APPLICATION);
			postUrlConnection.setRequestProperty(EReturnConstants.DATA_SERVICE_VERSION, "2.0");
			postUrlConnection.setRequestProperty(EReturnConstants.X_REQUESTED_WITH,
					EReturnConstants.X_REQUEST_WITH_TYPE);
			postUrlConnection.setRequestProperty(EReturnConstants.ACCEPT_ENCODING,
					EReturnConstants.ACCEPT_ENCODING_TYPE);
			postUrlConnection.setRequestProperty(EReturnConstants.ACCEPT_CHARSET, EReturnConstants.UTF_8);
			postUrlConnection.setUseCaches(false);
			for (String cookie : cookies) {
				String tmp = cookie.split(";", 2)[0];
				postUrlConnection.addRequestProperty(EReturnConstants.COOKIE, tmp);
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
	public ResponseDto completeTask(RequestDto requestDto) {
		ResponseDto responseDto = new ResponseDto();
		ResponseDto requestActionResponse=new ResponseDto();
		String workFlowInstanceId = workFlowService.getWorkFLowInstance(requestDto.getRequestId())
				.getWorkFlowInstanceId();
		String responseData = "";

		LOGGER.error("workflowInstanceId" + workFlowInstanceId);
		try {
			URL getUrl = new URL(EReturnConstants.GET_WORK_FLOW_INSTANCE + workFlowInstanceId);
			HttpURLConnection urlConnection = (HttpURLConnection) getUrl.openConnection();

			String authString = EReturnConstants.WF_INITIATOR_USER_NAME + EReturnConstants.COLON
					+ EReturnConstants.WF_INITIATOR_PASSWORD;
			String authStringEnc = new String(Base64.encodeBase64(authString.getBytes()));
			urlConnection.setRequestProperty(EReturnConstants.AUTH, (EReturnConstants.BASIC + authStringEnc));
			urlConnection.setRequestMethod(EReturnConstants.GET);
			urlConnection.setRequestProperty(EReturnConstants.CONTENT_TYPE, EReturnConstants.CONTENT_APPLICATION);

			urlConnection.connect();
			responseData = getDataFromStream(urlConnection.getInputStream());

			LOGGER.error(responseData);

			JSONArray jsonArray = new JSONArray(responseData);
			JSONObject jsonObject = new JSONObject();
			jsonObject = jsonArray.getJSONObject(0);
			LOGGER.error("taskInstance" + jsonObject.get("id").toString());
			
			requestActionResponse=requestAction(jsonObject.get("id").toString(), requestDto.getRequestStatus());
			if(requestActionResponse.getStatus().equals("SUCCESS"))
			{
				if(requestService.getRequestById(requestDto.getRequestId()).getRequestStatus().equals("APPROVED"))
				{
					hciMappingService.pushDataToEcc(requestService.getRequestById(requestDto.getRequestId()));
				}
			}

			responseDto.setStatus("SUCCESS");

		} catch (MalformedURLException e) {
			responseDto.setCode("1");
			responseDto.setMessage("FAILURE");
			responseDto.setStatus("ERROR");
			return responseDto;
		} catch (IOException e) {
			responseDto.setCode("1");
			responseDto.setMessage("FAILURE");
			responseDto.setStatus("ERROR");
			return responseDto;
		}

		return responseDto;
	}

	public ResponseDto requestAction(String taskInstanceId, String reqStatus) {
		String responseData="";
		List<String> cookies = null;
		String csrfToken = "";
		String payloadData;
		ResponseDto responseDto=new ResponseDto();

		payloadData = buildPayload(reqStatus);
		LOGGER.error(payloadData);
		try {

			URL getUrl = new URL(EReturnConstants.APPROVAL_XCSRF_TOKEN);
			URL postUrl = new URL(EReturnConstants.APPROVAL_URL + taskInstanceId);

			HttpURLConnection urlConnection = (HttpURLConnection) getUrl.openConnection();

			String authString = EReturnConstants.WF_INITIATOR_USER_NAME + EReturnConstants.COLON
					+ EReturnConstants.WF_INITIATOR_PASSWORD;
			String authStringEnc = new String(Base64.encodeBase64(authString.getBytes()));
			urlConnection.setRequestProperty(EReturnConstants.AUTH, (EReturnConstants.BASIC + authStringEnc));
			urlConnection.setRequestProperty(EReturnConstants.X_CSRF_TOKEN, EReturnConstants.FETCH);
			urlConnection.setRequestMethod(EReturnConstants.GET);

			urlConnection.connect();

			Map<String, List<String>> map = new HashMap<String, List<String>>();
			map = urlConnection.getHeaderFields();
			List<String> str = new ArrayList<String>();
			if (map.containsKey(EReturnConstants.X_CSRF_TOKEN)) {
				str.addAll(map.get(EReturnConstants.X_CSRF_TOKEN));
			}

			if (str.size() > 0) {
				csrfToken = str.get(0);
			}
			LOGGER.error(csrfToken);
			cookies = urlConnection.getHeaderFields().get(EReturnConstants.SET_COOKIE);

			allowMethods("PATCH");
			HttpURLConnection postUrlConnection = (HttpURLConnection) postUrl.openConnection();

			postUrlConnection.setDoOutput(true);
			postUrlConnection.setDoInput(true);
			postUrlConnection.setUseCaches(false);
			postUrlConnection.setRequestProperty(EReturnConstants.AUTH, (EReturnConstants.BASIC + authStringEnc));
			postUrlConnection.setRequestProperty(EReturnConstants.X_CSRF_TOKEN, csrfToken);
			postUrlConnection.setRequestMethod(EReturnConstants.PATCH);
			// postUrlConnection.setRequestProperty("X-HTTP-Method-Override",
			// "PATCH");

			postUrlConnection.setRequestProperty(EReturnConstants.CONTENT_TYPE, EReturnConstants.CONTENT_APPLICATION);
			postUrlConnection.setRequestProperty(EReturnConstants.ACCEPT, EReturnConstants.CONTENT_APPLICATION);
			postUrlConnection.setRequestProperty(EReturnConstants.DATA_SERVICE_VERSION, "2.0");
			postUrlConnection.setRequestProperty(EReturnConstants.X_REQUESTED_WITH,
					EReturnConstants.X_REQUEST_WITH_TYPE);
			postUrlConnection.setRequestProperty(EReturnConstants.ACCEPT_ENCODING,
					EReturnConstants.ACCEPT_ENCODING_TYPE);
			postUrlConnection.setRequestProperty(EReturnConstants.ACCEPT_CHARSET, EReturnConstants.UTF_8);
			postUrlConnection.setUseCaches(false);
			for (String cookie : cookies) {
				String tmp = cookie.split(";", 2)[0];
				postUrlConnection.addRequestProperty(EReturnConstants.COOKIE, tmp);
			}

			OutputStream os = postUrlConnection.getOutputStream();
			os.write(payloadData.getBytes());
			os.flush();

			postUrlConnection.connect();

			// System.out.println("Workflow " + 1 + " Response Code :" +
			// postUrlConnection.getResponseCode());

			responseData = getDataFromStream(postUrlConnection.getInputStream());
			responseDto.setStatus("SUCESS");
		} catch (MalformedURLException e) {
			//e.printStackTrace();
			responseDto.setMessage(e.getMessage());
			responseDto.setStatus("ERROR");
			return responseDto;
		} catch (IOException e) {
			//e.printStackTrace();
			responseDto.setMessage(e.getMessage());
			responseDto.setStatus("ERROR");
			return responseDto;
		}
		 return responseDto;

	}

	public String buildPayload(String reqStatus) {
		String payloadData = "";
		if (reqStatus.equals("Approved")) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("status", "completed");
			JSONObject obj = new JSONObject();
			obj.put("Action", "A");
			jsonObj.put("context", obj);

			payloadData = jsonObj.toString();
		} else if (reqStatus.equals("Rejected")) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("status", "completed");
			JSONObject obj = new JSONObject();
			obj.put("Action", "R");
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
}
