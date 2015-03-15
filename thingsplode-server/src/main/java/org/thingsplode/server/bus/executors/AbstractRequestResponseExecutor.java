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
import org.thingsplode.core.protocol.ExecutionStatus;
import org.thingsplode.core.protocol.Response;
import org.thingsplode.core.protocol.ResponseCode;

/**
 *
 * @author tamas.csaba@gmail.com
 * @param <REQ>
 * @param <RSP>
 */
public abstract class AbstractRequestResponseExecutor<REQ extends AbstractRequest, RSP extends Response> extends AbstractExecutor {

    private static final Logger logger = Logger.getLogger(AbstractRequestResponseExecutor.class);

    public Message<?> execute(Message<?> msg) {
        AbstractRequest req = (AbstractRequest) msg.getPayload();
        try {
            RSP response = executeImpl((REQ) msg.getPayload(), msg.getHeaders(), getDeviceRepo().findBydeviceId(req.getDeviceId()));
            if (response != null) {
                return MessageBuilder.withPayload(configureCommonFields(req, response)).build();
            } else {
                return MessageBuilder.withPayload(
                        configureCommonFields(req, new Response<>(req.getMessageId(), ExecutionStatus.DECLINED, ResponseCode.REQUIRED_RESPONSE_IS_MISSING, "return payload value is null"))).
                        build();
            }

        } catch (SrvExecutionException ex) {
            logger.error(ex.getMessage(), ex);
            //msgTemplate.send(MessageBuilder.withPayload(new ErrorMessage(ex.getExecutionStatus(), ex.getResponseCode(), ex)).build());
            return MessageBuilder.withPayload(
                    configureCommonFields(req, new Response<>(req.getMessageId(), ex.getExecutionStatus(), ex.getResponseCode(), ex.getMessage()))).
                    build();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            //msgTemplate.send(MessageBuilder.withPayload(new ErrorMessage(ExecutionStatus.DECLINED, ResponseCode.INTERNAL_SYSTEM_ERROR, ex)).build());
            return MessageBuilder.withPayload(
                    configureCommonFields(req, new Response<>(req.getMessageId(), ExecutionStatus.DECLINED, determineResponseCode(ex), determineExceptionMessage(ex)))).
                    build();
        }
    }

    private RSP configureCommonFields(AbstractRequest request, Response<RSP> originalRsp) {
        if (originalRsp == null) {
            return null;
        } else {
            return (RSP) originalRsp.
                    putResponseCorrelationId(request.getMessageId()).
                    putSourceServiceProviderName(this.getClass().getSimpleName()).
                    putDestinationDeviceId(request.getDeviceId()).
                    putProtocolVersion(request.getProtocolVersion());
        }
    }

    public abstract RSP executeImpl(REQ msg, MessageHeaders headers, Device device) throws SrvExecutionException, Exception;
}
