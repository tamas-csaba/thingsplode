/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.core.protocol;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author tamas.csaba@gmail.com
 */
@XmlType(name = "RspCode")
@XmlEnum
public enum ResponseCode {

    SUCCESSFULLY_EXECUTED(0),
    VALIDATION_ERROR(100),
    PERMISSION_DENIED(101),
    DEVICE_NOT_FOUND(102),
    INTERNAL_PERSISTENCY_ERROR(150),
    INTERNAL_SYSTEM_ERROR(200)
    ;

    private int code;

    ResponseCode(int i) {
        code = i;
    }

    public int getCode() {
        return code;
    }

    public static ResponseCode getByCode(int code) {
        ResponseCode[] codes = ResponseCode.values();
        if (codes != null) {
            for (int i = 0; i < codes.length; i++) {
                if (codes[i].getCode() == code) {
                    return codes[i];
                }
            }
        }
        return null;
    }
}
