/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.server.bus;

import org.thingsplode.server.executors.AbstractRequestExecutorService;
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

/**
 *
 * @author tamas.csaba@gmail.com
 */
@Component
public class ThingsplodeServiceLocator implements ApplicationContextAware {

    private ApplicationContext ctx;

    public AbstractRequestExecutorService getService(Message<?> message) throws SrvExecutionException {
        if (message == null || message.getPayload() == null) {
            throw new SrvExecutionException(ExecutionStatus.DECLINED, ResponseCode.INTERNAL_SYSTEM_ERROR, "the message or the message payload cannot be null.");
        }

        Object request = message.getPayload();

        AbstractRequestExecutorService service;
        if (request instanceof AbstractRequest) {
            AbstractRequest req = (AbstractRequest) request;
            if (req.getServiceProviderName() != null && !req.getServiceProviderName().isEmpty()) {
                try {
                    service = (AbstractRequestExecutorService) ctx.getBean(req.getServiceProviderName(), AbstractRequestExecutorService.class);
                } catch (BeansException ex) {
                    throw new SrvExecutionException(((AbstractRequest) request).getMessageId(), ExecutionStatus.DECLINED, ResponseCode.INTERNAL_SYSTEM_ERROR, "Service of name: " + req.getServiceProviderName() + " is not installed! Try to request a different service provider name. " + ex.getMessage(), ex);
                }
            } else {
                String requestBeanName = request.getClass().getSimpleName();
                requestBeanName = StringUtils.uncapitalize(requestBeanName);
                requestBeanName += "Impl";
                try {
                    service = (AbstractRequestExecutorService) ctx.getBean(requestBeanName, AbstractRequestExecutorService.class);
                } catch (BeansException ex) {
                    throw new SrvExecutionException(((AbstractRequest) request).getMessageId(), ExecutionStatus.DECLINED, ResponseCode.INTERNAL_SYSTEM_ERROR, "Service of name: " + requestBeanName + " is not installed! Try to request a different service provider name. " + ex.getMessage(), ex);
                }
            }
        } else {
            throw new SrvExecutionException(ExecutionStatus.DECLINED, ResponseCode.INTERNAL_SYSTEM_ERROR, "This locator does not support the request of type: " + request.getClass().getSimpleName());
        }
        return service;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }

}
