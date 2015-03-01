/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.core.domain.entities;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import org.thingsplode.core.domain.Value;

/**
 *
 * @author tamas.csaba@gmail.com
 */
@Entity
public class Treshold extends Persistable<Long> {

    private Type type;//if HIGH, the method isTriggerable will return true if the indication.value < tresholdIndication.value
    private String name;
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
    
    public static Treshold create(String name, Type type){
        Treshold t = new Treshold();
        t.setName(name);
        t.setType(type);
        return t;
    }
    
    public static Treshold create (String name, Type type, Value value){
        Treshold t = Treshold.create(name, type);
        t.setTresholdValue(value);
        return t;
        
    }
            
    public static enum Type {

        HIGH,
        LOW;
    }
}
