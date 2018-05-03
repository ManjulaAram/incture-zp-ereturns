package com.incture.zp.ereturns.utils;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Sender;

public class PushNotificationUtil {
	@Autowired
	private Environment environment;

	public void sendNotification(String msgTitle, List<String> tokenList, String msgBody) throws IOException {
		String serverkey = environment.getProperty("er.serverkey");
		Sender sender = new Sender(serverkey);
		Message message = new Message.Builder().addData("title", msgTitle).addData("body", msgBody)
				.collapseKey("type_a").build();
		sender.send(message, tokenList, 4);

	}

	public void sendNotification(String msgTitle, String token, String msgBody) throws IOException {

		String serverkey = environment.getProperty("er.serverkey");
		Sender sender = new Sender(serverkey);
		Message message = new Message.Builder().addData("title", msgTitle).addData("body", msgBody)
				.collapseKey("type_a").build();
		sender.send(message, token, 4);

	}
}

