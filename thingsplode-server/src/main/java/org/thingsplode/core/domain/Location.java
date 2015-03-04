/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.core.domain;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

/**
 *
 * @author tamas.csaba@gmail.com
 */
@Embeddable
public class Location implements Serializable {

    private String description;

    private double latitude = 0.0;
    private double longitude = 0.0;
    private Address address;

    public static Location create() {
        return new Location();
    }

    public static Location create(String description, Address address) {
        Location l = Location.create();
        l.setDescription(description);
        l.setAddress(address);
        return l;
    }

    public Location putDescription(String description) {
        this.setDescription(description);
        return this;
    }

    public Location putAddress(Address address) {
        this.setAddress(address);
        return this;
    }

    public Location putLatitude(double latitude) {
        this.setLatitude(latitude);
        return this;
    }

    public Location putLongitude(double longitude) {
        this.setLongitude(longitude);
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the mLatitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * @param mLatitude the mLatitude to set
     */
    public void setLatitude(double mLatitude) {
        this.latitude = mLatitude;
    }

    /**
     * @return the mLongitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * @param mLongitude the mLongitude to set
     */
    public void setLongitude(double mLongitude) {
        this.longitude = mLongitude;
    }

    /**
     * @return the address
     */
    @Embedded
    public Address getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(Address address) {
        this.address = address;
    }
}
