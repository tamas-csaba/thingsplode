/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.core.entities;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 *
 * @author tamas.csaba@gmail.com
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
    @Column(nullable = false)
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
    @Column(nullable = false)
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
    @Basic(optional = false)
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

    @Override
    public String toString() {
        return "Capability{" + "type=" + (type != null ? type : "null") + ", name=" + name + ", active=" + active + '}';
    }

    public static class CapabilityBuilder {

        private List<Capability> capabiities = new ArrayList<>();

        public static CapabilityBuilder newBuilder() {
            return new CapabilityBuilder();
        }

        public CapabilityBuilder add(Capability capability) {
            this.capabiities.add(capability);
            return this;
        }

        public CapabilityBuilder add(String name, Type type, boolean active) {
            Capability c = new Capability();
            c.setName(name);
            c.setType(type);
            c.setActive(active);
            return this.add(c);
        }

        public List<Capability> build() {
            return this.capabiities;
        }
    }

}
