/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.server.bus.executors;

import javax.annotation.PostConstruct;
import javax.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.messaging.MessageChannel;
import org.thingsplode.core.protocol.ResponseCode;
import org.thingsplode.server.repositories.DeviceRepository;

/**
 *
 * @author tamas.csaba@gmail.com
 */
public abstract class AbstractExecutor {

    @Autowired(required = true)
    @Qualifier("errorChannel")
    private MessageChannel errorChannel;
    @Autowired(required = true)
    private DeviceRepository deviceRepo;
    private MessagingTemplate msgTemplate;

    protected String determineExceptionMessage(Throwable e) {
        if (e == null) {
            return null;
        }
        if (e.getMessage() != null && !e.getMessage().isEmpty()) {
            return e.getMessage();
        } else {
            return determineExceptionMessage(e.getCause());
        }

    }

    protected ResponseCode determineResponseCode(Throwable th){
          if (th instanceof PersistenceException){
              return ResponseCode.INTERNAL_PERSISTENCY_ERROR;
          } else {
              return ResponseCode.INTERNAL_SYSTEM_ERROR;
          }
      }
    
    @PostConstruct
    public void init() {
        msgTemplate = new MessagingTemplate(errorChannel);
    }

    public MessageChannel getErrorChannel() {
        return errorChannel;
    }

    public DeviceRepository getDeviceRepo() {
        return deviceRepo;
    }

    public MessagingTemplate getMsgTemplate() {
        return msgTemplate;
    }
}
