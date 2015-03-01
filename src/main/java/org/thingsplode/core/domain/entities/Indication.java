/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.core.domain.entities;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import org.thingsplode.core.domain.Value;

/**
 *
 * @author tamas.csaba@gmail.com
 */
@Entity
public class Indication extends Persistable<Long> {

    private String name;
    private Value indicationValue;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public static Indication create() {
        return new Indication();
    }

    public static Indication create(String name, Value.Type type) {
        Indication i = Indication.create();
        i.setName(name);
        i.setIndicationValue(Value.create(type));
        return i;
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

}
