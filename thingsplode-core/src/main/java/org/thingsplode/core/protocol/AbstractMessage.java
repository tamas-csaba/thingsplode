/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.core.protocol;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author tamas.csaba@gmail.com
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AMsg")
@XmlRootElement
public abstract class AbstractMessage<T extends AbstractMessage<T>> implements Serializable {

    @XmlTransient
    public static final String MESSAGE_TYPE = "MESSAGE_TYPE";
    
    @XmlElement(required = true, name = "MsgID")
    private String messageId;
    @XmlElement(required = true, name = "PrtVer")
    private int protocolVersion;

    public T putMessageId(String messageId){
        this.setMessageId(messageId);
        return (T)this;
    }
    
    public T putProtocolVersion(int protocolVersion){
        this.setProtocolVersion(protocolVersion);
        return (T)this;
    }
    
    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public int getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(int protocolVersion) {
        this.protocolVersion = protocolVersion;
    }
}
