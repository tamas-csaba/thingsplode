/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.agent.structures;

import java.util.concurrent.ArrayBlockingQueue;

/**
 *
 * @author tamas.csaba@gmail.com
 * @param <ITEM>
 */
public class InMemoryBufferQueue<ITEM> implements BufferQueue<ITEM> {

    private final ArrayBlockingQueue<ITEM> q = new ArrayBlockingQueue<>(100);

    @Override
    public void offer(ITEM item) throws InterruptedException {
        q.put(item);
    }

    @Override
    public ITEM take() throws InterruptedException {
        return q.take();
    }

}
