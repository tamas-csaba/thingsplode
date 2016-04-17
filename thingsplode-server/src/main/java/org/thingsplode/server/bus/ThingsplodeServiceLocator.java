/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.server.bus;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.thingsplode.core.exceptions.SrvExecutionException;
import org.thingsplode.core.protocol.AbstractRequest;
import org.thingsplode.core.protocol.ExecutionStatus;
import org.thingsplode.core.protocol.ResponseCode;
import org.thingsplode.server.bus.executors.AbstractExecutor;

/**
 *
 * @author Csaba Tamas
 */
@Component
public class ThingsplodeServiceLocator implements ApplicationContextAware {

    private ApplicationContext ctx;

    public <SRV extends AbstractExecutor> SRV getService(Message<?> message, Class<SRV> type) throws SrvExecutionException {
        if (message == null || message.getPayload() == null) {
            throw new SrvExecutionException(ExecutionStatus.DECLINED, ResponseCode.INTERNAL_SYSTEM_ERROR, "the message or the message payload cannot be null.");
        }
        Object request = message.getPayload();
        if (request instanceof AbstractRequest) {
            AbstractRequest req = (AbstractRequest) request;
            if (req.getServiceProviderName() != null && !req.getServiceProviderName().isEmpty()) {
                try {
                    return (SRV) ctx.getBean(req.getServiceProviderName(), type);
                } catch (BeansException ex) {
                    throw new SrvExecutionException(((AbstractRequest) request).getMessageId(), ExecutionStatus.DECLINED, ResponseCode.INTERNAL_SYSTEM_ERROR, "Service of name: " + req.getServiceProviderName() + " is not installed! Try to request a different service provider name. " + ex.getMessage(), ex);
                }
            } else {
                String requestBeanName = request.getClass().getSimpleName();
                requestBeanName = StringUtils.uncapitalize(requestBeanName);
                requestBeanName += "Executor";
                try {
                    return (SRV) ctx.getBean(requestBeanName, type);
                } catch (BeansException ex) {
                    throw new SrvExecutionException(((AbstractRequest) request).getMessageId(), ExecutionStatus.DECLINED, ResponseCode.INTERNAL_SYSTEM_ERROR, "Service of name: " + requestBeanName + " is not installed! Try to request a different service provider name. " + ex.getMessage(), ex);
                }
            }
        } else {
            throw new SrvExecutionException(ExecutionStatus.DECLINED, ResponseCode.INTERNAL_SYSTEM_ERROR, "This locator does not support the request of type: " + request.getClass().getSimpleName());
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }

}
