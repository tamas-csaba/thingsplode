/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.core.domain;

import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 *
 * @author tamas.csaba@gmail.com
 */
@Embeddable
public class Address implements Serializable {
    private String state;
    private String postCode;
    private String street;
    private String city;
    private String country;
    private String houseNumber;

    public static Address create(){
        return new Address();
    }
    
    public Address putState(String state){
        this.setState(state);
        return this;
    }
    
    public Address putPostCode(String postCode){
        this.setPostCode(postCode);
        return this;
    }
    
    public Address putStreet(String street){
        this.setStreet(street);
        return this;
    }
    
    public Address putCity(String city){
        this.setCity(city);
        return this;
    }
    
    public Address putCountry(String country){
        this.setCountry(country);
        return this;
    }
    
    public Address putHouseNumber(String number){
        this.setHouseNumber(number);
        return this;
    }
    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return the postCode
     */
    public String getPostCode() {
        return postCode;
    }

    /**
     * @param postCode the postCode to set
     */
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    /**
     * @return the street
     */
    public String getStreet() {
        return street;
    }

    /**
     * @param street the street to set
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return the houseNumber
     */
    public String getHouseNumber() {
        return houseNumber;
    }

    /**
     * @param houseNumber the houseNumber to set
     */
    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }
}
