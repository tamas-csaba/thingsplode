/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.agent.infrastructure;

/**
 *
 * @author Csaba Tamas
 * @param <ITEM>
 */
public interface BufferQueue<ITEM> {

    void offer(ITEM item);
    ITEM take() throws InterruptedException;
}
