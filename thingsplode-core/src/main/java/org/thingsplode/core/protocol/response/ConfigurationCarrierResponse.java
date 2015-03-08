/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.core.protocol.response;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import org.thingsplode.core.entities.Configuration;
import org.thingsplode.core.protocol.ExecutionStatus;
import org.thingsplode.core.protocol.Response;
import org.thingsplode.core.protocol.ResponseCode;

/**
 *
 * @author tamas.csaba@gmail.com
 * @param <T>
 */
public abstract class ConfigurationCarrierResponse<T extends ConfigurationCarrierResponse<T>> extends Response<T> {

    private Collection<Configuration> configuration;

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
    
    /**
     * @return the configuration
     */
    public Collection<Configuration> getConfiguration() {
        return configuration;
    }

    /**
     * @param configuration the configuration to set
     */
    public void setConfiguration(Collection<Configuration> configuration) {
        this.configuration = configuration;
    }

    public T addConfigurations(Configuration... cfgs) {
        initializeConfiguration();
        Collections.addAll(this.getConfiguration(), cfgs);
        return (T)this;
    }

    private void initializeConfiguration() {
        if (configuration == null) {
            this.configuration = new ArrayList<>();
        }
    }

    @Override
    public String toString() {
        return "ConfigurationCarrierResponse{ " + super.toString() + " configuration=" + configuration + '}';
    }
}
