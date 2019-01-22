package com.incture.zp.ereturns.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.incture.zp.ereturns.dto.EmailRequestDto;
import com.incture.zp.ereturns.dto.EmailResponseDto;

@Component
public class EmailServiceUtil {

	private Properties prop = null;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceUtil.class);
	
	public EmailResponseDto sendEmail(EmailRequestDto requestDto) {
		
		EmailResponseDto emailResponseDto = new EmailResponseDto();
		
		int actualSize = 0;
		List<String> actualIds = new ArrayList<String>();
		List<String> ids = requestDto.getEmailIds();
		int size = ids.size();
		if(!ServiceUtil.isEmpty(ids)) {
			for(int j = 0 ; j < size ; j++) {
				if(ServiceUtil.isValidEmailAddress(ids.get(j))) {
					actualSize = actualSize + 1;
					actualIds.add(ids.get(j));
				}
			}
		}
		
		if(actualSize > 0) {
			try {

				final String fromEmail = getPropertyValue("mail.smtp.user");
				final String password = getPropertyValue("mail.smtp.password");
				
				Authenticator auth = new Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(fromEmail, password);
					}
				};
				
				Session session = Session.getDefaultInstance(prop, auth);
				session.setDebug(true);
				
				MimeMessage message = new MimeMessage(session);
				
				InternetAddress from = new InternetAddress();
				from.setAddress(fromEmail);
				
				message.setFrom(from);
				
				String to[] = new String[actualSize];
				actualIds.toArray(to);
				InternetAddress[] toAddress = new InternetAddress[to.length];

				for (int i = 0; i < to.length; i++) {
					toAddress[i] = new InternetAddress(to[i]);
					System.out.println("valid id's:"+toAddress[i]);
				}

				message.setRecipients(Message.RecipientType.TO, toAddress);
				String subject = "E-Returns Request with Request ID "+requestDto.getRequestId()+" has been "+requestDto.getAction();
				message.setSubject(subject);

				BodyPart messageBodyPart = new MimeBodyPart();
				String invoice = "";
				if(requestDto.getInvoice() != null && !(requestDto.getInvoice().equals(""))) {
					invoice = requestDto.getInvoice();
				} else {
					invoice = "NA";
				}
				String htmlContent = getMailTemplate(requestDto.getRequestId(), requestDto.getMaterial(), invoice, requestDto.getCustomerName(), requestDto.getAction());

				messageBodyPart.setContent(htmlContent, "text/html");

				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(messageBodyPart);

				message.setContent(multipart);
				Transport.send(message);
				LOGGER.error("Email sent");
				emailResponseDto.setMessage("Email successful");
				emailResponseDto.setStatus("SUCCESS");

			} catch (MessagingException e) {
				emailResponseDto.setMessage("Email unsuccessful - "+e.getMessage());
				emailResponseDto.setStatus("ERROR");
				e.printStackTrace();
			}

		} else {
			emailResponseDto.setMessage("Invalid Email ID's found");
			emailResponseDto.setStatus("ERROR");
		}
		
		return emailResponseDto;
	}

	
	public EmailServiceUtil() {
		InputStream is = null;
		try {
			this.prop = new Properties();
			is = this.getClass().getResourceAsStream(
					"/mailproperties.properties");
			prop.load(is);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getPropertyValue(String key) {
		return this.prop.getProperty(key);
	}

	public String getMailTemplate(String requestId, String material, String invoice, String customerName, String action) {
		
		StringBuilder mailTemplate = new StringBuilder(); 
		mailTemplate.append("<!DOCTYPE html>")
		     .append("<html>")
		     .append("<head>")
		     .append("	<title>Approve</title>")
		     .append("	<link href=\"https://fonts.googleapis.com/css?family=PT+Serif\" rel=\"stylesheet\">")
		     .append("	<style>")
		     .append("	body{")
		     .append("		width: auto;")
		     .append("		height: auto;")
		     .append("		display: block;")
		     .append("		margin: 0 auto;")
		     .append("		padding: 25px;")
		     .append("		font-family: 'PT Serif', serif;")
		     .append("	}")
		     .append("	.bar {")
		     .append("		width: 100%;")
		     .append("		height: 15px;")
		     .append("		background: #026894;")
		     .append("	}")
		     .append("	</style>")
		     .append("</head>")
		     .append("<body>")
		     .append("<img src=\"http://6erxg60qvo1qvjha44jrgpan.wpengine.netdna-cdn.com/wp-content/uploads/2015/02/Zuellig-Pharma-logo.jpg\" ")
		     .append("					width=\"250\" height=\"55\"/>")
		     .append("<img align=\"right\" src=\"https://image.ibb.co/cO37cy/e_returns.png\" width=\"150\" height=\"55\"/>")
		     .append("<div class=\"bar\">")
		     .append("</div>")
		     .append("<div>")
		     .append("	<h2> Dear "+customerName+", </h2>")
		     .append("	<p> Your E-Returns Request with Request ID <i><b>"+requestId+"</b></i> for Invoice <i><b>"+invoice+"</b></i> and Material <i><b>"+material+"</b></i> has been "+action+".</p>")
		     .append("")
		     .append("	<b style=\"color:#00415C;\">Regards,</b><br/>")
		     .append("	<b style=\"color:#00415C;\">E-Returns Team</b>")
		     .append("</div>")
		     .append("<p><i style=\"color:grey;\">Note: This is auto generated email please do not reply.</i></p>")
		     .append("</body>")
		     .append("</html>");

		return mailTemplate.toString();
	}
	
	public String getMailTemplateApproverSend(String requestId, String material, String invoice, String customerName, String action) {
			

		StringBuilder mailTemplate = new StringBuilder(); 
		mailTemplate.append("<!DOCTYPE html>")
		     .append("<html>")
		     .append("<head>")
		     .append("	<title>Request</title>")
		     .append("	<link href=\"https://fonts.googleapis.com/css?family=PT+Serif\" rel=\"stylesheet\">")
		     .append("	<style>")
		     .append("	body{")
		     .append("		width: auto;")
		     .append("		height: auto;")
		     .append("		display: block;")
		     .append("		margin: 0 auto;")
		     .append("		padding: 25px;")
		     .append("		font-family: 'PT Serif', serif;")
		     .append("	}")
		     .append("	.bar {")
		     .append("		width: 100%;")
		     .append("		height: 15px;")
		     .append("		background: #026894;")
		     .append("	}")
		     .append("	</style>")
		     .append("</head>")
		     .append("<body>")
		     .append("<img src=\"http://6erxg60qvo1qvjha44jrgpan.wpengine.netdna-cdn.com/wp-content/uploads/2015/02/Zuellig-Pharma-logo.jpg\" ")
		     .append("					width=\"250\" height=\"55\"/>")
		     .append("<img align=\"right\" src=\"https://image.ibb.co/cO37cy/e_returns.png\" width=\"150\" height=\"55\"/>")
		     .append("<div class=\"bar\">")
		     .append("</div>")
		     .append("<div>")
		     .append("	<h2> Dear "+customerName+", </h2>")
		     .append("	<p> E-Returns Request with Reference ID <i><b>"+requestId+"</b></i> is waiting for your Approval for Invoice <i><b>"+invoice+"</b></i> and ")
		     .append("	Material <i><b>"+material+"</b></i> .")
		     .append("	Please check your <a href='https://ereturn-c8e00d73c.dispatcher.ap1.hana.ondemand.com/index.html'> Inbox </a> or Mobile App for more information. </p>")
//		     .append("	For IOS check <a href='#zpapp://'> this </a>. </p>")
//		     .append("  <script> function test() {  window.open('zpapp://'); } </script> ")
		     .append("")
		     .append("	<b style=\"color:#00415C;\">Regards,</b><br/>")
		     .append("	<b style=\"color:#00415C;\">Workflow Team</b>")
		     .append("</div>")
		     .append("<p><i style=\"color:grey;\">Note: This is auto generated email please do not reply.</i></p>")
		     .append("</body>")
		     .append("</html>");

			return mailTemplate.toString();
		}

	public String getMailTemplateApproverReceive(String requestId, String material, String invoice, String customerName, String action) {
		

		StringBuilder mailTemplate = new StringBuilder(); 
		mailTemplate.append("<!DOCTYPE html>")
		     .append("<html>")
		     .append("<head>")
		     .append("	<title>Approve</title>")
		     .append("	<link href=\"https://fonts.googleapis.com/css?family=PT+Serif\" rel=\"stylesheet\">")
		     .append("	<style>")
		     .append("	body{")
		     .append("		width: auto;")
		     .append("		height: auto;")
		     .append("		display: block;")
		     .append("		margin: 0 auto;")
		     .append("		padding: 25px;")
		     .append("		font-family: 'PT Serif', serif;")
		     .append("	}")
		     .append("	.bar {")
		     .append("		width: 100%;")
		     .append("		height: 15px;")
		     .append("		background: #026894;")
		     .append("	}")
		     .append("	</style>")
		     .append("</head>")
		     .append("<body>")
		     .append("<img src=\"http://6erxg60qvo1qvjha44jrgpan.wpengine.netdna-cdn.com/wp-content/uploads/2015/02/Zuellig-Pharma-logo.jpg\" ")
		     .append("					width=\"250\" height=\"55\"/>")
		     .append("<img align=\"right\" src=\"https://image.ibb.co/cO37cy/e_returns.png\" width=\"150\" height=\"55\"/>")
		     .append("<div class=\"bar\">")
		     .append("</div>")
		     .append("<div>")
		     .append("	<h2> Dear Approver, </h2>")
		     .append("	<p> Your E-Returns Request with Request ID <i><b>"+requestId+"</b></i> for Invoice <i><b>"+invoice+"</b></i> and ")
		     .append("	Material <i><b>"+material+"</b></i>  has been <i><b>"+action+"</b></i>.</p>")
		     .append("")
		     .append("	<b style=\"color:#00415C;\">Regards,</b><br/>")
		     .append("	<b style=\"color:#00415C;\">E-Returns Team</b>")
		     .append("</div>")
		     .append("<p><i style=\"color:grey;\">Note: This is auto generated email please do not reply.</i></p>")
		     .append("</body>")
		     .append("</html>");
	
	
		return mailTemplate.toString();
	}
	
}
