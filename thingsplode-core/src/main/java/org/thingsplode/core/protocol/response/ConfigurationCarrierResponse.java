/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.core.protocol.response;

import java.util.Collection;
import java.util.HashMap;
import org.thingsplode.core.entities.Component;
import org.thingsplode.core.entities.Configuration;
import org.thingsplode.core.entities.Device;
import org.thingsplode.core.entities.Treshold;
import org.thingsplode.core.protocol.ExecutionStatus;
import org.thingsplode.core.protocol.Response;
import org.thingsplode.core.protocol.ResponseCode;

/**
 *
 * @author Csaba Tamas
 * @param <T>
 */
public abstract class ConfigurationCarrierResponse<T extends ConfigurationCarrierResponse<T>> extends Response<T> {
    
    private HashMap<String, Collection<Configuration>> configuration; //component name / configurations
    private HashMap<String, Collection<Treshold>> tresholds;    
    
    public ConfigurationCarrierResponse() {
    }
    
    public ConfigurationCarrierResponse(ExecutionStatus requestStatus, ResponseCode responseCode) {
        super(requestStatus, responseCode);
    }
    
    public ConfigurationCarrierResponse(String responseCorrelationID, ExecutionStatus requestStatus, ResponseCode responseCode) {
        super(responseCorrelationID, requestStatus, responseCode);
    }
    
    public ConfigurationCarrierResponse(String responseCorrelationID, ExecutionStatus requestStatus, ResponseCode responseCode, String resultMessage) {
        super(responseCorrelationID, requestStatus, responseCode, resultMessage);
    }
    
    public HashMap<String, Collection<Configuration>> getConfiguration() {
        return configuration;
    }
    
    public void setConfiguration(HashMap<String, Collection<Configuration>> configuration) {
        this.configuration = configuration;
    }
    
    public HashMap<String, Collection<Treshold>> getTresholds() {
        return tresholds;
    }
    
    public void setTresholds(HashMap<String, Collection<Treshold>> tresholds) {
        this.tresholds = tresholds;
    }
    
    public T addTresholdsFromDevice(Device d) {
        if (d != null) {
            initializeTresholds();
            addTresholdsFromComponent(d);
        }
        return (T) this;
    }
    
    private void addTresholdsFromComponent(Component<?> comp) {
        if (comp != null) {
            if (comp.getTresholds() != null && !comp.getTresholds().isEmpty()) {
                this.tresholds.put(comp.getIdentification(), comp.getTresholds());
            }
            if (comp.getComponents() != null && !comp.getComponents().isEmpty()) {
                comp.getComponents().forEach(sc -> addTresholdsFromComponent(sc));
            }
        }
    }
    
    public T addConfigurationsFromDevice(Device d) {
        if (d != null) {
            initializeConfiguration();
            addConfigurationFromComponent(d);
        }
        return (T) this;
    }
    
    private void addConfigurationFromComponent(Component<?> comp) {
        if (comp != null) {
            if (comp.getConfiguration() != null && !comp.getConfiguration().isEmpty()) {
                this.configuration.put(comp.getIdentification(), comp.getConfiguration());
            }
            if (comp.getComponents() != null && !comp.getComponents().isEmpty()) {
                comp.getComponents().forEach(sc -> addConfigurationFromComponent(sc));
            }
        }
    }
    
    public T addConfigurations(HashMap<String, Collection<Configuration>> cfgs) {
        initializeConfiguration();
        this.getConfiguration().putAll(cfgs);
        return (T) this;
    }
    
    private void initializeTresholds() {
        if (this.tresholds == null) {
            this.tresholds = new HashMap<>();
        }
    }
    
    private void initializeConfiguration() {
        if (this.configuration == null) {
            this.configuration = new HashMap<>();
        }
    }
    
    @Override
    public String toString() {
        return "ConfigurationCarrierResponse{ " + super.toString() + " configuration=" + configuration + '}';
    }
}
