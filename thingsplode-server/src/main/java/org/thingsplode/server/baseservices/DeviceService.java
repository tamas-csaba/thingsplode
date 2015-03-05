/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.server.baseservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thingsplode.core.entities.Device;
import org.thingsplode.core.exceptions.SrvExecutionException;
import org.thingsplode.core.protocol.ExecutionStatus;
import org.thingsplode.core.protocol.ResponseCode;
import org.thingsplode.server.repositories.DeviceRepository;
import org.thingsplode.server.repositories.EventRepository;

/**
 *
 * @author tamas.csaba@gmail.com
 */
@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepo;
    @Autowired
    private EventRepository eventRepo;
    //private boolean 

    @Transactional
    public void register(Device device) throws SrvExecutionException {
        try {
            deviceRepo.save(device);
        } catch (Exception e) {
            throw new SrvExecutionException(ExecutionStatus.DECLINED, ResponseCode.INTERNAL_PERSISTENCY_ERROR, e);
        }
    }

    @Transactional
    public void delete(Device device) throws SrvExecutionException {
        try {
            eventRepo.deleteByComponent(device);
            deviceRepo.delete(device);
        } catch (Exception e) {
            throw new SrvExecutionException(ExecutionStatus.DECLINED, ResponseCode.INTERNAL_PERSISTENCY_ERROR, e);
        }
    }

}
