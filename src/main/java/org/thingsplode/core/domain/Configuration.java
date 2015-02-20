/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.core.domain;

import java.io.Serializable;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author tam
 */
@MappedSuperclass
public class Configuration {

    private Type type;
    private String key;
    private Serializable value;

    static enum Type {
        STRING,
        BOOLEAN,
        NUMBER,
    }
}
