/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.server.bus.interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.stereotype.Component;
import org.thingsplode.core.entities.Device;
import org.thingsplode.core.protocol.AbstractMessage;
import org.thingsplode.core.protocol.AbstractRequest;
import org.thingsplode.server.repositories.DeviceRepository;

/**
 *
 * @author tamas.csaba@gmail.com
 */
@Component
public class RequestAugmentationInterceptor extends ChannelInterceptorAdapter {

    @Autowired(required = true)
    private DeviceRepository deviceRepo;

    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
//        if (message == null) {
//            return;
//        }
//
//        Object payload = message.getPayload();
//        AbstractRequest req;
//        if (payload == null || !(payload instanceof AbstractRequest)) {
//            return;
//        } else {
//            req = (AbstractRequest) payload;
//        }
//        Device d = deviceRepo.findBydeviceId(req.getDeviceId());
//        if (d != null) {
//            message.getHeaders().put(Device.MAIN_TYPE, d);
//        }
//
//        message.getHeaders().put(AbstractMessage.MESSAGE_TYPE, req.getClass().getSimpleName());

    }
}
