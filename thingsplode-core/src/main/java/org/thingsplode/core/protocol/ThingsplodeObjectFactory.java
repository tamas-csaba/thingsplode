/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.core.protocol;

import javax.xml.bind.annotation.XmlRegistry;
import org.thingsplode.core.protocol.request.HeartbeatRequest;
import org.thingsplode.core.protocol.request.BootNotificationRequest;

/**
 *
 * @author Csaba Tamas
 */
@XmlRegistry
public class ThingsplodeObjectFactory {

    public HeartbeatRequest createBootNotificationRequest() {
        return new HeartbeatRequest();
    }
    
    public BootNotificationRequest createRegistrationRequest() {
        return new BootNotificationRequest();
    }
}
