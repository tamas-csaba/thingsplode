/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.core.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlTransient;
import org.thingsplode.core.EnabledState;
import org.thingsplode.core.StatusInfo;

/**
 *
 * @author tamas.csaba@gmail.com
 * @param <T>
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = Component.DISCRIMINATOR, discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue(value = Component.MAIN_TYPE)

//@Table(
//        name = Component.TABLE_NAME,
//        uniqueConstraints = @UniqueConstraint(columnNames = {"name", Component.ROOT_COMP_REF})
//)
//todo: serial number also should be unique
public class Component<T extends Component<?>> extends Persistable<Long> {

    @XmlTransient
    public final static String MAIN_TYPE = "COMPONENTS";
    @XmlTransient
    public final static String DISCRIMINATOR = "MAIN_TYPE";
    @XmlTransient
    public final static String TABLE_NAME = "COMPONENT";
    @XmlTransient
    public final static String COMP_REF = "COMP_ID";
    @XmlTransient
    public final static String ROOT_COMP_REF = "ROOT_COMP_ID";
    @NotNull
    private String name;
    @NotNull
    private Type type;
    @NotNull
    private EnabledState enabledState;
    @NotNull
    private StatusInfo status;
    private Model model;
    private String serialNumber;
    private String partNumber;
    private Collection<Component> components;
    private Collection<Capability> capabilities;
    private Collection<Configuration> configuration;
    private Collection<Treshold> tresholds;

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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)//orphanRemoval = true
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
            tresholds.stream().forEach((t) -> {
                t.setScope(Treshold.Scope.COMPONENT);
            });
        }
    }

    /**
     * @param configuration the configuration to set
     */
    void setConfiguration(Collection<Configuration> configuration) {
        this.configuration = configuration;
    }

    protected void initializeCapabilities() {
        if (this.getCapabilities() == null) {
            this.setCapabilities(new ArrayList<>());
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
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
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
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = Component.COMP_REF)
    public Collection<Configuration> getConfiguration() {
        return configuration;
    }

    public Configuration getConfigurationByKey(String searchKey) {
        if (configuration == null || configuration.isEmpty()) {
            return null;
        }
        return configuration.stream().filter(p -> p.getKey().equalsIgnoreCase(searchKey)).findFirst().orElse(null);
    }

    public Capability getCapabilityByName(String capabilityName) {
        if (capabilities == null || capabilities.isEmpty()) {
            return null;
        }
        return capabilities.stream().filter(c -> c.getName().equalsIgnoreCase(capabilityName)).findFirst().orElse(null);
    }

    public Component getComponentByName(String componentName) {
        if (components == null || components.isEmpty()) {
            return null;
        }
        return components.stream().filter(c -> c.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public Treshold getTresholdByName(String tresholdName) {
        if (tresholds == null || tresholds.isEmpty()) {
            return null;
        }
        return tresholds.stream().filter(t -> t.getName().equalsIgnoreCase(tresholdName)).findFirst().orElse(null);
    }

    /**
     * @return the tresholds
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = Component.COMP_REF)
    public Collection<Treshold> getTresholds() {
        return tresholds;
    }

    public T addComponents(Component... componentArray) {
        this.initializeComponents();
        Collections.addAll(this.getComponents(), componentArray);
        return (T) this;
    }

    public T addCapabilities(Capability... capabilities) {
        this.initializeCapabilities();
        Collections.addAll(this.getCapabilities(), capabilities);
        return (T) this;
    }

    public T removeTresholds(String... names) {
        if (this.tresholds != null && !this.tresholds.isEmpty()) {
            Arrays.asList(names).stream().forEach(tn -> this.tresholds.removeIf(ot -> ot.getName().equalsIgnoreCase(tn)));
        }
        return (T) this;
    }

    public T removeCapabilities(String... names) {
        if (this.capabilities != null && !this.capabilities.isEmpty()) {
            Arrays.asList(names).stream().forEach(cn -> this.capabilities.removeIf(oc -> oc.getName().equalsIgnoreCase(cn)));
        }
        return (T) this;
    }

    public T removeCapabilities(Capability... caps) {
        if (this.capabilities != null && !this.capabilities.isEmpty()) {
            Arrays.asList(caps).stream().forEach(c -> {
                this.capabilities.removeIf(oc -> oc.getName().equalsIgnoreCase(c.getName()));
            });
        }
        return (T) this;
    }

    public T addTresholds(Treshold... tresholds) {
        this.initializeTresholds();
        ArrayList<Treshold> trshs = new ArrayList<>();
        Collections.addAll(trshs, tresholds);
        setTresholdsScope(trshs);
        getTresholds().addAll(trshs);
        return (T) this;
    }

    public T addOrUpdateTresholds(List<Treshold> trshs) {
        this.initializeTresholds();
        if (!this.tresholds.isEmpty() && trshs != null && !trshs.isEmpty()) {
            List<Treshold> removables = trshs.stream().
                    filter(nt -> this.getTresholdByName(nt.getName()) != null).
                    peek(nt -> this.updateTreshold(nt)).
                    collect(Collectors.toList());
            trshs.removeAll(removables);
            this.getTresholds().addAll(trshs);
        } else if (trshs != null && !trshs.isEmpty()) {
            this.getTresholds().addAll(trshs);
        }
        this.setTresholdsScope(this.getTresholds());
        return (T) this;
    }

    public T setCapabilityAcitvity(String capName, boolean active) {
        if (this.getCapabilities() != null) {
            this.getCapabilities().stream().filter(c -> c.getName().equalsIgnoreCase(capName)).forEach(fc -> fc.setActive(active));
        }
        return (T) this;
    }

    public boolean updateCapability(Capability cap) {
        if (this.getCapabilities() == null || this.getCapabilities().isEmpty()) {
            return false;
        }

        if (cap == null || cap.getName() == null || cap.getName().isEmpty()) {
            return false;
        }

        Capability currentCapability = this.getCapabilityByName(cap.getName());
        if (currentCapability == null) {
            return false;
        } else {
            currentCapability.refreshValues(cap);
            return true;
        }
    }

    public boolean updateTreshold(Treshold treshold) {
        if (this.getTresholds() == null || this.getTresholds().isEmpty()) {
            return false;
        }

        if (treshold == null || treshold.getName() == null || treshold.getName().isEmpty()) {
            return false;
        }

        Treshold currentTreshold = this.getTresholdByName(treshold.getName());
        if (currentTreshold == null) {
            return false;
        } else {
            currentTreshold.refresValues(treshold);
            return true;
        }
    }

    /**
     *
     * @param cfg the config values which need to be updated
     * @return false if configuration identified by key was not set on this
     * component
     */
    public boolean updateConfiguration(Configuration cfg) {
        if (this.getConfiguration() == null || this.getConfiguration().isEmpty()) {
            return false;
        }

        if (cfg != null && cfg.getSyncStatus() == null) {
            cfg.setSyncStatus(Configuration.SyncStatus.NEW);
        }

        if (cfg == null || cfg.getKey() == null || cfg.getKey().isEmpty()) {
            return false;
        }

        Configuration oldConfig = this.getConfigurationByKey(cfg.getKey());
        if (oldConfig == null) {
            return false;
        } else {
            oldConfig.refreshValues(cfg);
            return true;
        }
    }

    /**
     * It will add new configurations and update existing ones based in the key
     * value of the configuration.
     *
     * @param configs the configuration which need to be added or updated
     * @return
     */
    public T addOrUpdateConfigurations(List<Configuration> configs) {
        this.initializeConfiguration();
        if (!this.getConfiguration().isEmpty() && configs != null && !configs.isEmpty()) {
            List<Configuration> removables = configs.stream().
                    filter(ac -> this.getConfigurationByKey(ac.getKey()) != null).
                    peek(ac -> this.updateConfiguration(ac)).
                    collect(Collectors.toList());
            configs.removeAll(removables);
            this.getConfiguration().addAll(configs);
        } else if (configs != null && !configs.isEmpty()) {
            this.getConfiguration().addAll(configs);
        }
        return (T) this;
    }

    public T addOrUpdateConfigurations(Configuration... cfgsAsArray) {
        this.addOrUpdateConfigurations(Arrays.asList(cfgsAsArray));
        return (T) this;
    }

    public T addOrUpdateCapabilies(Capability... caps) {
        this.addOrUpdateCapabilies(Arrays.asList(caps));
        return (T) this;
    }

    public T addOrUpdateCapabilies(List<Capability> caps) {
        this.initializeCapabilities();
        if (this.getCapabilities().size() > 0 && caps != null && !caps.isEmpty()) {
            List<Capability> removables = caps.stream().
                    filter(nc -> this.getCapabilityByName(nc.getName()) != null).
                    peek(nc -> this.updateCapability(nc)).
                    collect(Collectors.toList());
            caps.removeAll(removables);
            this.getCapabilities().addAll(caps);
        } else if (caps != null && !caps.isEmpty()) {
            this.getCapabilities().addAll(caps);
        }

        return (T) this;
    }

    public T removeConfigurations(Configuration... confs) {
        if (this.configuration != null && this.configuration.size() > 0) {
            Arrays.asList(confs).stream().forEach(c -> {
                this.configuration.removeIf(oc -> oc.getKey().equalsIgnoreCase(c.getKey()));
            });
        }
        return (T) this;
    }

    public T removeConfigurations(String... confKeys) {
        if (this.configuration != null && this.configuration.size() > 0) {
            Arrays.asList(confKeys).stream().forEach(s -> {
                this.configuration.removeIf(oc -> oc.getKey().equalsIgnoreCase(s));
            });
        }
        return (T) this;
    }

    protected void initializeComponents() {
        if (this.components == null) {
            this.components = new ArrayList<>();
        }
    }

    static public enum Type {

        HARDWARE,
        SOFTWARE;
    }

    public T putEnabledState(EnabledState state) {
        this.setEnabledState(state);
        return (T) this;
    }

    public T putType(Type type) {
        this.setType(type);
        return (T) this;
    }

    public T putStatusInfo(StatusInfo status) {
        this.setStatus(status);
        return (T) this;
    }

    public T putModel(Model model) {
        this.setModel(model);
        return (T) this;
    }

    public T putSerialNumber(String serialNumber) {
        this.setSerialNumber(serialNumber);
        return (T) this;
    }

    public T putPartNumber(String partNumber) {
        this.setPartNumber(partNumber);
        return (T) this;
    }

    @Override
    public String toString() {
        return "Component{ " + super.toString() + " name=" + name + ", type=" + type + ", enabledState=" + enabledState + ", status=" + status + ", model=" + model + ", serialNumber=" + serialNumber + ", partNumber=" + partNumber + ", components=" + components + ", capabilities=" + capabilities + ", configuration=" + configuration + ", tresholds=" + tresholds + '}';
    }

}
