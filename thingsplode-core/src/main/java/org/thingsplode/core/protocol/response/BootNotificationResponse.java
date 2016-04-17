/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.core.protocol.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import org.thingsplode.core.protocol.ExecutionStatus;
import org.thingsplode.core.protocol.ResponseCode;

/**
 *
 * @author Csaba Tamas
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BootNotificationRsp")
public class BootNotificationResponse extends ConfigurationCarrierResponse<BootNotificationResponse> {

    private Long registrationID;
    private Long currentTimeMillis;
    

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

    @Override
    public String toString() {
        return "BootNotificationResponse{ " + super.toString() + " registrationID=" + registrationID + ", currentTimeMillis=" + currentTimeMillis + '}';
    }
}
