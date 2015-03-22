/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.agent.infrastructure;

import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.thingsplode.core.entities.Event;

/**
 *
 * @author tamas.csaba@gmail.com
 */
@Component
public class EventQueueManager {

    private BufferQueue<Event> eventQueue;

    @PostConstruct
    public void init() {
    }

    public BufferQueue<Event> getEventQueue() {
        return eventQueue;
    }

}
