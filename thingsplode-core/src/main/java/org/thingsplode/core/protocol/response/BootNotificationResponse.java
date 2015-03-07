/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.core.protocol.response;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import org.thingsplode.core.entities.Configuration;
import org.thingsplode.core.protocol.ExecutionStatus;
import org.thingsplode.core.protocol.Response;
import org.thingsplode.core.protocol.ResponseCode;

/**
 *
 * @author tamas.csaba@gmail.com
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BootNotificationRsp")
public class BootNotificationResponse extends Response {

    private Long registrationID;
    private Long currentTimeMillis;
    private String locale;
    private Collection<Configuration> configuration;

    public BootNotificationResponse() {
        super();
    }

    public BootNotificationResponse(ExecutionStatus requestStatus, ResponseCode responseCode) {
        super(requestStatus, responseCode);
    }

    public BootNotificationResponse(String responseCorrelationID, ExecutionStatus requestStatus, ResponseCode responseCode) {
        super(responseCorrelationID, requestStatus, responseCode);
    }

    public BootNotificationResponse(String responseCorrelationID, ExecutionStatus requestStatus, ResponseCode responseCode, String resultMessage) {
        super(responseCorrelationID, requestStatus, responseCode, resultMessage);
    }

    public BootNotificationResponse(Long registrationID, ExecutionStatus requestStatus, ResponseCode responseCode) {
        super(requestStatus, responseCode);
        this.registrationID = registrationID;
    }

    public BootNotificationResponse(Long registrationID, String responseCorrelationID, ExecutionStatus requestStatus, ResponseCode responseCode) {
        super(responseCorrelationID, requestStatus, responseCode);
        this.registrationID = registrationID;
    }

    public BootNotificationResponse(Long registrationID, String responseCorrelationID, ExecutionStatus requestStatus, ResponseCode responseCode, String resultMessage) {
        super(responseCorrelationID, requestStatus, responseCode, resultMessage);
        this.registrationID = registrationID;
    }

    /**
     * @return the registrationID
     */
    public Long getRegistrationID() {
        return registrationID;
    }

    /**
     * @param registrationID the registrationID to set
     */
    public void setRegistrationID(Long registrationID) {
        this.registrationID = registrationID;
    }

    /**
     * @return the locale
     */
    public String getLocale() {
        return locale;
    }

    /**
     * @param locale the locale to set
     */
    public void setLocale(String locale) {
        this.locale = locale;
    }

    /**
     * @return the currentTimeMillis
     */
    public Long getCurrentTimeMillis() {
        return currentTimeMillis;
    }

    /**
     * @param currentTimeMillis the currentTimeMillis to set
     */
    public void setCurrentTimeMillis(Long currentTimeMillis) {
        this.currentTimeMillis = currentTimeMillis;
    }

    /**
     * @return the configuration
     */
    public Collection<Configuration> getConfiguration() {
        return configuration;
    }

    /**
     * @param configuration the configuration to set
     */
    public void setConfiguration(Collection<Configuration> configuration) {
        this.configuration = configuration;
    }

    public BootNotificationResponse addConfigurations(Configuration... cfgs) {
        initializeConfiguration();
        Collections.addAll(this.getConfiguration(), cfgs);
        return this;
    }

    private void initializeConfiguration() {
        if (configuration == null) {
            this.configuration = new ArrayList<>();
        }
    }

}
