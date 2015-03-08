/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.server.infrastructure;

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
    public Device registerOrUpdate(Device device) throws SrvExecutionException {
        if (device == null) {
            throw new SrvExecutionException(ExecutionStatus.DECLINED, ResponseCode.VALIDATION_ERROR, "The device cannot be null.");
        }
        if (device.getDeviceId().isEmpty()) {
            throw new SrvExecutionException(ExecutionStatus.DECLINED, ResponseCode.VALIDATION_ERROR, "The device Id cannot be null.");
        }
        if (device.getId() != null) {
            Device existingDevice = deviceRepo.findOne(device.getId());
            if (existingDevice != null && existingDevice.getDeviceId() != null && !existingDevice.getDeviceId().equalsIgnoreCase(device.getDeviceId())) {
                throw new SrvExecutionException(ExecutionStatus.DECLINED, ResponseCode.VALIDATION_ERROR, "Device with resgistration id [" + existingDevice.getId() + "] is already registered under a different device identification.");
            }
        } else {
            Device existingDevice = deviceRepo.findBydeviceId(device.getDeviceId());
            if (existingDevice != null) {
                device.setId(existingDevice.getId());
            }
        }

        try {
            Device d = deviceRepo.save(device);
            return d;
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
