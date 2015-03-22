/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.agent.monitors;

import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.thingsplode.agent.structures.SamplingProvider;

/**
 *
 * @author tamas.csaba@gmail.com
 */
@Component
public class MonitoringExecutor {

    private static final Logger logger = Logger.getLogger(MonitoringExecutor.class);
    private ScheduledExecutorService scheduler;
    @Value("${scheduler.threadpool.size:3}")
    private int schedulerThreadPoolSize;
    private HashMap<Long, SamplingProvider> metricProviders;
    @Value("${scheduler.threadpool.size:10}")
    private long initialDelay;

    @PostConstruct
    public void init() {
        scheduler = Executors.newScheduledThreadPool(schedulerThreadPoolSize, (Runnable r) -> {
            Thread t = new Thread(r, "SCHEDULER");
            t.setDaemon(true);
            t.setUncaughtExceptionHandler((Thread t1, Throwable e) -> {
                logger.error(String.format("Uncaught exception on thread %s. Exception %s with message %s.", t1.getName(), e.getClass().getSimpleName(), e.getMessage()), e);
            });
            return t;
        });

        if (metricProviders != null && !metricProviders.isEmpty()) {
            metricProviders.forEach((k, v) -> scheduler.scheduleAtFixedRate(v, initialDelay, k, TimeUnit.SECONDS));
        }

    }
}
