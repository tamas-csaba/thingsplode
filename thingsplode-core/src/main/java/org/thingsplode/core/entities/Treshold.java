/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.core.entities;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
        name = Treshold.TABLE_NAME,
        uniqueConstraints = @UniqueConstraint(columnNames = {"name", Component.COMP_REF})
)
public class Treshold extends Persistable<Long> {

    @XmlTransient
    public final static String TABLE_NAME = "TRESHOLDS";

    private String name;
    private Scope scope;
    private Type type;//if HIGH, the method isTriggerable will return true if the indication.value < tresholdIndication.value
    private Value tresholdValue;

    void refresValues(Treshold treshold) {
        if (treshold.getName() != null && !treshold.getName().isEmpty()) {
            this.name = treshold.getName();
        }
        if (treshold.getScope() != null) {
            this.scope = treshold.getScope();
        }
        if (treshold.getType() != null) {
            this.type = treshold.getType();
        }
        if (treshold.getTresholdValue() != null) {
            this.tresholdValue = treshold.getTresholdValue();
        }
    }

    @Column(length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the type
     */
    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    public Type getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(Type type) {
        this.type = type;
    }

    @Embedded
    public Value getTresholdValue() {
        return tresholdValue;
    }

    public void setTresholdValue(Value tresholdValue) {
        this.tresholdValue = tresholdValue;
    }

    public static Treshold create(String name, Type tresholdType) {
        Treshold t = new Treshold();
        t.setName(name);
        t.setType(tresholdType);
        return t;
    }

    public static Treshold create(String tresholdName, Type tresholdType, Value value) {
        Treshold t = Treshold.create(tresholdName, tresholdType);
        t.setTresholdValue(value);
        return t;
    }

    public static Treshold create(String tresholdName, Type tresholdType, Value.Type valueType, String tresholdValue) {
        Treshold t = Treshold.create(tresholdName, tresholdType);
        t.setTresholdValue(Value.create(valueType, tresholdValue));
        return t;
    }

    public Treshold putValue(Value.Type valueType, String tresholdValue) {
        this.setTresholdValue(Value.create(valueType, tresholdValue));
        return this;
    }

    /**
     * @return the scope
     */
    @Enumerated(EnumType.STRING)
    public Scope getScope() {
        return scope;
    }

    /**
     * @param scope the scope to set
     */
    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public static enum Type {

        HIGH,
        LOW;
    }

    public static enum Scope {

        GLOBAL,
        DEVICE,
        COMPONENT;
    }

    public static class TresholdBuilder {

        private final List<Treshold> tresholds = new ArrayList<>();

        public static TresholdBuilder newBuilder() {
            return new TresholdBuilder();
        }

        public TresholdBuilder add(Treshold treshold) {
            this.tresholds.add(treshold);
            return this;
        }

        public TresholdBuilder add(String tresholdName, Type tresholdType, Value.Type valueType, String valueContent) {
            Treshold trsh = new Treshold();
            trsh.setName(tresholdName);
            trsh.setType(tresholdType);
            trsh.setTresholdValue(Value.create(valueType, valueContent));
            this.tresholds.add(trsh);
            return this;
        }

        public List<Treshold> build() {
            return tresholds;
        }
    }

}
