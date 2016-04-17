/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.server.bus.gateways;

import java.util.concurrent.Future;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.Payload;
import org.thingsplode.core.protocol.AbstractRequest;
import org.thingsplode.core.protocol.Response;

/**
 *
 * @author Csaba Tamas
 */
@MessagingGateway(name = "requestEntryGateway", defaultRequestChannel = "requestChannel")
public interface RequestGateway {

    public Future<Response> execute(@Payload AbstractRequest request);

}
