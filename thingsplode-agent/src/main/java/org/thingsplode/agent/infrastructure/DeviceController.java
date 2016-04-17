/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.agent.infrastructure;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.thingsplode.agent.monitors.providers.SystemComponentProvider;
import org.thingsplode.core.EnabledState;
import org.thingsplode.core.StatusInfo;
import org.thingsplode.core.entities.Device;

/**
 *
 * @author Csaba Tamas
 */
@Component
public class DeviceController implements ApplicationListener<ApplicationEvent> {

    private Device device;
    @Autowired(required = true)
    private SystemComponentProvider systemComponentProvider;
    @Value("${device.identification:default_device}")
    private String deviceId;

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ContextRefreshedEvent) {
            ContextRefreshedEvent crevt = ((ContextRefreshedEvent) event);
            device = Device.create(deviceId, EnabledState.DISABLED, StatusInfo.OFFLINE);
            device.addComponents(systemComponentProvider.collect());
            try {
                device.putIpAddress(InetAddress.getLocalHost());
                device.putStartupDate(Calendar.getInstance());
            } catch (UnknownHostException ex) {
                Logger.getLogger(DeviceController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
