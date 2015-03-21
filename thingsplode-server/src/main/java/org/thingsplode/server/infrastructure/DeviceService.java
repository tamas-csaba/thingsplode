/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.server.infrastructure;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thingsplode.core.EnabledState;
import org.thingsplode.core.StatusInfo;
import org.thingsplode.core.entities.Capability;
import org.thingsplode.core.entities.Component;
import org.thingsplode.core.entities.Configuration;
import org.thingsplode.core.entities.Device;
import org.thingsplode.core.entities.Model;
import org.thingsplode.core.entities.Treshold;
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

    private static final Logger logger = Logger.getLogger(DeviceService.class);
    @Autowired
    private DeviceRepository deviceRepo;
    @Autowired
    private ModelRepository modelRepo;
    @Autowired
    private EventRepository eventRepo;
    @Value("${autoregistration.enabled:false}")
    private boolean enableAutoRegistration;
    @Value("${overwrite.serialnumber.ondevices.enabled:false}")
    private boolean overwriteSerialNumberOnDevices;
    @Value("${overwrite.serialnumber.oncomponents.enabled:true}")
    private boolean overwriteSerialNumberOnComponents;
    @Value("${repo.query.pagesize:100}")
    private int pageSize;

    /**
     *
     * @param deviceID
     * @param state
     * @return true if the device was found and state is changed
     */
    @Transactional
    public boolean setDeviceEnabledState(String deviceID, EnabledState state) {
        Device d = deviceRepo.findByIdentification(deviceID);
        if (d != null) {
            d.setEnabledState(state);
            deviceRepo.save(d);
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public void setConfigurationForDevices(List<Configuration> configurations) {
        int pageIndex = 0;
        Page<Device> devicePage;
        do {
            devicePage = deviceRepo.findAll(new PageRequest(pageIndex, pageSize));
            if (devicePage != null && devicePage.getSize() > 0) {
                devicePage.getContent().stream().forEach((d) -> {
                    d.getConfiguration().clear();
                });
                deviceRepo.save(devicePage.getContent());
                deviceRepo.flush();
                devicePage.getContent().stream().forEach((d) -> {
                    d.getConfiguration().addAll(configurations);
                });
                deviceRepo.save(devicePage.getContent());
            }
            pageIndex += 1;
        } while (devicePage != null && (!devicePage.isLast() || devicePage.getNumber() > 0));
    }

    /**
     * Registers a new device or update with the newest status
     *
     * @param newDevice
     * @return saved device
     * @throws SrvExecutionException
     */
    @Transactional
    public Device registerOrUpdate(Device newDevice) throws SrvExecutionException {
        Device existingDevice;
        if (newDevice == null) {
            throw new SrvExecutionException(ExecutionStatus.DECLINED, ResponseCode.VALIDATION_ERROR, "The device cannot be null.");
        }
        if (newDevice.getIdentification().isEmpty()) {
            throw new SrvExecutionException(ExecutionStatus.DECLINED, ResponseCode.VALIDATION_ERROR, "The device Id cannot be null.");
        }
        if (newDevice.getId() != null) {
            existingDevice = deviceRepo.findOne(newDevice.getId());
            if (existingDevice != null && existingDevice.getIdentification() != null && !existingDevice.getIdentification().equalsIgnoreCase(newDevice.getIdentification())) {
                throw new SrvExecutionException(ExecutionStatus.DECLINED, ResponseCode.VALIDATION_ERROR, "Device with resgistration id [" + existingDevice.getId() + "] is already registered under a different device identification.");
            }
        } else {
            existingDevice = deviceRepo.findByIdentification(newDevice.getIdentification());
        }

        if (existingDevice == null && !isEnableAutoRegistration()) {
            throw new SrvExecutionException(ExecutionStatus.DECLINED, ResponseCode.PERMISSION_DENIED, "The device is not activated and auto registration is not enabled.");
        } else if (existingDevice == null && isEnableAutoRegistration()) {
            try {
                attachModel(newDevice);
                setDeviceFieldsOnRegistration(newDevice);
                Device d = deviceRepo.save(newDevice);
                initializeComponents(d);
                return d;
            } catch (Exception e) {
                logger.error("cannot register or update device, due to: " + e.getMessage(), e);
                throw new SrvExecutionException(ExecutionStatus.DECLINED, ResponseCode.INTERNAL_PERSISTENCY_ERROR, e.getMessage(), e);
            }
        } else if (existingDevice != null && existingDevice.getEnabledState() == EnabledState.DISABLED) {
            throw new SrvExecutionException(ExecutionStatus.DECLINED, ResponseCode.PERMISSION_DENIED, "The device is in DISABLED state.");
        } else if (existingDevice != null && (existingDevice.getEnabledState() == EnabledState.UNINITIALIZED || existingDevice.getEnabledState() == EnabledState.ENABLED)) {
            try {
                setDeviceFieldsOnRegistration(existingDevice);
                mergeComponents(newDevice, existingDevice);
                mergeDeviceFields(newDevice, existingDevice);
                Device d = deviceRepo.save(existingDevice);
                initializeComponents(d);
                return d;
            } catch (Exception e) {
                logger.error("cannot register or update device, due to: " + e.getMessage(), e);
                throw new SrvExecutionException(ExecutionStatus.DECLINED, ResponseCode.INTERNAL_PERSISTENCY_ERROR, e.getMessage(), e);
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
        if (source.getHostAddress() != null) {
            destination.setHostAddress(source.getHostAddress());
        }
        if (source.getLocation() != null) {
            destination.setLocation(source.getLocation());
        }
    }

    private void mergeComponents(Component<?> source, Component<?> destination) {
        mergeComponentFields(source, destination);
        if (source.getComponents() != null) {
            source.getComponents().forEach(sc -> {
                Component<?> existingComponent = destination.getComponentByName(sc.getIdentification());
                if (existingComponent == null) {
                    destination.addComponents(sc);
                } else {
                    mergeComponentFields(sc, existingComponent);
                    if (sc.getComponents() != null || !sc.getComponents().isEmpty()) {
                        ((Component<?>) sc).getComponents().forEach(ssc -> {
                            Component<?> existingSubComponent = existingComponent.getComponentByName(ssc.getIdentification());
                            if (existingSubComponent == null) {
                                existingComponent.addComponents(ssc);
                            } else {
                                mergeComponents(ssc, existingSubComponent);
                            }
                        });
                    }
                }
            });
        }
    }

    private void mergeComponentFields(Component<?> source, Component<?> destination) {
        destination.setStatus(source.getStatus());
        source.getConfiguration().stream().filter(sc -> (destination.getConfigurationByKey(sc.getKey()) == null)).forEach(newSC -> {
            newSC.setSynced();
            destination.addOrUpdateConfigurations(newSC);
        });

        //add capabilities which are new and remove the ones which are not anymore on the message
        if (destination.getCapabilities() != null) {
            destination.getCapabilities().stream().forEach(dc -> {
                Capability sourceCapability = source.getCapabilityByName(dc.getName());
                if (sourceCapability != null) {
                    dc.setActive(sourceCapability.isActive());
                    dc.setType(sourceCapability.getType());
                }
            });
        }
        if (source.getCapabilities() != null) {
            source.getCapabilities().stream().filter(c -> (destination.getCapabilityByName(c.getName()) == null)).forEach(c -> destination.addCapabilities(c));
        }
        List<Capability> deletables = destination.getCapabilities().stream().
                filter(c -> (source.getCapabilityByName(c.getName()) == null)).
                collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        destination.removeCapabilities(deletables.toArray(new Capability[]{}));

        if (source.getTresholds() != null) {
            List<Treshold> newTresholds = source.getTresholds().stream().filter(st -> destination.getTresholdByName(st.getName()) == null).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
            destination.addTresholds(newTresholds.toArray(new Treshold[]{}));
        }
        if ((destination instanceof Device) && overwriteSerialNumberOnDevices) {
            destination.setPartNumber(source.getPartNumber());
            destination.setSerialNumber(source.getSerialNumber());
        } else if (!(destination instanceof Device) && overwriteSerialNumberOnComponents) {
            destination.setPartNumber(source.getPartNumber());
            destination.setSerialNumber(source.getSerialNumber());
        }
        if (source.getStatus() != null) {
            destination.setStatus(source.getStatus());
        }

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

    @Transactional
    public Device getInitializedDeviceByDbId(Long id) {
        //todo: see todo at getInitializedDeviceByDeviceId
        Device d = deviceRepo.findOne(id);
        initializeComponents(d);
        return d;
    }

    @Transactional(readOnly = true)
    public Device getInitializedDeviceByDeviceId(String deviceId) {
        //todo: create a optimal fetched query
        /**
         * @Query("SELECT p FROM Person p JOIN FETCH p.roles WHERE p.id =
         * (:id)") public Person findByIdAndFetchRolesEagerly(@Param("id") Long
         * id ---- or use transaction template from where is it needed
         * TransactionTemplate transactionTemplate = new
         * TransactionTemplate(transactionManager);
         * transactionTemplate.execute(new TransactionCallbackWithoutResult() {
         * @Override protected void
         * doInTransactionWithoutResult(TransactionStatus status) { ...
         *
         * });
         */
        Device d = deviceRepo.findByIdentification(deviceId);
        initializeComponents(d);
        return d;
    }

    private void initializeComponents(Component c) {
        if (c != null) {
            if (c.getCapabilities() != null) {
                c.getCapabilities().size();
            }
            if (c.getConfiguration() != null) {
                c.getConfiguration().size();
            }
            if (c.getTresholds() != null) {
                c.getTresholds().size();
            }
            if (c.getComponents() != null) {
                c.getComponents().size();
                c.getComponents().forEach(sc -> initializeComponents((Component) sc));
            }
        }
    }

    /**
     * @return the enableAutoRegistration
     */
    public boolean isEnableAutoRegistration() {
        return enableAutoRegistration;
    }

    /**
     * @param enableAutoRegistration the enableAutoRegistration to set
     */
    public void setEnableAutoRegistration(boolean enableAutoRegistration) {
        this.enableAutoRegistration = enableAutoRegistration;
    }

    /**
     * @param overwriteSerialNumberOnDevices the overwriteSerialNumberOnDevices
     * to set
     */
    public void setOverwriteSerialNumberOnDevices(boolean overwriteSerialNumberOnDevices) {
        this.overwriteSerialNumberOnDevices = overwriteSerialNumberOnDevices;
    }

    /**
     * @param overwriteSerialNumberOnComponents the
     * overwriteSerialNumberOnComponents to set
     */
    public void setOverwriteSerialNumberOnComponents(boolean overwriteSerialNumberOnComponents) {
        this.overwriteSerialNumberOnComponents = overwriteSerialNumberOnComponents;
    }

}
