package com.tenneti.thousie.service;

import org.springframework.stereotype.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class SMSService {

	public static final String ACCOUNT_SID = "<YOUR_SID>";
	public static final String AUTH_TOKEN = "<YOUR_TOKEN_HERE>";

	public Message sendSMS(String toNo, String sms, String url) {

		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
		Message message = Message
				.creator(new PhoneNumber("whatsapp:" + toNo), new PhoneNumber("whatsapp:<YOUR_TWILIO_WHATSAPP_ACCOUNT>"), sms)
				.setMediaUrl(url).create();

		return message;
	}
}
