/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.agent.monitors;

import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.thingsplode.agent.infrastructure.EventQueueManager;
import org.thingsplode.agent.infrastructure.SamplingProvider;
import org.thingsplode.agent.monitors.providers.SystemMetricProvider;
import org.thingsplode.agent.monitors.providers.ThreadMetricProvider;
import org.thingsplode.core.Value.Type;
import org.thingsplode.core.entities.Event;

/**
 *
 * @author tamas.csaba@gmail.com
 */
@Component
public class MonitoringExecutor {

    private static final Logger logger = Logger.getLogger(MonitoringExecutor.class);
    @Autowired(required = true)
    private EventQueueManager queueManager;
    private ScheduledExecutorService scheduler;
    @Value("${scheduler.threadpool.size:3}")
    private int schedulerThreadPoolSize;
    private HashMap<SamplingProvider, Long> samplingProviders;
    @Value("${scheduler.threadpool.size:10}")
    private long initialDelay;
    @Value("${scheduler.autoinitialize.system_metrics:true}")
    private boolean autoInitializeSystemMetricProvider;
    @Value("${scheduler.autoinitialize.thread_metrics:true}")
    private boolean autoInitializeThreadMetricProvider;

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
        if (autoInitializeSystemMetricProvider) {
            addProvider(new SystemMetricProvider(), 60);
        }
        if (autoInitializeThreadMetricProvider) {
            addProvider(new ThreadMetricProvider(), 300);
        }
        scheduleProviders();
    }

    public void addProvider(SamplingProvider provider, int samplingrateInSeconds) {
        if (provider != null) {
            if (samplingProviders == null) {
                samplingProviders = new HashMap<>();
            }
            samplingProviders.put(provider, initialDelay);
        }
    }

    private void scheduleProviders() {
        if (samplingProviders != null && !samplingProviders.isEmpty()) {
            samplingProviders.forEach((p, t) -> {
                p.setItemQueue(queueManager.getEventQueue());
                scheduler.scheduleAtFixedRate(p, initialDelay, t, TimeUnit.SECONDS);
            });
        } else {
            String issue = "The sampling provider configuration is empty, this system will not send metrics sampling.";
            logger.warn(issue);
            Event warnEvt = Event.create(Event.Classes.SYSTEM.GENERAL_ERROR.toString(), Event.Classes.SYSTEM.toString(), Event.EventType.FAULT, Event.Severity.WARNING, Calendar.getInstance());
            warnEvt.addIndication("message", Type.TEXT, issue);
            queueManager.getEventQueue().offer(warnEvt);
        }
    }
}
