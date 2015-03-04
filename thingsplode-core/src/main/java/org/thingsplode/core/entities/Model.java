/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.core.entities;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author tamas.csaba@gmail.com
 */
@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"manufacturer", "type", "version"})
)
public class Model extends Persistable<Long> {

    private String manufacturer;
    private String type;
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

    public Model putVersion(String version) {
        this.setVersion(version);
        return this;
    }

    /**
     * @return the manufacturer
     */
    @Basic(optional = true)
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
    @Basic(optional = false)
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
     * @return the version
     */
    @Basic(optional = false)
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
