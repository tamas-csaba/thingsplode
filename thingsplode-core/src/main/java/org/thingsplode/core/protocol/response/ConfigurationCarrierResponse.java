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
import org.thingsplode.core.protocol.ExecutionStatus;
import org.thingsplode.core.protocol.Response;
import org.thingsplode.core.protocol.ResponseCode;

/**
 *
 * @author tamas.csaba@gmail.com
 * @param <T>
 */
public abstract class ConfigurationCarrierResponse<T extends ConfigurationCarrierResponse<T>> extends Response<T> {

    private HashMap<String, Collection<Configuration>> configuration; //component name / configurations

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
                this.configuration.put(comp.getName(), comp.getConfiguration());
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

    private void initializeConfiguration() {
        if (configuration == null) {
            this.configuration = new HashMap<>();
        }
    }

    @Override
    public String toString() {
        return "ConfigurationCarrierResponse{ " + super.toString() + " configuration=" + configuration + '}';
    }
}
