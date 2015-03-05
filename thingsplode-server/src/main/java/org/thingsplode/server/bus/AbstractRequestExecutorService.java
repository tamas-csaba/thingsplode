/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.server.bus;

import org.springframework.messaging.Message;
import org.thingsplode.core.protocol.AbstractRequest;
import org.thingsplode.core.protocol.Response;

/**
 *
 * @author tamas.csaba@gmail.com
 * @param <REQ>
 * @param <RSP>
 */
public abstract class AbstractRequestExecutorService<REQ extends AbstractRequest, RSP extends Response> {

    public abstract Message<?> execute(Message<?> msg);
}
