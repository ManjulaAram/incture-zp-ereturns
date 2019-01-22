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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incture.zp.ereturns.constants.EReturnConstants;
import com.incture.zp.ereturns.constants.EReturnsHciConstants;
import com.incture.zp.ereturns.constants.EReturnsWorkflowConstants;
import com.incture.zp.ereturns.dto.EmailRequestDto;
import com.incture.zp.ereturns.dto.EmailResponseDto;
import com.incture.zp.ereturns.services.EmailService;
import com.incture.zp.ereturns.utils.EmailServiceUtil;
import com.incture.zp.ereturns.utils.ServiceUtil;
import com.sap.core.connectivity.api.configuration.DestinationConfiguration;

@Service
@Transactional
public class EmailServiceImpl implements EmailService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);
	
	@Autowired
	EmailServiceUtil emailServiceUtil;

	String destination;
	String user;
	String pwd;
	
	public EmailServiceImpl() {
		
		DestinationConfiguration destConfiguration = ServiceUtil.getDest(EReturnsHciConstants.HCI_ECC_ENDPOINT);
		destination = destConfiguration.getProperty(EReturnsHciConstants.HCI_DESTINATION_URL);
		user = destConfiguration.getProperty(EReturnsHciConstants.HCI_DESTINATION_USER);
		pwd = destConfiguration.getProperty(EReturnsHciConstants.HCI_DESTINATION_PWD);
	}
	
	public EmailResponseDto triggerEmail(EmailRequestDto emailRequestDto) {
		EmailResponseDto responseDto = new EmailResponseDto();
		String paylod = "";
		String subject = "";
		if(emailRequestDto.getSflag() != null && !(emailRequestDto.getSflag().equals(""))) {
			if(emailRequestDto.getSflag().equalsIgnoreCase("WF_SEND")) {
				paylod = emailServiceUtil.getMailTemplateApproverSend(emailRequestDto.getRequestId(), emailRequestDto.getMaterial(), emailRequestDto.getInvoice(), emailRequestDto.getCustomerName(), emailRequestDto.getAction());
				subject = emailRequestDto.getSubject();
			} else if(emailRequestDto.getSflag().equalsIgnoreCase("WF_RECEIVE")) {
				paylod = emailServiceUtil.getMailTemplateApproverReceive(emailRequestDto.getRequestId(), emailRequestDto.getMaterial(), emailRequestDto.getInvoice(), emailRequestDto.getCustomerName(), emailRequestDto.getAction());
				subject = emailRequestDto.getSubject();
			}
		} else {
			paylod = emailServiceUtil.getMailTemplate(emailRequestDto.getRequestId(), emailRequestDto.getMaterial(), emailRequestDto.getInvoice(), emailRequestDto.getCustomerName(), emailRequestDto.getAction());
			subject = "E-Returns Request with Request ID "+emailRequestDto.getRequestId()+" has been "+emailRequestDto.getAction();
			LOGGER.error("Created by for Mail to sender:"+subject+"..."+emailRequestDto.getTomailIds());
		}
		String str = sendMail(paylod, emailRequestDto.getTomailIds(), subject);
		responseDto.setMessage(str);
		responseDto.setStatus("SENT");
		return responseDto;
		
	}
	
	@Override
	public String sendMail(String payloadData, String toemailIds, String subject) {
		String responseData = "";
		List<String> cookies = null;
		String csrfToken = "";

		try {
			URL getUrl = new URL(destination+EReturnsHciConstants.EMAIL_HCI_URL);
			URL postUrl = new URL(destination+EReturnsHciConstants.EMAIL_HCI_URL);

			HttpURLConnection urlConnection = (HttpURLConnection) getUrl.openConnection();

			String authString = user + EReturnConstants.COLON + pwd;
			String authStringEnc = new String(Base64.encodeBase64(authString.getBytes()));
			urlConnection.setRequestProperty(EReturnConstants.AUTH, (EReturnConstants.BASIC + authStringEnc));
			urlConnection.setRequestProperty(EReturnsWorkflowConstants.X_CSRF_TOKEN, EReturnsWorkflowConstants.FETCH);
			
			
			urlConnection.setRequestProperty(EReturnConstants.CONTENT_TYPE, EReturnConstants.CONTENT_TEXT_HTML);
			urlConnection.setRequestProperty(EReturnsHciConstants.SENDER, EReturnsHciConstants.EMAIL_SENDER);
			urlConnection.setRequestProperty(EReturnsHciConstants.RECEIVER, EReturnsHciConstants.EMAIL_RECEIVER);
			urlConnection.setRequestProperty(EReturnsHciConstants.SUBJECT, EReturnsHciConstants.SUBJECT);
			urlConnection.setRequestMethod(EReturnConstants.GET);

			urlConnection.connect();

			Map<String, List<String>> map = new HashMap<String, List<String>>();
			map = urlConnection.getHeaderFields();
			
			List<String> str = new ArrayList<String>();
			if (map.containsKey(EReturnsWorkflowConstants.MAIL_X_CSRF_TOKEN)) {
				str.addAll(map.get(EReturnsWorkflowConstants.MAIL_X_CSRF_TOKEN));
//				LOGGER.error("CSRF token for Email inside:"+map.get(EReturnsWorkflowConstants.MAIL_X_CSRF_TOKEN));
			}

			if (str.size() > 0) {
				csrfToken = str.get(0);
			}
//			LOGGER.error("CSRF token for Email:"+csrfToken);
			cookies = urlConnection.getHeaderFields().get(EReturnsWorkflowConstants.SET_COOKIE);
			

			HttpURLConnection postUrlConnection = (HttpURLConnection) postUrl.openConnection();

			postUrlConnection.setDoOutput(true);
			postUrlConnection.setDoInput(true);
			postUrlConnection.setUseCaches(false);
			postUrlConnection.setRequestProperty(EReturnConstants.AUTH, (EReturnConstants.BASIC + authStringEnc));
			postUrlConnection.setRequestProperty(EReturnsWorkflowConstants.X_CSRF_TOKEN, csrfToken);
			postUrlConnection.setRequestMethod(EReturnConstants.POST);
			//
			postUrlConnection.setRequestProperty(EReturnsHciConstants.SENDER, EReturnsHciConstants.EMAIL_SENDER);
			postUrlConnection.setRequestProperty(EReturnsHciConstants.RECEIVER, toemailIds);
			postUrlConnection.setRequestProperty(EReturnsHciConstants.SUBJECT, subject);
			postUrlConnection.setRequestProperty(EReturnConstants.CONTENT_TYPE, EReturnConstants.CONTENT_TEXT_HTML);
			//
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
