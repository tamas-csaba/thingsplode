/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.server.bus.executors;

import java.util.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;
import org.thingsplode.core.entities.Device;
import org.thingsplode.core.entities.Event;
import org.thingsplode.core.protocol.sync.EventSync;
import org.thingsplode.server.repositories.EventRepository;

/**
 *
 * @author tamas.csaba@gmail.com
 * @param <REQ>
 */
@Service
public class EventSyncExecutor<REQ extends EventSync> extends AbstractSyncExecutor<REQ> {

    @Autowired(required = true)
    private EventRepository eventRepo;

    @Override
    public void executeImpl(EventSync sync, MessageHeaders headers, Device device) {
        Event evt = sync.getEvent();
        //is it the device? or it is an event on the component
        evt.setComponent(device);
        Calendar now = Calendar.getInstance();
        if (evt.getReceiveDate() == null) {
            evt.setReceiveDate(now);
        }

        if (evt.getEventDate() == null) {
            evt.setEventDate(now);
        }
        eventRepo.save(evt);

    }

}
