//package com.cafe.service;
//
//import java.io.IOException;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import com.sendgrid.Method;
//import com.sendgrid.Request;
//import com.sendgrid.Response;
//import com.sendgrid.SendGrid;
//import com.sendgrid.helpers.mail.Mail;
//import com.sendgrid.helpers.mail.objects.Content;
//import com.sendgrid.helpers.mail.objects.Email;
//
//@Service
//public class EmailService {
//	
//	@Value("${sendgrid.api.key}")
//	private String senderGridAPIkey;
//	
//	@Value("${sendgrid.sender.email}")
//	private String senderEmail;
//	
//	
//	public void sendEmail(List<String>recipients, String subject, String message) {
//		try {
//			SendGrid sendGrid= new SendGrid(senderGridAPIkey);
//			
//			for(String recipient: recipients) {
//				Email from= new Email(senderEmail);
//				Email to = new Email(recipient);
//				Content content = new Content("text/plain",message);
//				Mail mail= new Mail(from, subject,to,content);
//				
//				Request request= new Request();
//				request.setMethod(Method.POST);
//				request.setEndpoint("mail/send");
//				request.setBody(mail.build());
//				
//				Response response= sendGrid.api(request);
//                System.out.println("Email sent to: " + recipient + " | Status: " + response.getStatusCode());
//
//				
//			}
//		}catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//}
