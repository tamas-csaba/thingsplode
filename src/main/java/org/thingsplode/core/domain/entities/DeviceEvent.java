/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.core.domain.entities;

import java.util.Calendar;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlTransient;
import org.thingsplode.core.domain.Event;

/**
 *
 * @author tamas.csaba@gmail.com
 */
@Entity
@DiscriminatorValue(value = "DEVICE_EVT")
public class DeviceEvent extends Event {

    @XmlTransient
    protected static final String JOIN_COLUMN = "DEVICE_ID";

    private Device device;

    @ManyToOne
    @JoinColumn(name = JOIN_COLUMN, insertable = true, updatable = false, nullable = false)
    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public static DeviceEvent create() {
        DeviceEvent evt = new DeviceEvent();
        evt.setEventDate(Calendar.getInstance());
        return evt;
    }

    public static DeviceEvent create(String eventId, String eventClass, Severity severity) {
        return (DeviceEvent) DeviceEvent.create().putId(eventId).putClass(eventClass).putSeverity(severity);
    }

    public static DeviceEvent create(String eventId, String eventClass, Severity severity, Calendar receiveDate) {
        return (DeviceEvent) DeviceEvent.create(eventId, eventClass, severity).putReceiveDate(receiveDate);
    }

    public DeviceEvent putDevice(Device device) {
        this.setDevice(device);
        return this;
    }

}
