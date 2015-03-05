/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.server.executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.thingsplode.core.entities.Device;
import org.thingsplode.core.protocol.AbstractRequest;
import org.thingsplode.core.protocol.ExecutionStatus;
import org.thingsplode.core.protocol.Response;
import org.thingsplode.core.protocol.ResponseCode;
import org.thingsplode.server.repositories.DeviceRepository;

/**
 *
 * @author tamas.csaba@gmail.com
 * @param <REQ>
 * @param <RSP>
 */
public abstract class AbstractRequestExecutorService<REQ extends AbstractRequest, RSP extends Response> {
    
    @Autowired(required = true)
    private DeviceRepository deviceRepo;

    public Message<?> execute(Message<?> msg) {
        AbstractRequest req = (AbstractRequest) msg.getPayload();
        try {
            return executeImpl(msg, deviceRepo.findBydeviceId(req.getDeviceId()));
        } catch (Exception ex) {
            return MessageBuilder.withPayload(new Response(req.getMessageId(), ExecutionStatus.DECLINED, ResponseCode.INTERNAL_SYSTEM_ERROR, ex.getMessage())).build();
        }
    }

    public abstract Message<?> executeImpl(Message<?> msg, Device device);
}
