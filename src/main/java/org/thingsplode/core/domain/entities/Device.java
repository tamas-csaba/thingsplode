/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.core.domain.entities;

import org.thingsplode.core.domain.Model;
import java.net.InetAddress;
import java.util.Calendar;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import org.thingsplode.core.domain.EnabledState;
import org.thingsplode.core.domain.Location;
import org.thingsplode.core.domain.StatusInfo;

/**
 *
 * @author tam
 */
@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Device extends Persistable<Long> {
    private String deviceId;
    private EnabledState enabledState;
    private StatusInfo statusInfo;
    private Calendar startupDate;
    private Calendar lastHeartBeat;
    private Location location;
    private Model model;
    private Collection<Capability> capabilities;
    private Collection<Component> components;
    private Collection<Event> eventLog;
    private Collection<ConfigurationEntity> configuration;
    private InetAddress ipAddress;
    
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
     * @return the enabledState
     */
    @Enumerated(EnumType.STRING)
    public EnabledState getEnabledState() {
        return enabledState;
    }

    /**
     * @param enabledState the enabledState to set
     */
    public void setEnabledState(EnabledState enabledState) {
        this.enabledState = enabledState;
    }

    /**
     * @return the statusInfo
     */
    @Enumerated(EnumType.STRING)
    public StatusInfo getStatusInfo() {
        return statusInfo;
    }

    /**
     * @param statusInfo the statusInfo to set
     */
    public void setStatusInfo(StatusInfo statusInfo) {
        this.statusInfo = statusInfo;
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
     * @return the model
     */
    @Embedded
    public Model getModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(Model model) {
        this.model = model;
    }

    /**
     * @return the capabilities
     */
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    public Collection<Capability> getCapabilities() {
        return capabilities;
    }

    /**
     * @param capabilities the capabilities to set
     */
    public void setCapabilities(Collection<Capability> capabilities) {
        this.capabilities = capabilities;
    }

    /**
     * @return the components
     */
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name="DEVICE_ID")
    public Collection<Component> getComponents() {
        return components;
    }

    /**
     * @param components the components to set
     */
    public void setComponents(Collection<Component> components) {
        this.components = components;
    }

    /**
     * @return the log
     */
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinColumn(name="DEVICE_ID")
    public Collection<Event> getEventLog() {
        return eventLog;
    }

    /**
     * @param log the log to set
     */
    public void setEventLog(Collection<Event> log) {
        this.eventLog = log;
    }

    /**
     * @return the configuration
     */
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinColumn(name="DEVICE_ID")
    public Collection<ConfigurationEntity> getConfiguration() {
        return configuration;
    }

    /**
     * @param configuration the configuration to set
     */
    public void setConfiguration(Collection<ConfigurationEntity> configuration) {
        this.configuration = configuration;
    }

    /**
     * @return the ipAddress
     */
    public InetAddress getIpAddress() {
        return ipAddress;
    }

    /**
     * @param ipAddress the ipAddress to set
     */
    public void setIpAddress(InetAddress ipAddress) {
        this.ipAddress = ipAddress;
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
}
