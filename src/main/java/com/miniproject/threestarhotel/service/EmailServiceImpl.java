package com.miniproject.threestarhotel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Component
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Override
    public void sendSimpleMessage() throws MessagingException {

        MimeMessage msg = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        FileSystemResource file = new FileSystemResource(new File(
                "C:\\Users\\Handry\\Downloads\\test.xlsx"));

        helper.setTo("handry.k.wgs@gmail.com");
        helper.setSubject("Successful registration!");
        helper.setText("<h1>Your account has been registered!</h1>", true);
        helper.addAttachment("User Details.xlsx", file);

        emailSender.send(msg);
    }

}
