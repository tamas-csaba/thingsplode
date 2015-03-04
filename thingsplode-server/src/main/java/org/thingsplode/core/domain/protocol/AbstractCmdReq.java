/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.core.domain.protocol;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author tamas.csaba@gmail.com
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ACmdReq")
public abstract class AbstractCmdReq extends AbstractMessage {

    @XmlElement(name = "DstDevId")
    private String destinationDeviceId;
    @XmlElement(name = "SrcSP")
    private String sourceServiceProviderName;

    /**
     * @return the destinationDeviceId
     */
    public String getDestinationDeviceId() {
        return destinationDeviceId;
    }

    /**
     * @param destinationDeviceId the destinationDeviceId to set
     */
    public void setDestinationDeviceId(String destinationDeviceId) {
        this.destinationDeviceId = destinationDeviceId;
    }

    /**
     * @return the sourceServiceProviderName
     */
    public String getSourceServiceProviderName() {
        return sourceServiceProviderName;
    }

    /**
     * @param sourceServiceProviderName the sourceServiceProviderName to set
     */
    public void setSourceServiceProviderName(String sourceServiceProviderName) {
        this.sourceServiceProviderName = sourceServiceProviderName;
    }
}
