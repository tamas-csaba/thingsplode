/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.agent.structures;

/**
 *
 * @author tamas.csaba@gmail.com
 * @param <ITEM>
 */
public interface BufferQueue<ITEM> {

    public void offer(ITEM item) throws InterruptedException;

    public ITEM take() throws InterruptedException;
}
