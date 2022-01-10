package com.example.lab2.jms;

import com.example.lab2.models.Event;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataModificationTopic {

    ArrayList<EventListener> listenerList = new ArrayList<>();

    public void subscribe(EventListener listener) {
        listenerList.add(listener);
    }

    public void sendUpdateEvent(String entity, Object value) {
        Event event = new Event("update", entity, value.toString());
        updateListeners(event);
    }

    public void sendInsertEvent(String entity, Object value) {
        Event event = new Event("insert", entity, value.toString());
        updateListeners(event);
    }

    public void sendDeleteEvent(String entity, Object value) {
        Event event = new Event("delete", entity, value.toString());
        updateListeners(event);
    }

    public void updateListeners(Event event) {
        for (EventListener listener: listenerList)
            listener.update(event);
    }
}
