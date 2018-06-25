package com.incture.zp.ereturns.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.codec.binary.Base64;

import com.incture.zp.ereturns.constants.EReturnConstants;

public class RestInvoker {

	private String baseUrl;
	private String name;
	private String password;
	
	public RestInvoker(String baseUrl, String name, String password)
	{
		this.baseUrl = baseUrl;
		this.name = name;
		this.password = password;
	}
	
	public String getData(String path) {
		StringBuilder data = new StringBuilder();
		try {
				URL url = new URL(baseUrl + path);
				URLConnection urlConnection = setNameAndPassword(url);
				BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
				String line;
				while ((line = reader.readLine()) != null) {
					data.append(line);
				}
				reader.close();
				return data.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private URLConnection setNameAndPassword(URL url) throws IOException {
		URLConnection urlConnection = url.openConnection();
		String authString = name + EReturnConstants.COLON + password;
		String authStringEnc = new String(Base64.encodeBase64(authString.getBytes()));
		urlConnection.setRequestProperty(EReturnConstants.AUTH, EReturnConstants.BASIC + authStringEnc);
		return urlConnection;
	}

	public String postDataToServer(String path, String data) {
		URL url;
		StringBuilder sb = new StringBuilder();
		try {
			url = new URL(baseUrl + path);
			HttpURLConnection urlConnection = (HttpURLConnection) setNameAndPassword(url);
			urlConnection.setDoOutput(true);
			urlConnection.setRequestMethod(EReturnConstants.POST);
			urlConnection.setRequestProperty(EReturnConstants.CONTENT_TYPE, EReturnConstants.CONTENT_APPLICATION);
			OutputStream os = urlConnection.getOutputStream();
			os.write(data.getBytes());
			os.flush();
			BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			reader.close();
		} catch (MalformedURLException e) {
			return e.getMessage();
		} catch (IOException e) {
			return e.getMessage();
		}
		return sb.toString();
	}

}
