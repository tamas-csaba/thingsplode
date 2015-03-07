/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.thingsplode.server.bus;


import org.springframework.integration.annotation.MessagingGateway;
import org.thingsplode.core.protocol.AbstractResponse;
import org.thingsplode.core.protocol.Response;

/**
 *
 * @author tamas.csaba@gmail.com
 */
@MessagingGateway(name = "entryGateway", defaultRequestChannel = "requestChannel")
public interface TestRequestService {
    public Response execute(AbstractResponse request);
}
