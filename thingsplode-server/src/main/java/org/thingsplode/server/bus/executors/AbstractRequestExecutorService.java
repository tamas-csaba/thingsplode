/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.server.bus.executors;

import javax.annotation.PostConstruct;
import javax.persistence.PersistenceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.thingsplode.core.entities.Device;
import org.thingsplode.core.exceptions.SrvExecutionException;
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

    private static final Logger logger = Logger.getLogger(AbstractRequestExecutorService.class);
    @Autowired(required = true)
    @Qualifier("errorChannel")
    private MessageChannel errorChannel;
    @Autowired(required = true)
    private DeviceRepository deviceRepo;
    private MessagingTemplate msgTemplate;

    @PostConstruct
    public void init() {
        msgTemplate = new MessagingTemplate(errorChannel);
    }

    public Message<?> execute(Message<?> msg) {
        AbstractRequest req = (AbstractRequest) msg.getPayload();
        try {
            return executeImpl(msg, deviceRepo.findBydeviceId(req.getDeviceId()));
        } catch (SrvExecutionException ex) {
            logger.error(ex.getMessage(), ex);
            //msgTemplate.send(MessageBuilder.withPayload(new ErrorMessage(ex.getExecutionStatus(), ex.getResponseCode(), ex)).build());
            return MessageBuilder.withPayload(new Response(req.getMessageId(), ex.getExecutionStatus(), ex.getResponseCode(), ex.getMessage())).build();
        } catch (PersistenceException ex) {
            logger.error(ex.getMessage(), ex);
            return MessageBuilder.withPayload(new Response(req.getMessageId(), ExecutionStatus.DECLINED, ResponseCode.INTERNAL_PERSISTENCY_ERROR, determineExceptionMessage(ex))).build();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            //msgTemplate.send(MessageBuilder.withPayload(new ErrorMessage(ExecutionStatus.DECLINED, ResponseCode.INTERNAL_SYSTEM_ERROR, ex)).build());
            return MessageBuilder.withPayload(new Response(req.getMessageId(), ExecutionStatus.DECLINED, ResponseCode.INTERNAL_SYSTEM_ERROR, determineExceptionMessage(ex))).build();
        }
    }

    private String determineExceptionMessage(Throwable e) {
        if (e == null) {
            return null;
        }
        if (e.getMessage() != null && !e.getMessage().isEmpty()) {
            return e.getMessage();
        } else {
            return determineExceptionMessage(e.getCause());
        }

    }

    public abstract Message<?> executeImpl(Message<?> msg, Device device) throws SrvExecutionException, Exception;
}
