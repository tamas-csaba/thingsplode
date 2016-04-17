/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.server.bus.executors;

import org.apache.log4j.Logger;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.thingsplode.core.entities.Device;
import org.thingsplode.core.exceptions.SrvExecutionException;
import org.thingsplode.core.protocol.AbstractRequest;
import org.thingsplode.core.protocol.ErrorMessage;
import org.thingsplode.core.protocol.ExecutionStatus;

/**
 *
 * @author Csaba Tamas
 * @param <REQ>
 */
public abstract class AbstractSyncExecutor<REQ extends AbstractRequest> extends AbstractExecutor {

    private static final Logger logger = Logger.getLogger(AbstractSyncExecutor.class);

    public void process(Message<?> msg) {
        AbstractRequest req = (AbstractRequest) msg.getPayload();
        Device callerDevice = getDeviceRepo().findByIdentification(req.getDeviceId());
        if (callerDevice == null) {
            throw new IllegalStateException(String.format("The device identified by id [%s] is not registered", req.getDeviceId() != null ? req.getDeviceId() : "<NULL>"));
        }
        try {
            executeImpl((REQ) msg.getPayload(), msg.getHeaders(), callerDevice);
        } catch (SrvExecutionException ex) {
            logger.error(ex.getMessage(), ex);
            getMsgTemplate().send(MessageBuilder.withPayload(new ErrorMessage(ex.getExecutionStatus(), ex.getResponseCode(), ex)).build());
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            getMsgTemplate().send(MessageBuilder.withPayload(new ErrorMessage(ExecutionStatus.DECLINED, determineResponseCode(ex), ex)).build());
        }
    }

    public abstract void executeImpl(REQ msg, MessageHeaders headers, Device device) throws SrvExecutionException, Exception;
}
