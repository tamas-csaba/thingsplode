/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.core.protocol.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import org.thingsplode.core.StatusInfo;
import org.thingsplode.core.protocol.AbstractRequest;

/**
 *
 * @author tamas.csaba@gmail.com
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HeartbeatReq")
public class HeartbeatRequest extends AbstractRequest {
        
    private StatusInfo deviceStatus;

    /**
     * @return the deviceStatus
     */
    public StatusInfo getDeviceStatus() {
        return deviceStatus;
    }

    /**
     * @param deviceStatus the deviceStatus to set
     */
    public void setDeviceStatus(StatusInfo deviceStatus) {
        this.deviceStatus = deviceStatus;
    }
    
}
