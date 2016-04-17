/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.core.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlTransient;
import org.thingsplode.core.Value;

/**
 *
 * @author Csaba Tamas
 */
@Entity
@Table(
        name = Capability.TABLE_NAME,
        uniqueConstraints = @UniqueConstraint(columnNames = {"name", Component.COMP_REF})
)
public class Capability extends Persistable<Long> {

    @XmlTransient
    public final static String TABLE_NAME = "CAPABILITIES";
    @XmlTransient
    public final static String CAPABILITY_REF = "CAP_ID";
    private Type type;
    private String name;
    private boolean active;
    private Value value;
    private Collection<Parameter> parameters;

    static public enum Type {

        READ, //sensor information
        WRITE_OR_EXECUTE; //configuration option or command eg. ResetCapability
    }

    /**
     * apply the values of the Capability provided as a source to this entity
     *
     * @param source
     */
    public void refreshValues(Capability source) {
        this.type = source.getType() != null ? source.getType() : this.getType();
        this.active = source.isActive();
        this.name = source.getName();
        this.value = source.getValue();
        this.parameters = source.getParameters();
    }

    /**
     * @return the type
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
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
    @Column(nullable = false, length = 50)
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    @Embedded
    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
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

    /**
     * @return the parameters
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = CAPABILITY_REF)
    public Collection<Parameter> getParameters() {
        return parameters;
    }

    /**
     * @param parameters the parameters to set
     */
    public void setParameters(Collection<Parameter> parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return "Capability{" + "type=" + (type != null ? type : "null") + ", name=" + name + ", active=" + active + '}';
    }

    public static class CapabilityBuilder {

        private final List<Capability> capabilities = new ArrayList<>();

        public static CapabilityBuilder newBuilder() {
            return new CapabilityBuilder();
        }

        public CapabilityBuilder add(Capability capability) {
            this.capabilities.add(capability);
            return this;
        }

        private Capability createCapability(String name, Type type, boolean active) {
            Capability c = new Capability();
            c.setName(name);
            c.setType(type);
            c.setActive(active);
            return c;
        }

        public CapabilityBuilder add(String name, Type type, boolean active) {
            return this.add(createCapability(name, type, active));
        }

        public CapabilityBuilder add(String name, Type type, boolean active, Value value) {
            Capability c = createCapability(name, type, active);
            c.setValue(value);
            return this.add(c);
        }

        public CapabilityBuilder add(String name, Type type, boolean active, Value.Type valueType, String valueString) {
            return add(name, type, active, Value.create(valueType, valueString));
        }

        public CapabilityBuilder add(String name, Type type, boolean active, Parameter... params) {
            Capability c = createCapability(name, type, active);
            c.setParameters(Arrays.asList(params));
            return this.add(c);
        }

        public List<Capability> build() {
            return this.capabilities;
        }
    }

}
