/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.core.protocol.response;

import java.util.Collection;
import org.thingsplode.core.entities.Configuration;
import org.thingsplode.core.protocol.Response;

/**
 *
 * @author tamas.csaba@gmail.com
 */
public class HeartbeatResponse extends ConfigurationCarrierResponse<HeartbeatResponse> {
    private Collection<Configuration> configuration;
    
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
    
}
