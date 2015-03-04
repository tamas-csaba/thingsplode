/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.core.domain.protocol.cmdrequest;

import java.util.Collection;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import org.thingsplode.core.domain.entities.Configuration;
import org.thingsplode.core.domain.protocol.AbstractCmdReq;

/**
 *
 * @author tamas.csaba@gmail.com
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SyncCmd")
public class SynchronizationCmd extends AbstractCmdReq {

    
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
