/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.core.domain;

import java.io.Serializable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import org.thingsplode.core.domain.entities.Persistable;

/**
 *
 * @author tam
 */
@MappedSuperclass
public class Configuration extends Persistable<Long> {

    private Type type;
    private String key;
    private Serializable value;

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

    public Serializable getValue() {
        return value;
    }

    public void setValue(Serializable value) {
        this.value = value;
    }

    static enum Type {

        STRING,
        BOOLEAN,
        NUMBER,
    }

    public static Configuration create() {
        return new Configuration();
    }

    public static Configuration create(String key, Type type) {
        Configuration c = new Configuration();
        c.setKey(key);
        c.setType(type);
        return c;
    }

    public static Configuration create(String key, Serializable value, Type type) {
        Configuration c = Configuration.create(key, type);
        c.setValue(value);
        return c;
    }

    public Configuration putValue(Serializable value) {
        this.setValue(value);
        return this;
    }
}
