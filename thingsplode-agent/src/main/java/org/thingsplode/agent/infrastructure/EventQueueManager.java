/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.agent.infrastructure;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thingsplode.core.Value;
import org.thingsplode.core.entities.Event;

/**
 *
 * @author tamas.csaba@gmail.com
 */
@Component
public class EventQueueManager {

    @Autowired(required = true)
    private BufferQueue<Event> eventQueue;

    @PostConstruct
    public void init() {
    }

    public BufferQueue<Event> getEventQueue() {
        return eventQueue;
    }

    public void sendFaultEvent(Event.Severity severity, String message) {
        Event errorEvent = Event.create(Event.Classes.SYSTEM.GENERAL_ERROR.toString(), Event.Classes.SYSTEM.toString(), Event.EventType.FAULT, severity);
        errorEvent.addIndication("message", Value.Type.TEXT, message);
        if (this.eventQueue != null) {
            this.eventQueue.offer(errorEvent);
        }
    }

}
