package com.miniproject.threestarhotel.service;

import javax.mail.MessagingException;

public interface EmailService {

    void sendSimpleMessage() throws MessagingException;
}
