/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.AbstractMessageChannel;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.handler.AbstractReplyProducingMessageHandler;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.support.MessageBuilder;
import org.thingsplode.core.exceptions.SrvExecutionException;
import org.thingsplode.core.protocol.ExecutionStatus;
import org.thingsplode.core.protocol.Response;
import org.thingsplode.core.protocol.ResponseCode;
import org.thingsplode.server.bus.ThingsplodeServiceLocator;
import org.thingsplode.server.bus.interceptors.RequestAugmentationInterceptor;

/**
 * * Possible values
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
@Configuration
public class BusConfig {

    @Bean
    @Description("Entry to the messaging system through the gateway for requests.")
    public MessageChannel requestChannel() {
        AbstractMessageChannel ch = new DirectChannel();
        ch.addInterceptor(0, requestAugmentationInterceptor());
        return ch;
    }

    @Bean
    @Description("Entry to the messaging system through the gateway for sync messages.")
    public MessageChannel syncChannel() {
        AbstractMessageChannel ch = new DirectChannel();
        ch.addInterceptor(0, requestAugmentationInterceptor());
        return ch;
    }

    @Bean
    @Description("Exit from the messaging system through the gateway.")
    public MessageChannel responseChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(autoStartup = "true", requiresReply = "true", inputChannel = "requestChannel")
    public MessageHandler requestMessageHandler() {
        return new AbstractReplyProducingMessageHandler() {

            @Autowired
            private ThingsplodeServiceLocator serviceLocator;

            @Override
            protected Object handleRequestMessage(Message<?> requestMessage) {
                try {
                    return serviceLocator.getService(requestMessage).execute(requestMessage);
                } catch (SrvExecutionException ex) {
                    return MessageBuilder.withPayload(new Response(ex.getMessageCorrelationID(), ExecutionStatus.DECLINED, ResponseCode.INTERNAL_SYSTEM_ERROR, ex.getMessage()));
                }
            }
        };
    }

//    @Bean
//    @Router(autoStartup = "true", inputChannel = "requestChannel")
//    public HeaderValueRouter messageTypeRouter() {
//        HeaderValueRouter router = new HeaderValueRouter(AbstractMessage.MESSAGE_TYPE);
//        router.setChannelMapping(null, null);
//        return router;
//    }
//    @Bean
//    @Description("Sends request messages to the web service outbound gateway")
//    public MessageChannel invocationChannel() {
//        return new DirectChannel();
//    }
    @Bean
    public RequestAugmentationInterceptor requestAugmentationInterceptor() {
        return new RequestAugmentationInterceptor();
    }

}
