/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.core.domain.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import org.thingsplode.core.domain.AbstractComponent;
import org.thingsplode.core.domain.EnabledState;
import org.thingsplode.core.domain.StatusInfo;

/**
 *
 * @author tamas.csaba@gmail.com
 */
@Entity
public class Component extends AbstractComponent {

    private String name;
    private Type type;
    private Collection<ComponentEvent> eventLog;

    /**
     * @return the name
     */
    @Basic(optional = false)
    @Column()
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
    @Column(nullable = false)
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
     * @return the eventLog
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = ComponentEvent.JOIN_COLUMN)
    public Collection<ComponentEvent> getEventLog() {
        return eventLog;
    }

    /**
     * @param eventLog the eventLog to set
     */
    public void setEventLog(Collection<ComponentEvent> eventLog) {
        this.eventLog = eventLog;
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
        this.setEnabledState(state);
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
        this.initializeComponents();
        Collections.addAll(this.getComponents(), componentArray);
        return this;
    }

    public Component addCapabilities(Capability... capabilities) {
        this.initializeCapabilities();
        Collections.addAll(this.getCapabilities(), capabilities);
        return this;
    }

    private void decorateEvents(ComponentEvent... evts) {
        for (ComponentEvent evt : evts) {
            evt.setComponent(this);
        }
    }

    public Component addEvents(ComponentEvent... events) {
        this.initializeEventLog();
        decorateEvents(events);
        Collections.addAll(this.eventLog, events);
        return this;
    }

    public Component addConfigurations(Configuration... configs) {
        this.initializeConfiguration();
        Collections.addAll(this.getConfiguration(), configs);
        return this;
    }

    public void initializeEventLog() {
        if (this.eventLog == null) {
            this.eventLog = new ArrayList<>();
        }
    }
}
