package com.incture.zp.ereturns.servicesimpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;

import com.incture.zp.ereturns.constants.EReturnConstants;
import com.incture.zp.ereturns.services.WorkflowTriggerService;

@Service
public class WorkflowTriggerServiceImpl implements WorkflowTriggerService {

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

			if(str.size() > 0) {
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

			// System.out.println("Workflow " + 1 + " Response Code :" +
			// postUrlConnection.getResponseCode());

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
