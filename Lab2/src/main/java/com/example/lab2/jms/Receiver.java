package com.example.lab2.jms;

import com.example.lab2.models.Email;
import com.example.lab2.models.Event;
import com.example.lab2.services.EmailService;
import com.example.lab2.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {
    @Autowired
    private EmailService emailService;

    @Autowired
    private EventService eventService;


    @JmsListener(destination = "dataBaseWatchDog")
    public void receiveMessage(Event event) {
        eventService.save(event);

        String msg = String.format("%s happend.", event.getAction());
        Email email = new Email(msg, "admin@mail.ru");
        emailService.save(email);
    }
}
