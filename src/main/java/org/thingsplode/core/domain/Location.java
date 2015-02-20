/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.core.domain;

import javax.persistence.Embeddable;

/**
 *
 * @author tam
 */
@Embeddable
public class Location {

    private String name;
    private double mLatitude = 0.0;
    private double mLongitude = 0.0;
    private Address address;
}
