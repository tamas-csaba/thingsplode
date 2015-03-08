/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.core.protocol;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author tamas.csaba@gmail.com
 * @param <T>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ACmdReq")
public abstract class AbstractCommandRequest<T extends AbstractCommandRequest> extends AbstractMessage<T> {

    @XmlElement(name = "DstDevId")
    private String destinationDeviceId;
    @XmlElement(name = "SrcSP")
    private String sourceServiceProviderName;

    public T putDestinationDeviceId(String destinationDeviceId) {
        this.setDestinationDeviceId(destinationDeviceId);
        return (T) this;
    }

    public T putSourceServiceProviderName(String sourceSPName) {
        this.setSourceServiceProviderName(sourceSPName);
        return (T) this;
    }

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

    @Override
    public String toString() {
        return "AbstractCommandRequest{ " + super.toString() + " destinationDeviceId=" + destinationDeviceId + ", sourceServiceProviderName=" + sourceServiceProviderName + '}';
    }
}
