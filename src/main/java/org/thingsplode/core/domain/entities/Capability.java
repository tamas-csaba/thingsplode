/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.core.domain.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 *
 * @author tam
 */
@Entity
public class Capability extends Persistable<Long> {

    private Type type;
    private String name;
    private boolean active;

    static public enum Type {
        READ, //sensor information
        WRITE_OR_EXECUTE; //configuration option or command eg. ResetCapability
    }

    /**
     * @return the type
     */
    @Enumerated(EnumType.STRING)
    public Type getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(Type type) {
        this.type = type;
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
     * @return the active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    public static Capability create() {
        return new Capability();
    }

    public static Capability create(Type type, String name, boolean active) {
        Capability c = Capability.create(type, name);
        c.setActive(active);
        return c;
    }

    public static Capability create(Type type, String name) {
        Capability c = new Capability();
        c.setType(type);
        c.setName(name);
        return c;
    }

}
