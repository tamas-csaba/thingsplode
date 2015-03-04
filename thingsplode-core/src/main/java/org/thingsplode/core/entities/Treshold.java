/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.core.entities;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import org.thingsplode.core.Value;

/**
 *
 * @author tamas.csaba@gmail.com
 */
@Entity
public class Treshold extends Persistable<Long> {

    private String name;
    private Scope scope;
    private Type type;//if HIGH, the method isTriggerable will return true if the indication.value < tresholdIndication.value
    private Value tresholdValue;

    public String getName() {
        return name;
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

    @Embedded
    public Value getTresholdValue() {
        return tresholdValue;
    }

    public void setTresholdValue(Value tresholdValue) {
        this.tresholdValue = tresholdValue;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isTriggerable(Indication indication) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
}
