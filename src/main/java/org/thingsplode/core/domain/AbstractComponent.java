/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.core.domain;

import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;
import org.thingsplode.core.domain.entities.Capability;
import org.thingsplode.core.domain.entities.Configuration;
import org.thingsplode.core.domain.entities.Model;
import org.thingsplode.core.domain.entities.Persistable;
import org.thingsplode.core.domain.entities.Component;

/**
 *
 * @author tamas.csaba@gmail.com
 */
@MappedSuperclass
@XmlRootElement
public abstract class AbstractComponent extends Persistable<Long> {

    private EnabledState enabledState;
    private StatusInfo status;
    private Model model;
    private Collection<Component> components;
    private Collection<Capability> capabilities;
    private Collection<Configuration> configuration;
    private String serialNumber;
    private String partNumber;

    /**
     * @return the enabledState
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public EnabledState getEnabledState() {
        return enabledState;
    }

    /**
     * @param enabledState the enabledState to set
     */
    public void setEnabledState(EnabledState enabledState) {
        this.enabledState = enabledState;
    }

    /**
     * @return the status
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public StatusInfo getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(StatusInfo status) {
        this.status = status;
    }

    /**
     * @return the model
     */
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public Model getModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(Model model) {
        this.model = model;
    }

    /**
     * @return the subComponents
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "ROOT_COMP_ID")
    public Collection<Component> getComponents() {
        return components;
    }

    /**
     * @param subComponents the subComponents to set
     */
    public void setComponents(Collection<Component> subComponents) {
        this.components = subComponents;
    }

    /**
     * @return the capabilities
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "COMP_ID")
    public Collection<Capability> getCapabilities() {
        return capabilities;
    }

    /**
     * @param capabilities the capabilities to set
     */
    public void setCapabilities(Collection<Capability> capabilities) {
        this.capabilities = capabilities;
    }

    /**
     * @return the configuration
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "COMP_ID")
    public Collection<Configuration> getConfiguration() {
        return configuration;
    }
    
    /**
     * @return the serialNumber
     */
    @Basic
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     * @param serialNumber the serialNumber to set
     */
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    /**
     * @return the partNumber
     */
    @Basic
    public String getPartNumber() {
        return partNumber;
    }

    /**
     * @param partNumber the partNumber to set
     */
    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    /**
     * @param configuration the configuration to set
     */
    public void setConfiguration(Collection<Configuration> configuration) {
        this.configuration = configuration;
    }

    public void initializeComponents() {
        if (this.components == null) {
            this.components = new ArrayList<>();
        }
    }

    public void initializeCapabilities() {
        if (this.capabilities == null) {
            this.capabilities = new ArrayList<>();
        }
    }

    public void initializeConfiguration() {
        if (this.configuration == null) {
            this.configuration = new ArrayList<>();
        }
    }
    
}
