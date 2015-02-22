/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.core.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author tam
 */
@Embeddable
public class Model implements Serializable {

    private String manufacturer;
    private String type;
    private String serialNumber;
    private String partNumber;
    private String version;

    public static Model create() {
        return new Model();
    }

    public Model putManufacturer(String manufacturer) {
        this.setManufacturer(manufacturer);
        return this;
    }

    public Model putType(String type) {
        this.setType(type);
        return this;
    }

    public Model putSerialNumber(String serialNumber) {
        this.setSerialNumber(serialNumber);
        return this;
    }

    public Model putPartNumber(String partNumber) {
        this.setPartNumber(partNumber);
        return this;
    }

    public Model putVersion(String version) {
        this.setVersion(version);
        return this;
    }

    /**
     * @return the manufacturer
     */
    public String getManufacturer() {
        return manufacturer;
    }

    /**
     * @param manufacturer the manufacturer to set
     */
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    /**
     * @return the type
     */
    @Column(name = "MODEL_TYPE")
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
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

    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }
}
