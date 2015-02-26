/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.core.domain.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import org.thingsplode.core.domain.EnabledState;
import org.thingsplode.core.domain.StatusInfo;

/**
 *
 * @author tam
 */
@Entity
public class Component extends Persistable<Long> {

    private String name;
    private Type type;
    private EnabledState enabledState;
    private StatusInfo status;
    private Model model;
    private Collection<Component> subComponents;
    private Collection<Capability> capabilities;
    private Collection<Event> eventLog;
    private Collection<Configuration> configuration;


    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the type
     */
    @Enumerated(EnumType.STRING)
    public Type getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(Type type) {
        this.type = type;
    }

    /**
     * @return the enabledState
     */
    @Enumerated(EnumType.STRING)
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
    @Embedded
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
    public Collection<Component> getSubComponents() {
        return subComponents;
    }

    /**
     * @param subComponents the subComponents to set
     */
    public void setSubComponents(Collection<Component> subComponents) {
        this.subComponents = subComponents;
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
     * @return the eventLog
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "COMP_ID")
    public Collection<Event> getEventLog() {
        return eventLog;
    }

    /**
     * @param eventLog the eventLog to set
     */
    public void setEventLog(Collection<Event> eventLog) {
        this.eventLog = eventLog;
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
     * @param configuration the configuration to set
     */
    public void setConfiguration(Collection<Configuration> configuration) {
        this.configuration = configuration;
    }

    static public enum Type {
        HARDWARE,
        SOFTWARE;
    }
    
    public static Component create() {
        return new Component();
    }

    public static Component create(String name, Type type) {
        Component comp = Component.create();
        comp.setName(name);
        comp.setType(type);
        return comp;
    }

    public static Component create(String name, Type type, EnabledState state) {
        Component comp = Component.create(name, type);
        comp.setEnabledState(state);
        return comp;
    }

    public Component putEnabledState(EnabledState state) {
        this.setEnabledState(enabledState);
        return this;
    }

    public Component putStatusInfo(StatusInfo status) {
        this.setStatus(status);
        return this;
    }

    public Component putModel(Model model) {
        this.setModel(model);
        return this;
    }

    public Component addComponents(Component... componentArray) {
        if (subComponents == null) {
            this.subComponents = new ArrayList<>();
        }
        Collections.addAll(this.subComponents, componentArray);
        return this;
    }

    public Component addCapabilities(Capability... capabilities) {
        if (this.capabilities == null) {
            this.capabilities = new ArrayList<>();
        }
        Collections.addAll(this.capabilities, capabilities);
        return this;
    }

    public Component addEvents(Event... events) {
        if (this.eventLog == null) {
            this.eventLog = new ArrayList<>();
        }
        Collections.addAll(this.eventLog, events);
        return this;
    }

    public Component addConfigurations(Configuration... configs) {
        if (this.configuration == null) {
            this.configuration = new ArrayList<>();
        }
        Collections.addAll(this.configuration, configs);
        return this;
    }
}
