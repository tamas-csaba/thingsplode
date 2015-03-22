/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.agent.infrastructure;

import java.util.concurrent.ArrayBlockingQueue;
import org.apache.log4j.Logger;

/**
 *
 * @author tamas.csaba@gmail.com
 * @param <ITEM>
 */
public class InMemoryBufferQueue<ITEM> implements BufferQueue<ITEM> {

    private static final Logger logger = Logger.getLogger(InMemoryBufferQueue.class);
    private final ArrayBlockingQueue<ITEM> q = new ArrayBlockingQueue<>(100);

    @Override
    public void offer(ITEM item) {
        try {
            q.put(item);
        } catch (InterruptedException ex) {
            logger.error(String.format("%s caught while trying to put an item in the %s with message: %s", ex.getClass().getSimpleName(), this.getClass().getSimpleName(), ex.getMessage()), ex);
        }
    }

    @Override
    public ITEM take() throws InterruptedException {
        return q.take();
    }

}
