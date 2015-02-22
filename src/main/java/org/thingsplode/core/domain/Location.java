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
 * @author tam
 */
@Embeddable
public class Location implements Serializable {

    private String name;
    private double latitude = 0.0;
    private double longitude = 0.0;
    private Address address;

    public static Location create() {
        return new Location();
    }

    public static Location create(String name, Address address) {
        Location l = Location.create();
        l.setName(name);
        l.setAddress(address);
        return l;
    }

    public Location putName(String name) {
        this.setName(name);
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

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
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
