/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.core.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlTransient;
import org.thingsplode.core.EnabledState;
import org.thingsplode.core.StatusInfo;

/**
 *
 * @author tamas.csaba@gmail.com
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = Component.DISCRIMINATOR, discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue(value = Component.MAIN_TYPE)

@Table(
        name = Component.TABLE_NAME,
        uniqueConstraints = @UniqueConstraint(columnNames = {"name", Component.ROOT_COMP_REF})
)
public class Component extends Persistable<Long> {

    /**
     * COMPONENT
     */
    @XmlTransient
    public final static String MAIN_TYPE = "COMPONENT";
    @XmlTransient
    public final static String DISCRIMINATOR = "MAIN_TYPE";
    @XmlTransient
    public final static String TABLE_NAME = "COMPONENT";
    @XmlTransient
    public final static String COMP_REF = "COMP_ID";
    @XmlTransient
    public final static String ROOT_COMP_REF = "ROOT_COMP_ID";
    private String name;
    private Type type;
    private EnabledState enabledState;
    private StatusInfo status;
    private Model model;
    private String serialNumber;
    private String partNumber;
    private Collection<Component> components;
    private Collection<Capability> capabilities;
    private Collection<Configuration> configuration;
    private Collection<Treshold> tresholds;

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

//    /**
//     * @return the eventLog
//     */
//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name = ComponentEvent.JOIN_COLUMN)
//    public Collection<ComponentEvent> getEventLog() {
//        return eventLog;
//    }
//
//    /**
//     * @param eventLog the eventLog to set
//     */
//    public void setEventLog(Collection<ComponentEvent> eventLog) {
//        this.eventLog = eventLog;
//    }
    /**
     * @return the subComponents
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = Component.ROOT_COMP_REF)
    public Collection<Component> getComponents() {
        return components;
    }

    /**
     * @param subComponents the subComponents to set
     */
    public void setComponents(Collection<Component> subComponents) {
        this.components = subComponents;
    }

    public void setTresholds(Collection<Treshold> tresholds) {
        setTresholdsScope(tresholds);
        this.tresholds = tresholds;
    }

    protected void setTresholdsScope(Collection<Treshold> tresholds) {
        if (tresholds != null) {
            for (Treshold t : tresholds) {
                t.setScope(Treshold.Scope.COMPONENT);
            }
        }
    }

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
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
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
     * @return the serialNumber
     */
    @Basic
    @Column(unique = true)
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

    protected void initializeCapabilities() {
        if (this.getCapabilities() == null) {
            this.setCapabilities(new ArrayList<Capability>());
        }
    }

    public void initializeConfiguration() {
        if (this.getConfiguration() == null) {
            this.configuration = new ArrayList<>();
        }
    }

    public void initializeTresholds() {
        if (this.getTresholds() == null) {
            this.tresholds = new ArrayList<>();
        }
    }

    /**
     * @return the capabilities
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = Component.COMP_REF)
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
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = Component.COMP_REF)
    public Collection<Configuration> getConfiguration() {
        return configuration;
    }

    public Configuration getConfigurationByKey(String searchKey) {
        return configuration.stream().filter(p -> p.getKey().equalsIgnoreCase(searchKey)).findFirst().orElse(null);
    }

    public Capability getCapabilityByName(String capabilityName) {
        return capabilities.stream().filter(c -> c.getName().equalsIgnoreCase(capabilityName)).findFirst().orElse(null);
    }

    public Component getSubComponentByName(String componentName) {
        return components.stream().filter(c -> c.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    /**
     * @return the tresholds
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = Component.COMP_REF)
    public Collection<Treshold> getTresholds() {
        return tresholds;
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

    public Component putType(Type type) {
        this.setType(type);
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

    public Component addSubComponents(Component... componentArray) {
        this.initializeComponents();
        Collections.addAll(this.getComponents(), componentArray);
        return this;
    }

    public Component addCapabilities(Capability... capabilities) {
        this.initializeCapabilities();
        Collections.addAll(this.getCapabilities(), capabilities);
        return this;
    }

    public Component addTresholds(Treshold... tresholds) {
        this.initializeTresholds();
        ArrayList<Treshold> trshs = new ArrayList<>();
        Collections.addAll(trshs, tresholds);
        setTresholdsScope(trshs);
        getTresholds().addAll(trshs);
        return this;
    }

//    private void decorateEvents(ComponentEvent... evts) {
//        for (ComponentEvent evt : evts) {
//            evt.setComponent(this);
//        }
//    }
//    public Component addEvents(ComponentEvent... events) {
//        this.initializeEventLog();
//        decorateEvents(events);
//        Collections.addAll(this.eventLog, events);
//        return this;
//    }
    public Component addConfigurations(Configuration... configs) {
        this.initializeConfiguration();
        Collections.addAll(this.getConfiguration(), configs);
        return this;
    }

//    public void initializeEventLog() {
//        if (this.eventLog == null) {
//            this.eventLog = new ArrayList<>();
//        }
//    }
    protected void initializeComponents() {
        if (this.components == null) {
            this.components = new ArrayList<>();
        }
    }
}
