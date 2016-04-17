/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.core.entities;

import java.net.InetAddress;
import java.util.Calendar;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.thingsplode.core.EnabledState;
import org.thingsplode.core.Location;
import org.thingsplode.core.StatusInfo;

/**
 *
 * @author Csaba Tamas
 */
@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@DiscriminatorValue(value = Device.MAIN_TYPE)
@Table(name = Device.TABLE_NAME)
@XmlRootElement
public class Device extends Component<Device> {

    @XmlTransient
    public final static String TABLE_NAME = "DEVICES";
    @XmlTransient
    public final static String MAIN_TYPE = "DEVICE";
    @XmlTransient
    private Calendar startupDate;
    @XmlTransient
    private Calendar lastHeartBeat;
    private Location location;
    private String hostAddress;

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
     * @return the ipAddress
     */
    @Column(length = 50)
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

    public static Device create(String deviceIdentification, EnabledState enabledState, StatusInfo statusInfo) {
        Device d = Device.create();
        d.setIdentification(deviceIdentification);
        d.setEnabledState(enabledState);
        d.setStatus(statusInfo);
        return d;
    }

    public Device putDeviceId(String deviceIdentification) {
        this.setIdentification(deviceIdentification);
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

    public Device putIpAddress(String addr) {
        this.setHostAddress(addr);
        return this;
    }

    public Device putIpAddress(InetAddress addr) {
        this.setHostAddress(addr.getHostAddress());
        return this;
    }

    @Override
    protected void setTresholdsScope(Collection<Treshold> tresholds) {
        if (tresholds != null) {
            tresholds.stream().forEach((t) -> {
                t.setScope(Treshold.Scope.DEVICE);
            });
        }
    }

    @Override
    public String toString() {
        return "Device{ " + super.toString() + ", startupDate=" + startupDate + ", lastHeartBeat=" + lastHeartBeat + ", location=" + location + ", hostAddress=" + hostAddress + '}';
    }
}
