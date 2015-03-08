/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.core.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author tamas.csaba@gmail.com
 */
@Table(
        name = Configuration.TABLE_NAME,
        uniqueConstraints = @UniqueConstraint(columnNames = {"key", Component.COMP_REF})
)
@Entity
public class Configuration extends Persistable<Long> {
    
    @XmlTransient
    public final static String TABLE_NAME = "COMPONENT";
    
    private Type type;
    private String key;
    private String value;
    @XmlTransient
    private SyncStatus syncStatus;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public Type getType() {
        return type;
    }
    
    public void setType(Type type) {
        this.type = type;
    }

    /**
     *
     * @return
     */
    @Basic(optional = false)
    public String getKey() {
        return key;
    }
    
    public void setKey(String key) {
        this.key = key;
    }
    
    @Basic(optional = false)
    public String getValue() {
        return value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the syncStatus
     */
    @Enumerated(EnumType.STRING)
    public SyncStatus getSyncStatus() {
        return syncStatus;
    }

    /**
     * @param syncStatus the syncStatus to set
     */
    public void setSyncStatus(SyncStatus syncStatus) {
        this.syncStatus = syncStatus;
    }
    
    public void setNew() {
        this.setSyncStatus(SyncStatus.NEW);
    }
    
    public void setSynced() {
        this.setSyncStatus(SyncStatus.SYNCED);
    }
    
    public static enum Type {
        
        STRING,
        BOOLEAN,
        NUMBER,
    }
    
    public static Configuration create() {
        return new Configuration();
    }
    
    public static Configuration create(String key, Configuration.Type type) {
        Configuration c = new Configuration();
        c.setKey(key);
        c.setType(type);
        return c;
    }
    
    public static Configuration create(String key, String value, Configuration.Type type) {
        Configuration c = Configuration.create(key, type);
        c.setValue(value);
        return c;
    }
    
    public Configuration putValue(String value) {
        this.setValue(value);
        return this;
    }
    
    public static enum SyncStatus {
        
        NEW,
        SYNCED
    }
}
