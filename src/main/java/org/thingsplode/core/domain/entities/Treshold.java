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
public class Treshold {
    private Type type;//if HIGH, the method isTriggerable will return true if the indication.value < tresholdIndication.value
    private Indication tresholdIndication;

    public boolean isTriggerable(Indication indication) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    static enum Type {
        HIGH,
        LOW;
    }
}
