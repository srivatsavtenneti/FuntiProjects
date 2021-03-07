package com.tenneti.thousie.service;

import java.io.File;
import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailSenderService {
	
	@Autowired
    private JavaMailSender javaMailSender;
	
	public void sendEmailWithAttachment() throws MessagingException, IOException {

        MimeMessage msg = javaMailSender.createMimeMessage();

        // true = multipart message
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo("noreply@mckinsey.com");
        
        helper.setTo("vatsav_1987@yahoo.co.in");

        helper.setSubject("Hello Srivatsav");

        // default = text/plain
        //helper.setText("Check attachment for image!");

        // true = text/html
        helper.setText("<h1>Your Tickets</h1>", true);

        // hard coded a file path
        FileSystemResource file = new FileSystemResource(new File("C:\\Users\\vatsa\\HousieTickets\\Image\\A.png"));

        helper.addAttachment("Ticket.png", file);

        javaMailSender.send(msg);

    }

}
