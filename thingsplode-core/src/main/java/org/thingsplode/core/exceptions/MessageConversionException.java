/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.thingsplode.core.exceptions;

/**
 *
 * @author tamas.csaba@gmail.com
 */
public class MessageConversionException extends Exception {

    public MessageConversionException() {
    }

    public MessageConversionException(String message) {
        super(message);
    }

    public MessageConversionException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageConversionException(Throwable cause) {
        super(cause);
    }

    public MessageConversionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
