/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.core.protocol.sync;

import java.util.ArrayList;
import java.util.Collection;
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
    private Collection<Event> events;

    public EventSync() {
        super();
    }

    public EventSync(String deviceId, String componentName, Event event, Long timeStamp) {
        super(deviceId, timeStamp);
        this.componentName = componentName;
        this.addEvent(event);
    }

    public EventSync(String deviceId, String componentName, Event event, Long timeStamp, String serviceProviderName) {
        super(deviceId, timeStamp, serviceProviderName);
        this.componentName = componentName;
        this.addEvent(event);
    }

    public final EventSync addEvent(Event evt) {
        if (evt != null) {
            if (events == null) {
                events = new ArrayList<>();
            }
            events.add(evt);
        }
        return this;
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

    @Override
    public String toString() {
        return "EventSync{ " + super.toString() + " componentName=" + componentName + ", event=" + (getEvents() != null ? getEvents() : "NO EVENTS") + '}';
    }

    /**
     * @return the events
     */
    public Collection<Event> getEvents() {
        return events;
    }

    /**
     * @param events the events to set
     */
    public void setEvents(Collection<Event> events) {
        this.events = events;
    }
}
