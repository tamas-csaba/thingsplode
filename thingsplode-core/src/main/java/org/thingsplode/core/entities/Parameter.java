/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.core.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;
import org.thingsplode.core.ValueType;

/**
 *
 * @author tamas.csaba@gmail.com
 */
@Entity
@Table(name = Parameter.TABLE_NAME)
public class Parameter extends Persistable<Long> {

    @XmlTransient
    public final static String TABLE_NAME = "PARAMETERS";
    private String name;
    private ValueType valueType;

    public Parameter() {
        super();
    }

    public Parameter(String name, ValueType valueType) {
        super();
        this.name = name;
        this.valueType = valueType;
    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public ValueType getValueType() {
        return valueType;
    }

    public void setValueType(ValueType valueType) {
        this.valueType = valueType;
    }

}
