/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.core.protocol;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author tamas.csaba@gmail.com
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AReq")
public abstract class AbstractRequest extends AbstractMessage {

    @XmlElement(required = true, name = "DeviceID")
    @NotNull
    private String deviceId;
    @XmlElement(name = "MsgTstmp")
    private Long messageTimestamp;
    @XmlElement(name = "SrvProvider")
    private String serviceProviderName;

    public AbstractRequest() {
    }

    public AbstractRequest(String deviceId, Long timeStamp) {
        this.deviceId = deviceId;
        this.messageTimestamp = timeStamp;
    }

    public AbstractRequest(String deviceId, Long timeStamp, String serviceProviderName) {
        this(deviceId, timeStamp);
        this.serviceProviderName = serviceProviderName;
    }

    /**
     * @return the deviceId
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * @param deviceId the deviceId to set
     */
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * @return the serviceProviderName
     */
    public String getServiceProviderName() {
        return serviceProviderName;
    }

    /**
     * @param serviceProviderName the serviceProviderName to set
     */
    public void setServiceProviderName(String serviceProviderName) {
        this.serviceProviderName = serviceProviderName;
    }

    /**
     * @return the messageTimestamp
     */
    public Long getMessageTimestamp() {
        return messageTimestamp;
    }

    /**
     * @param messageTimestamp the messageTimestamp to set
     */
    public void setMessageTimestamp(Long messageTimestamp) {
        this.messageTimestamp = messageTimestamp;
    }



}
