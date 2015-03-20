/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.server.bus.executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
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
public class BootNotificationRequestExecutor<REQ extends BootNotificationRequest, RSP extends BootNotificationResponse> extends AbstractRequestResponseExecutor<REQ, RSP> {

    @Autowired(required = true)
    private DeviceService deviceService;

    @Override
    public BootNotificationResponse executeImpl(BootNotificationRequest req, MessageHeaders headers, Device d) throws SrvExecutionException {
        Device initializedDevice = deviceService.registerOrUpdate(req.getDevice());
        BootNotificationResponse rsp = new BootNotificationResponse(initializedDevice.getId(), req.getMessageId(), ExecutionStatus.ACKNOWLEDGED, ResponseCode.SUCCESSFULLY_EXECUTED);
        rsp.addConfigurationsFromDevice(initializedDevice);
        rsp.addTresholdsFromDevice(initializedDevice);
        rsp.setCurrentTimeMillis(System.currentTimeMillis());
        return rsp;
    }

}
