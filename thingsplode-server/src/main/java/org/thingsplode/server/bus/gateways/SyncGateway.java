/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.thingsplode.server.bus.gateways;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.Payload;
import org.thingsplode.core.protocol.AbstractRequest;

/**
 *
 * @author Csaba Tamas
 */
@MessagingGateway(name = "syncEntryGateway", defaultRequestChannel = "syncChannel")
public interface SyncGateway {
    
    public void process(@Payload AbstractRequest sync);
}
