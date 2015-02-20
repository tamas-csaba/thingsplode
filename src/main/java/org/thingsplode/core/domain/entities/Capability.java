/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.core.domain.entities;

/**
 *
 * @author tam
 */
public class Capability {
    private Type type;
    private String name;
    private boolean active;

    static enum Type {
        READ, //sensor information
        WRITE_OR_EXECUTE; //configuration option or command eg. ResetCapability
    }

}
