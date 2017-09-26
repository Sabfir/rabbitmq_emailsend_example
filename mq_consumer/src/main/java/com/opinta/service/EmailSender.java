package com.opinta.service;

import com.opinta.model.MessageDto;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

import static javax.mail.Message.RecipientType.TO;

public class EmailSender {

    public void send(MessageDto messageDto) {
        final String email = "oleg.sabfir@gmail.com";
        final String password = "";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(email, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email));
            message.setRecipients(TO, InternetAddress.parse(messageDto.getEmail()));
            message.setSubject("Testing Subject");
            message.setText(messageDto.getText());

            Transport.send(message);

            System.out.println("Email is sent successfully");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
