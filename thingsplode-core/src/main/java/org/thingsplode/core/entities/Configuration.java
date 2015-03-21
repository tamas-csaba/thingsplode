/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.core.entities;

import org.thingsplode.core.ValueType;
import java.util.ArrayList;
import java.util.List;
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
        uniqueConstraints = @UniqueConstraint(name = "unique_conf", columnNames = {"key", Component.COMP_REF})
)
@Entity
public class Configuration extends Persistable<Long> {
    
    @XmlTransient
    public final static String TABLE_NAME = "CONFIGURATION";
    
    private ValueType type;
    private String key;
    private String value;
    @XmlTransient
    private SyncStatus syncStatus;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    public ValueType getType() {
        return type;
    }
    
    public void setType(ValueType type) {
        this.type = type;
    }

    /**
     *
     * @return
     */
    @Basic(optional = false)
    @Column(length = 50)
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
    @Column(length = 30)
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
    
    
    public static Configuration create() {
        return new Configuration();
    }
    
    public static Configuration create(String key, ValueType type) {
        Configuration c = new Configuration();
        c.setKey(key);
        c.setType(type);
        c.setSyncStatus(SyncStatus.NEW);
        return c;
    }
    
    public static Configuration create(String key, String value, ValueType type) {
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
    
    public void refreshValues(Configuration source) {
        this.key = source.getKey();
        this.syncStatus = source.getSyncStatus() != null ? source.getSyncStatus() : SyncStatus.NEW;
        this.type = source.getType();
        this.value = source.getValue();
    }
    
    @Override
    public String toString() {
        return "Configuration{" + "type=" + type + ", key=" + key + ", value=" + value + ", syncStatus=" + syncStatus + '}';
    }
    
    public static class ConfigurationBuilder {
        
        private List<Configuration> configurations = new ArrayList<>();
        
        public static ConfigurationBuilder newBuilder() {
            return new ConfigurationBuilder();
        }
        
        public ConfigurationBuilder add(Configuration cfg) {
            configurations.add(cfg);
            return this;
        }
        
        public ConfigurationBuilder addConfiguration(String key, ValueType type, String value) {
            return addConfiguration(key, type, value, SyncStatus.NEW);
        }
        
        public ConfigurationBuilder addConfiguration(String key, ValueType type, String value, SyncStatus syncStatus) {
            Configuration cfg = new Configuration();
            cfg.setKey(key);
            cfg.setType(type);
            cfg.setValue(value);
            cfg.setSyncStatus(syncStatus);
            return this.add(cfg);
        }
        
        public List<Configuration> build() {
            return configurations;
        }
    }
}
