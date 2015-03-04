/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.core.domain.protocol;

import java.util.Calendar;
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
    private Calendar timeStamp;

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
     * @return the timeStamp
     */
    public Calendar getTimeStamp() {
        return timeStamp;
    }

    /**
     * @param timeStamp the timeStamp to set
     */
    public void setTimeStamp(Calendar timeStamp) {
        this.timeStamp = timeStamp;
    }
}
