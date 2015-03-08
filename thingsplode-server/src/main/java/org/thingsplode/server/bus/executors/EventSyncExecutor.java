/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.server.bus.executors;

import java.util.Calendar;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.thingsplode.core.entities.Device;
import org.thingsplode.core.entities.Event;
import org.thingsplode.core.protocol.ErrorMessage;
import org.thingsplode.core.protocol.ExecutionStatus;
import org.thingsplode.core.protocol.ResponseCode;
import org.thingsplode.core.protocol.response.BootNotificationResponse;
import org.thingsplode.core.protocol.sync.EventSync;
import org.thingsplode.server.repositories.EventRepository;

/**
 *
 * @author tamas.csaba@gmail.com
 * @param <REQ>
 * @param <RSP>
 */
public class EventSyncExecutor<REQ extends EventSync, RSP extends ErrorMessage> extends AbstractRequestExecutorService<REQ, RSP> {

    @Autowired(required = true)
    private EventRepository eventRepo;

    @Override
    public Message<?> executeImpl(Message<?> msg, Device device) {
        EventSync sync = (EventSync) msg.getPayload();
        if (device == null) {
            return MessageBuilder.withPayload(new BootNotificationResponse(sync.getMessageId(), ExecutionStatus.DECLINED, ResponseCode.DEVICE_NOT_FOUND)).build();
        }
        Event evt = sync.getEvent();
        evt.setComponent(device);
        if (evt.getReceiveDate() == null) {
            evt.setReceiveDate(Calendar.getInstance());
        }
        eventRepo.save(evt);
        return null;

    }

}
