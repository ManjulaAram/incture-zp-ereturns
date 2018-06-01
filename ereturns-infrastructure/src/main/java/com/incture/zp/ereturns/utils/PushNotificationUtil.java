package com.incture.zp.ereturns.utils;

import java.io.IOException;
import java.util.List;

import org.json.JSONObject;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Sender;
import com.incture.zp.ereturns.constants.EReturnConstants;
import com.incture.zp.ereturns.dto.StatusResponseDto;

public class PushNotificationUtil {

	public void sendNotification(String msgTitle, List<String> tokenList, String msgBody) throws IOException {
		String serverkey = EReturnConstants.SERVER_KEY;
		Sender sender = new Sender(serverkey);
		Message message = new Message.Builder().addData("title", msgTitle).addData("body", msgBody)
				.collapseKey("type_a").build();
		sender.send(message, tokenList, 4);

	}

	public void sendNotification(String msgTitle, String token, String msgBody, List<StatusResponseDto> list) throws IOException {
		try {

			String serverKey = EReturnConstants.SERVER_KEY;

			Sender sender = new Sender(serverKey);
			Message message = new Message.Builder().addData("title", msgTitle).addData("body", msgBody).addData("PendingRequestDetails", new JSONObject(list).toString())
					.collapseKey("type_a").build();
			sender.send(message, token, 4);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
