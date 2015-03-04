/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.core.entities;

import javax.persistence.Basic;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import org.thingsplode.core.Value;

/**
 *
 * @author tamas.csaba@gmail.com
 */
@Entity
public class Indication extends Persistable<Long> {

    private String name;
    private Value indicationValue;

    @Basic(optional = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the indicationValue
     */
    @Embedded
    public Value getIndicationValue() {
        return indicationValue;
    }

    /**
     * @param indicationValue the indicationValue to set
     */
    public void setIndicationValue(Value indicationValue) {
        this.indicationValue = indicationValue;
    }

    public static Indication create() {
        return new Indication();
    }

    public static Indication create(String name) {
        Indication i = Indication.create();
        i.setName(name);
        return i;
    }

    public static Indication create(String name, Value.Type type) {
        Indication i = Indication.create(name);
        i.setIndicationValue(Value.create(type));
        return i;
    }

    public static Indication create(String name, Value.Type type, String valueContent) {
        Indication i = Indication.create(name, type);
        i.getIndicationValue().setContent(valueContent);
        return i;
    }

    public Indication putValue(Value.Type type, String valueContent) {
        if (indicationValue == null) {
            indicationValue = new Value();
        }
        indicationValue.setType(type);
        indicationValue.setContent(valueContent);
        return this;
    }

}
