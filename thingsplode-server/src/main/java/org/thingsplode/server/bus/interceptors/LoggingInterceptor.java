/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.server.bus.interceptors;

import org.apache.log4j.Logger;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.stereotype.Component;

/**
 *
 * @author tamas.csaba@gmail.com
 */
@Component
public class LoggingInterceptor extends ChannelInterceptorAdapter {

    private Logger logger = Logger.getLogger(LoggingInterceptor.class);

//    public Message<?> preSend(Message<?> message, MessageChannel channel) {
//        System.out.println("\n\n\n\n PRESEND --> message on channel [" + channel.toString() + "]: \n " + message.toString());
//        return message;
//    }
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        if (logger.isDebugEnabled()) {
            logger.debug("On channel: " + channel.toString() + " -> Message: " + message.toString());
        }
        return message;

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
