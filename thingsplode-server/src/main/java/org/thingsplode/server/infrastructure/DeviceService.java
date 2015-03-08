/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.server.infrastructure;

import java.util.Calendar;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thingsplode.core.EnabledState;
import org.thingsplode.core.StatusInfo;
import org.thingsplode.core.entities.Configuration;
import org.thingsplode.core.entities.Device;
import org.thingsplode.core.entities.Model;
import org.thingsplode.core.exceptions.SrvExecutionException;
import org.thingsplode.core.protocol.ExecutionStatus;
import org.thingsplode.core.protocol.ResponseCode;
import org.thingsplode.server.repositories.DeviceRepository;
import org.thingsplode.server.repositories.EventRepository;
import org.thingsplode.server.repositories.ModelRepository;

/**
 *
 * @author tamas.csaba@gmail.com
 */
@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepo;
    @Autowired
    private ModelRepository modelRepo;
    @Autowired
    private EventRepository eventRepo;
    @Value("${autoregistration.enabled:false}")
    private boolean enableAutoRegistration;

    /**
     * Registers a new device or update with the newest status
     *
     * @param device
     * @return saved device
     * @throws SrvExecutionException
     */
    @Transactional
    public Device registerOrUpdate(Device device) throws SrvExecutionException {
        Device existingDevice;
        if (device == null) {
            throw new SrvExecutionException(ExecutionStatus.DECLINED, ResponseCode.VALIDATION_ERROR, "The device cannot be null.");
        }
        if (device.getDeviceId().isEmpty()) {
            throw new SrvExecutionException(ExecutionStatus.DECLINED, ResponseCode.VALIDATION_ERROR, "The device Id cannot be null.");
        }
        if (device.getId() != null) {
            existingDevice = deviceRepo.findOne(device.getId());
            if (existingDevice != null && existingDevice.getDeviceId() != null && !existingDevice.getDeviceId().equalsIgnoreCase(device.getDeviceId())) {
                throw new SrvExecutionException(ExecutionStatus.DECLINED, ResponseCode.VALIDATION_ERROR, "Device with resgistration id [" + existingDevice.getId() + "] is already registered under a different device identification.");
            }
        } else {
            existingDevice = deviceRepo.findBydeviceId(device.getDeviceId());
        }

        if (existingDevice == null && !enableAutoRegistration) {
            throw new SrvExecutionException(ExecutionStatus.DECLINED, ResponseCode.PERMISSION_DENIED, "The device is not activated and auto registration is not enabled.");
        } else if (existingDevice == null && enableAutoRegistration) {
            try {
                attachModel(device);
                setDeviceFieldsOnRegistration(device);
                Device d = deviceRepo.save(device);
                return d;
            } catch (Exception e) {
                throw new SrvExecutionException(ExecutionStatus.DECLINED, ResponseCode.INTERNAL_PERSISTENCY_ERROR, e);
            }
        } else if (existingDevice != null && existingDevice.getEnabledState() == EnabledState.DISABLED) {
            throw new SrvExecutionException(ExecutionStatus.DECLINED, ResponseCode.PERMISSION_DENIED, "The device is in DISABLED state.");
        } else if (existingDevice != null && (existingDevice.getEnabledState() == EnabledState.UNINITIALIZED || existingDevice.getEnabledState() == EnabledState.ENABLED)) {
            try {

                setDeviceFieldsOnRegistration(existingDevice);

            } catch (Exception e) {
                throw new SrvExecutionException(ExecutionStatus.DECLINED, ResponseCode.INTERNAL_PERSISTENCY_ERROR, e);
            }
        }
        return null;
    }

    private void attachModel(Device device) {
        Model model = modelRepo.findByManufacturerAndTypeAndVersion(device.getModel().getManufacturer(), device.getModel().getType(), device.getModel().getVersion());
        if (model != null) {
            device.setModel(model);
        }
    }

    private void mergeDeviceFields(Device source, Device destination) {
        destination.setStatus(source.getStatus());
        //*****
        source.getConfiguration().stream().filter(s -> (destination.getConfigurationByKey(s.getKey()) == null)).forEach(s -> {
            s.setSynced();
            destination.addConfigurations(s);
        });
        
        source.getCapabilities().stream().filter(c -> (destination.getCapabilityByName(c.getName()) == null)).forEach(c -> destination.addCapabilities(c));
    }

    private void setDeviceFieldsOnRegistration(Device device) {
        Calendar now = Calendar.getInstance();
        device.setEnabledState(EnabledState.ENABLED);
        device.setLastHeartBeat(now);
        if (device.getStartupDate() == null) {
            device.setStartupDate(now);
        }
        if (device.getStatus() == null) {
            device.setStatus(StatusInfo.ONLINE);
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
