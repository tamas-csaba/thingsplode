/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.core;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 *
 * @author tamas.csaba@gmail.com
 */
@Embeddable
public class Value implements Serializable {

    private Type type;
    private String content;

    /**
     * @return the type
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "VALUE_TYPE")
    public Type getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(Type type) {
        this.type = type;
    }

    public static Value create(Type type) {
        Value v = new Value();
        v.setType(type);
        return v;
    }

    public static Value create(Type type, String value) {
        Value v = Value.create(type);
        v.setContent(value);
        return v;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    public static enum Type {
        NUMBER,
        TEXT,
        BOOLEAN
    }
}
