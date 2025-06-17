package com.tix.nostra.nostra_tix.util;

import org.springframework.stereotype.Service;

import jakarta.mail.*;
import jakarta.mail.internet.*;

@Service
public class EmailService {

    private Session newSession;

    // Constructor Injection
    public EmailService(Session mailSession) {
        this.newSession = mailSession;
    }

    public void sendMail(String to, String subject, String messages) throws Exception {

        Message message = new MimeMessage(newSession);
        message.setFrom(new InternetAddress("from@example.com"));
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(to));
        message.setSubject(subject);
        String msg = messages;

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(msg, "text/html; charset=utf-8");
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        message.setContent(multipart);

        Transport.send(message);

        System.out.println("Email sent successfully");
    }

}