/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.agent.monitors.providers;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.thingsplode.agent.infrastructure.SamplingProvider;
import org.thingsplode.core.Value;
import org.thingsplode.core.entities.Event;
import org.thingsplode.core.entities.Event.Classes;
import org.thingsplode.core.entities.Event.EventType;

/**
 *
 * @author Csaba Tamas
 */
public class SystemMetricProvider extends SamplingProvider<Event> {

    private final OperatingSystemMXBean osMxBean = ManagementFactory.getOperatingSystemMXBean();
    private final MemoryMXBean memMxBean = ManagementFactory.getMemoryMXBean();
    private final ThreadMXBean threadMxBean = ManagementFactory.getThreadMXBean();

    @Override
    public List<Event> collect() {
        List<Event> events = new ArrayList<>();
        Calendar now = Calendar.getInstance();

        events.add(Event.create().
                putId(Classes.SYSTEM.HEAP.toString()).
                putEventClass(Classes.SYSTEM.toString()).
                putType(EventType.SAMPLING).
                putSeverity(Event.Severity.INFO).
                putEventDate(now).
                addIndication("heap_max", Value.Type.NUMBER, getMaxHeap()).
                addIndication("heap_current", Value.Type.NUMBER, getCurrentHeap())
        );

        events.add(Event.create().
                putId(Classes.SYSTEM.LOAD.toString()).
                putEventClass(Classes.SYSTEM.toString()).
                putType(EventType.SAMPLING).
                putSeverity(Event.Severity.INFO).
                putEventDate(now).
                addIndication("load_average", Value.Type.NUMBER, getLoadAverage())
        );
        
        events.add(Event.create().
                putId(Classes.SYSTEM.THREADS.toString()).
                putEventClass(Classes.SYSTEM.toString()).
                putType(EventType.SAMPLING).
                putSeverity(Event.Severity.INFO).
                putEventDate(now).
                addIndication("thread_count", Value.Type.NUMBER, getThreadCount()).
                addIndication("peak_thread_count", Value.Type.NUMBER, getPeakThreadCount()).
                addIndication("total_started_threads", Value.Type.NUMBER, getTotalStartedThreadCount())
        );

        return events;
    }

    private String getCurrentHeap() {
        return String.valueOf(memMxBean.getHeapMemoryUsage().getUsed());
    }

    private String getMaxHeap() {
        return String.valueOf(memMxBean.getHeapMemoryUsage().getMax());
    }

    private String getLoadAverage() {
        return String.valueOf(osMxBean.getSystemLoadAverage());
    }

    private String getPeakThreadCount() {
        return String.valueOf(threadMxBean.getPeakThreadCount());
    }

    private String getThreadCount() {
        return String.valueOf(threadMxBean.getThreadCount());
    }

    private String getTotalStartedThreadCount() {
        return String.valueOf(threadMxBean.getTotalStartedThreadCount());
    }
}
