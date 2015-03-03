/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.core.domain.entities;

import java.util.Calendar;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author tamas.csaba@gmail.com
 */
@Entity
public class Configuration extends Persistable<Long> {
    private Type type;
    private String key;
    private String value;
    private SyncStatus syncStatus;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public Type getType() {
        return type;
    }
    
    public void setType(Type type) {
        this.type = type;
    }
    
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

    private static enum SyncStatus {
        NEW, 
        SYNCED
    }
}
