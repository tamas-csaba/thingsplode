/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.agent.infrastructure;

import org.apache.log4j.Logger;

/**
 *
 * @author Csaba Tamas
 * @param <ITEM>
 */
public abstract class SamplingProvider<ITEM> extends StaticProvider<ITEM> implements Runnable {

    private static final Logger logger = Logger.getLogger(SamplingProvider.class);
    private BufferQueue<ITEM> samplingQueue;

    public void setItemQueue(BufferQueue<ITEM> metricQueue) {
        this.samplingQueue = metricQueue;
    }

    @Override
    public void run() {
        try {
            if (samplingQueue != null) {
                for (ITEM m : collect()) {
                    samplingQueue.offer(m);
                }
            } else {
                logger.warn("The metric queue is not defined, skipping sampling.");
            }
        } catch (Exception e) {
            logger.error(String.format("%s is caught while executing the %s", e.getClass().getSimpleName(), this.getClass().getSimpleName()), e);
        }
    }

    

}
