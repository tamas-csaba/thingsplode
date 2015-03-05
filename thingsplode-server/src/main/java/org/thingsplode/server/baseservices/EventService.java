/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.server.baseservices;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thingsplode.core.entities.Device;
import org.thingsplode.core.entities.Event;
import org.thingsplode.server.repositories.DeviceRepository;
import org.thingsplode.server.repositories.EventRepository;

/**
 *
 * @author tamas.csaba@gmail.com
 */
@Service
public class EventService {

    private Logger logger = Logger.getLogger(EventService.class);

    @Autowired
    private DeviceRepository deviceRepo;
    @Autowired
    private EventRepository eventRepo;
    private ExecutorService executor;
    //private ArrayBlockingQueue< queue ;

    @PostConstruct
    public void init() {
//        executor = Executors.newSingleThreadExecutor();
//        executor.execute(new Runnable() {
//
//            @Override
//            public void run() {
//
//            }
//        });

    }

    public void registerEvents(Device device, String componentName, Event evt) {
        
    }

    @PreDestroy
    public void destroy() throws InterruptedException {
//        executor.shutdown();
//        if (!executor.awaitTermination(3, TimeUnit.MINUTES)) {
//            logger.warn("the executor service did not gracefully shut down.");
//        }

    }
}
