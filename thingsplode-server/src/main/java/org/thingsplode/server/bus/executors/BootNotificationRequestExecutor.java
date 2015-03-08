/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.server.bus.executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.thingsplode.core.entities.Device;
import org.thingsplode.core.exceptions.SrvExecutionException;
import org.thingsplode.core.protocol.ExecutionStatus;
import org.thingsplode.core.protocol.ResponseCode;
import org.thingsplode.core.protocol.request.BootNotificationRequest;
import org.thingsplode.core.protocol.response.BootNotificationResponse;
import org.thingsplode.server.infrastructure.DeviceService;

/**
 *
 * @author tamas.csaba@gmail.com
 * @param <REQ>
 * @param <RSP>
 */
@Service
public class BootNotificationRequestExecutor<REQ extends BootNotificationRequest, RSP extends BootNotificationResponse> extends AbstractRequestExecutorService<REQ, RSP> {

    @Autowired(required = true)
    private DeviceService deviceService;
    

    @Override
    public Message<?> executeImpl(Message<?> msg, Device d) {
        BootNotificationRequest req = (BootNotificationRequest) msg.getPayload();
        try {
            Long registrationID = deviceService.registerOrUpdate(req.getDevice()).getId();
            BootNotificationResponse rsp = new BootNotificationResponse(registrationID, req.getMessageId(), ExecutionStatus.ACKNOWLEDGED, ResponseCode.SUCCESSFULLY_EXECUTED);
            rsp.setCurrentTimeMillis(System.currentTimeMillis());
            return MessageBuilder.withPayload(rsp).build();
        } catch (SrvExecutionException ex) {
            return MessageBuilder.withPayload(new BootNotificationResponse(req.getMessageId(), ExecutionStatus.DECLINED, ResponseCode.INTERNAL_SYSTEM_ERROR, ex.getMessage())).build();
        }

    }



}
