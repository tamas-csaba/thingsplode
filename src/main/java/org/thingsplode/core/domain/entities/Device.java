/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.core.domain.entities;

import java.net.InetAddress;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import org.thingsplode.core.domain.AbstractComponent;
import org.thingsplode.core.domain.EnabledState;
import org.thingsplode.core.domain.Location;
import org.thingsplode.core.domain.StatusInfo;

/**
 *
 * @author tamas.csaba@gmail.com
 */
@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Device extends AbstractComponent {

    private String deviceId;
    private Calendar startupDate;
    private Calendar lastHeartBeat;
    private Location location;
    private Collection<DeviceEvent> eventLog;
    private String hostAddress;

    /**
     * @return the deviceId
     */
    @Column(nullable = false, unique = true)
    @NotNull
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
     * @return the lastHeartBeat
     */
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Calendar getLastHeartBeat() {
        return lastHeartBeat;
    }

    /**
     * @param lastHeartBeat the lastHeartBeat to set
     */
    public void setLastHeartBeat(Calendar lastHeartBeat) {
        this.lastHeartBeat = lastHeartBeat;
    }

    /**
     * @return the location
     */
    @Embedded
    public Location getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * @return the log
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = DeviceEvent.JOIN_COLUMN)
    public Collection<DeviceEvent> getEventLog() {
        return eventLog;
    }

    /**
     * @param log the log to set
     */
    public void setEventLog(Collection<DeviceEvent> log) {
        this.eventLog = log;
    }    

    /**
     * @return the ipAddress
     */
    public String getHostAddress() {
        return hostAddress;
    }

    /**
     * @param host the ipAddress to set
     */
    public void setHostAddress(String host) {
        this.hostAddress = host;
    }

    /**
     * @return the startupDate
     */
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Calendar getStartupDate() {
        return startupDate;
    }

    /**
     * @param startupDate the startupDate to set
     */
    public void setStartupDate(Calendar startupDate) {
        this.startupDate = startupDate;
    }

    public static Device create() {
        return new Device();
    }

    public static Device create(String deviceId, EnabledState enabledState, StatusInfo statusInfo) {
        Device d = Device.create();
        d.setDeviceId(deviceId);
        d.setEnabledState(enabledState);
        d.setStatus(statusInfo);
        return d;
    }

    public Device putDeviceId(String deviceId) {
        this.setDeviceId(deviceId);
        return this;
    }

    public Device putEnabledState(EnabledState enabledState) {
        this.setEnabledState(enabledState);
        return this;
    }

    public Device putStatusInfo(StatusInfo statusInfo) {
        this.setStatus(statusInfo);
        return this;
    }

    public Device putStartupDate(Calendar startupDate) {
        this.setStartupDate(startupDate);
        return this;
    }

    public Device putLastHeartbeat(Calendar heartbeatDate) {
        this.setLastHeartBeat(heartbeatDate);
        return this;
    }

    public Device putLocation(Location location) {
        this.setLocation(location);
        return this;
    }

    public Device putModel(Model model) {
        this.setModel(model);
        return this;
    }

    public Device addCapabilities(Capability... capabilitiez) {
        this.initializeCapabilities();
        Collections.addAll(this.getCapabilities(), capabilitiez);
        return this;
    }

    public Device addComponents(Component... componentz) {
        this.initializeComponents();
        Collections.addAll(this.getComponents(), componentz);
        return this;
    }

    public Device addConfigurations(Configuration... configurationz) {
       this.initializeConfiguration();
        Collections.addAll(this.getConfiguration(), configurationz);
        return this;
    }

    public Device putIpAddress(String addr) {
        this.setHostAddress(addr);
        return this;
    }

    public Device putIpAddress(InetAddress addr) {
        this.setHostAddress(addr.getHostAddress());
        return this;
    }

    public Device putSerialNumber(String serialNumber) {
        this.setSerialNumber(serialNumber);
        return this;
    }

    public Device putPartNumber(String partNumber) {
        this.setPartNumber(partNumber);
        return this;
    }
}
