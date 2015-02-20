/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.core.domain.entities;

import java.util.Collection;
import org.thingsplode.core.domain.EnabledState;
import org.thingsplode.core.domain.Model;
import org.thingsplode.core.domain.StatusInfo;

/**
 *
 * @author tam
 */
public class Component {
    
    private String name;
    private Type type;
    private EnabledState enabledState;
    private StatusInfo status;
    private Model model;
    private Collection<Component> subComponents;
    private Collection<Capability> capabilities;
    private Collection<Event> eventLog;
    private Collection<ConfigurationEntity> configuration;

    static enum Type {
        HARDWARE,
        SOFTWARE;
    }
}
