/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.core;

import java.io.Serializable;
import org.thingsplode.core.exceptions.MessageConversionException;

/**
 *
 * @author Csaba Tamas
 */
public class Expectable implements Serializable {

    public <EXP> EXP expectMessageByType(Class<EXP> type) throws MessageConversionException {
        try {
            return type.cast(this);
        } catch (ClassCastException ex) {
            throw new MessageConversionException("The expected message is not type of " + type.getSimpleName() + ": " + ex.getMessage(), ex);
        }
    }

    @Override
    public String toString() {
        return "Expectable{" + '}';
    }
}
