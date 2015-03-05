/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.server.executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.thingsplode.core.exceptions.SrvExecutionException;
import org.thingsplode.core.protocol.AbstractRequest;
import org.thingsplode.core.protocol.ExecutionStatus;
import org.thingsplode.core.protocol.Response;
import org.thingsplode.core.protocol.ResponseCode;
import org.thingsplode.core.protocol.request.RegistrationRequest;
import org.thingsplode.server.infrastructure.DeviceService;

/**
 *
 * @author tamas.csaba@gmail.com
 */
@Component
public class RegistrationRequestImpl<REQ extends AbstractRequest, RSP extends Response> extends AbstractRequestExecutorService<REQ, RSP> {

    @Autowired(required = true)
    private DeviceService deviceService;

    @Override
    public Message<?> executeImpl(Message<?> msg) {
        RegistrationRequest req = (RegistrationRequest) msg.getPayload();
        try {
            deviceService.register(req.getDevice());
            return MessageBuilder.withPayload(new Response(req.getMessageId(), ExecutionStatus.ACKNOWLEDGED, ResponseCode.SUCCESSFULLY_EXECUTED)).build();
        } catch (SrvExecutionException ex) {
            return MessageBuilder.withPayload(new Response(req.getDeviceId(), ExecutionStatus.DECLINED, ResponseCode.INTERNAL_SYSTEM_ERROR, ex.getMessage())).build();
        }

    }

}
