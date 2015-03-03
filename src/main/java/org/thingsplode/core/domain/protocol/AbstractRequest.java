/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.thingsplode.core.domain.protocol;

import java.util.Calendar;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author tamas.csaba@gmail.com
 */
public abstract class AbstractRequest extends AbstractMessage {
    @XmlElement(required = true, name = "DeviceID")
    @NotNull
    private String deviceId;
    @XmlElement(name = "Tstmp")
    private Calendar timeStamp;
}
