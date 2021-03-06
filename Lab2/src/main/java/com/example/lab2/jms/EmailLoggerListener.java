package com.example.lab2.jms;

import com.example.lab2.models.Email;
import com.example.lab2.models.Event;
import com.example.lab2.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class EmailLoggerListener implements EventListener {
    @Autowired
    private EmailService emailService;

    @Override
    public void update(Event event) {
        String msg = String.format("%s happend.", event.getAction());
        Email email = new Email(msg, "someone@example.com");
        emailService.save(email);
    }
}
