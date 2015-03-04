/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.core.protocol;

import javax.xml.bind.annotation.XmlRegistry;
import org.thingsplode.core.protocol.request.BootNotificationRequest;
import org.thingsplode.core.protocol.request.RegistrationRequest;

/**
 *
 * @author tamas.csaba@gmail.com
 */
@XmlRegistry
public class ThingsplodeObjectFactory {

    public BootNotificationRequest createBootNotificationRequest() {
        return new BootNotificationRequest();
    }
    
    public RegistrationRequest createRegistrationRequest() {
        return new RegistrationRequest();
    }
}
