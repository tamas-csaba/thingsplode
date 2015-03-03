/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.core.domain.entities;

import java.util.Calendar;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlTransient;
import org.thingsplode.core.domain.Event;

/**
 *
 * @author tamas.csaba@gmail.com
 */
@Entity
@DiscriminatorValue(value = "COMPONENT_EVT")
public class ComponentEvent extends Event {

    @XmlTransient
    protected static final String JOIN_COLUMN = "COMPONENT_ID";

    private Component component;

    @ManyToOne(optional = false)
    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }

    public static ComponentEvent create() {
        ComponentEvent evt = new ComponentEvent();
        evt.setEventDate(Calendar.getInstance());
        return evt;
    }

    public static ComponentEvent create(String eventId, String eventClass, Severity severity) {
        return (ComponentEvent) ComponentEvent.create().putId(eventId).putClass(eventClass).putSeverity(severity);
    }
    
    public static ComponentEvent create(String eventId, String eventClass, Severity severity, Calendar receiveDate) {
        return (ComponentEvent) ComponentEvent.create(eventId, eventClass, severity).putReceiveDate(receiveDate);
    }

    public ComponentEvent putComponent(Component component) {
        this.setComponent(component);
        return this;
    }

}
