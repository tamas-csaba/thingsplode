/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.core.domain.entities;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
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
    private Collection<Configuration> configuration;
    private String hostAddress;
    private String serialNumber;
    private String partNumber;

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
     * @return the enabledState
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
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
    @Column(nullable = false)
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
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
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
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
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
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "DEVICE_ID")
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
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "DEVICE_ID")
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
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "DEVICE_ID")
    public Collection<Configuration> getConfiguration() {
        return configuration;
    }

    /**
     * @param configuration the configuration to set
     */
    public void setConfiguration(Collection<Configuration> configuration) {
        this.configuration = configuration;
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
    public void setIpAddress(String host) {
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

    /**
     * @return the serialNumber
     */
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     * @param serialNumber the serialNumber to set
     */
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    /**
     * @return the partNumber
     */
    public String getPartNumber() {
        return partNumber;
    }

    /**
     * @param partNumber the partNumber to set
     */
    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public static Device create() {
        return new Device();
    }

    public static Device create(String deviceId, EnabledState enabledState, StatusInfo statusInfo) {
        Device d = Device.create();
        d.setDeviceId(deviceId);
        d.setEnabledState(enabledState);
        d.setStatusInfo(statusInfo);
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
        this.setStatusInfo(statusInfo);
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
        if (this.capabilities == null) {
            this.capabilities = new ArrayList<>();
        }
        Collections.addAll(this.capabilities, capabilitiez);
        return this;
    }

    public Device addComponents(Component... componentz) {
        if (this.components == null) {
            this.components = new ArrayList<>();
        }
        Collections.addAll(this.components, componentz);
        return this;
    }

    public Device addConfigurations(Configuration... configurationz) {
        if (this.configuration == null) {
            this.configuration = new ArrayList<>();
        }
        Collections.addAll(this.configuration, configurationz);
        return this;
    }

    public Device putIpAddress(String addr) {
        this.setIpAddress(addr);
        return this;
    }

    public Device putIpAddress(InetAddress addr) {
        this.setIpAddress(addr.getHostAddress());
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
