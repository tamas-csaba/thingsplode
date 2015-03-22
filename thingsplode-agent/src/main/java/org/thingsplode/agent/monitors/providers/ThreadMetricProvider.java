/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.agent.monitors.providers;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import org.thingsplode.agent.infrastructure.SamplingProvider;
import org.thingsplode.core.Value;
import org.thingsplode.core.entities.Event;

/**
 *
 * @author tamas.csaba@gmail.com
 */
public class ThreadMetricProvider extends SamplingProvider<Event> {

    private ThreadMXBean threadMxBean = ManagementFactory.getThreadMXBean();

    private List<ThreadInfo> getThreadInfo() {
        return Arrays.asList(threadMxBean.getThreadInfo(threadMxBean.getAllThreadIds()));
    }

    @Override
    public List<Event> collect() {
        List<Event> events = new ArrayList<>();
        Calendar now = Calendar.getInstance();
        
        Event evt = Event.create().
                putId(Event.Classes.SYSTEM.THREAD_DETAILS.toString()).
                putEventClass(Event.Classes.SYSTEM.toString()).
                putType(Event.EventType.SAMPLING).
                putSeverity(Event.Severity.INFO).
                putEventDate(now);
        
        getThreadInfo().forEach(ti -> {
            evt.addIndication("thread_name", Value.Type.TEXT, ti.getThreadName()).
                    addIndication("thread_state", Value.Type.TEXT, ti.getThreadState().toString()).
                    addIndication("thread_blocked_count", Value.Type.NUMBER, String.valueOf(ti.getBlockedCount())).
                    addIndication("thread_blocked_time", Value.Type.NUMBER, String.valueOf(ti.getBlockedTime()));
        });
        
        events.add(evt);
        return events;
    }

}
