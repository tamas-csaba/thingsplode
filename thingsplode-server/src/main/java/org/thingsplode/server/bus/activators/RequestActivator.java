/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.server.bus.activators;

import java.util.Map;
import org.springframework.integration.annotation.Headers;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.thingsplode.core.protocol.sync.EventSync;

/**
 * Possible values
 *
 * @Aggregator
 * @Filter
 * @Router
 * @ServiceActivator
 * @Splitter
 * @Transformer
 * @InboundChannelAdapter
 * @BridgeFrom
 * @BridgeTo
 * 
 * @author tamas.csaba@gmail.com
 */
@MessageEndpoint
public class RequestActivator {

//    class DeviceAwareMessageHandler extends AbstractReplyProducingMessageHandler {
//        //AbstractMessageHandler
//
//        @Override
//        protected void handleMessageInternal(Message<?> message) throws Exception {
//            if (!(message.getPayload() instanceof AbstractRequest)){
//                throw new SrvExecutionException(ExecutionStatus.DECLINED, ResponseCode.INTERNAL_SYSTEM_ERROR, "The Request Activator accepts only instances of AbstractRequest.");
//            }
//        }
//        
//    }
    
    @ServiceActivator(autoStartup = "true", requiresReply = "true", inputChannel = "registrationRequest")
    public void processMessage(EventSync eventSync, @Headers Map<String, Object> headerMap) {
        
    }

}
