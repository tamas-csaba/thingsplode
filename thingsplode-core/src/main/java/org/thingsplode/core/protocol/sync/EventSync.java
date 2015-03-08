/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.core.protocol.sync;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import org.thingsplode.core.entities.Event;
import org.thingsplode.core.protocol.AbstractRequest;

/**
 *
 * @author tamas.csaba@gmail.com
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EvtSync")
public class EventSync extends AbstractRequest {

    private String componentName;
    private Event event;

    public EventSync() {
        super();
    }

    public EventSync(String deviceId, String componentName, Event event, Long timeStamp) {
        super(deviceId, timeStamp);
        this.componentName = componentName;
        this.event = event;
    }

    public EventSync(String deviceId, String componentName, Event event, Long timeStamp, String serviceProviderName) {
        super(deviceId, timeStamp, serviceProviderName);
        this.componentName = componentName;
        this.event = event;
    }

    /**
     * @return the componentName
     */
    public String getComponentName() {
        return componentName;
    }

    /**
     * @param componentName the componentName to set
     */
    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    /**
     * @return the event
     */
    public Event getEvent() {
        return event;
    }

    /**
     * @param event the event to set
     */
    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return "EventSync{ " + super.toString() + " componentName=" + componentName + ", event=" + event + '}';
    }
}
