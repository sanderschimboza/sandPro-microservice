package com.example.emailservice.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.InternetHeaders;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import java.util.Properties;

@Service
@Slf4j
public class EmailService {

    public String sendMail(String email, String msg) {
        log.info("Going to send an Email Now ::::: {}", msg);
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.ssl.checkserveridentity", true);

        InternetHeaders headers = new InternetHeaders();
        headers.addHeader("Content-type", "text/html; charset=UTF-8");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("email@gmail.com", "#####");
                    }
                });
        try {
            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress("SandPro"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("SandPro - Activate Your Account");
            message.setContent(msg, "text/html");

            Transport.send(message);

            return "Message Sent!";

        } catch (Exception e) {
            log.error(e.getMessage());
            JOptionPane.showMessageDialog(null, e);
            return "Could not send Email with Error::::" + e.getMessage();
        }
    }
}
