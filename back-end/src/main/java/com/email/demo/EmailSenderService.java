package com.email.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendNotification(String email, String subject, String text) {
        SimpleMailMessage notification = new SimpleMailMessage();
        notification.setFrom("pasddemo@gmail.com");
        notification.setTo(email);
        notification.setSubject(subject);
        notification.setText(text);

        mailSender.send(notification);

        System.out.println("Success");
    }
}
