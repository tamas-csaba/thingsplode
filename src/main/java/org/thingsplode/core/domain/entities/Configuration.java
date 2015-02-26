/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.core.domain.entities;

import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author tam
 */
@Entity
public class Configuration extends Persistable<Long> {
    private Type type;
    private String key;
    private String value;
    private Calendar commitDate;
    
    @Enumerated(EnumType.STRING)
    public Type getType() {
        return type;
    }
    
    public void setType(Type type) {
        this.type = type;
    }
    
    public String getKey() {
        return key;
    }
    
    public void setKey(String key) {
        this.key = key;
    }
    
    public String getValue() {
        return value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
    
    @Temporal(TemporalType.TIMESTAMP)
    public Calendar getCommitDate() {
        return commitDate;
    }
    
    public void setCommitDate(Calendar commitDate) {
        this.commitDate = commitDate;
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
}
