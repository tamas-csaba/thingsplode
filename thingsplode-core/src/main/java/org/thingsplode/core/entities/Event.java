/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.core.entities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.thingsplode.core.Value;

/**
 *
 *
 * @author tamas.csaba@gmail.com
 */
@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
@Table(name = Event.TABLE_NAME)
public class Event extends Persistable<Long> {

    @XmlTransient
    public final static String TABLE_NAME = "EVENTS";
    @XmlTransient
    public final static String EVENT_REF = "EVT_ID";
    @XmlTransient
    public final static String COMP_REF = "COMPONENT_ID";
    @XmlTransient
    private Component component;
    private String eventId;
    private String eventClass;
    private Severity severity;
    private EventType type;
    private Calendar eventDate;
    @XmlTransient
    private Calendar receiveDate;
    private Collection<Indication> indications;

    public static class Classes {

        public static System_EventNames SYSTEM;

        public enum System_EventNames {
            HEAP,
            THREADS,
            THREAD_DETAILS,
            LOAD,
            GENERAL_ERROR;
        }
    }

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Generated(value = GenerationTime.INSERT)
//    @Column(name = COL_ID, updatable = false, insertable = false)
//    @Override
//    public Long getId() {
//        return super.getId();
//    }
//
    /**
     * @return the component
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = COMP_REF)
    public Component getComponent() {
        return component;
    }

    /**
     * @param component the component to set
     */
    public void setComponent(Component component) {
        this.component = component;
    }

    @Basic(optional = false)
    @Column(length = 100)
    public String getEventId() {
        return eventId;
    }

    /**
     * @param name the eventId to set
     */
    public void setEventId(String name) {
        this.eventId = name;
    }

    /**
     * @return the eventClass
     */
    @Basic(optional = false)
    @Column(length = 30)
    public String getEventClass() {
        return eventClass;
    }

    /**
     * @param eventClass the eventClass to set
     */
    public void setEventClass(String eventClass) {
        this.eventClass = eventClass;
    }

    /**
     * @return the indications
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = EVENT_REF)
    public Collection<Indication> getIndications() {
        return indications;
    }

    /**
     * @param indications the indications to set
     */
    public void setIndications(Collection<Indication> indications) {
        this.indications = indications;
    }

    /**
     * @return the severity
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    public Severity getSeverity() {
        return severity;
    }

    /**
     * @param severity the severity to set
     */
    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    /**
     * @return the receiveDate
     */
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(nullable = false)
    public Calendar getReceiveDate() {
        return receiveDate;
    }

    /**
     * @param receiveDate the receiveDate to set
     */
    public void setReceiveDate(Calendar receiveDate) {
        this.receiveDate = receiveDate;
    }

    /**
     * @return the eventDate
     */
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(nullable = false)
    public Calendar getEventDate() {
        return eventDate;
    }

    /**
     * @param eventDate the eventDate to set
     */
    public void setEventDate(Calendar eventDate) {
        this.eventDate = eventDate;
    }

    public static Event create() {
        Event evt = new Event();
        evt.setEventDate(Calendar.getInstance());
        return evt;
    }

    public static Event create(String eventId, String eventClass, EventType type, Severity severity) {
        return Event.create().putId(eventId).putEventClass(eventClass).putSeverity(severity).putType(type);
    }

    public static Event create(String eventId, String eventClass, EventType type, Severity severity, Calendar eventDate) {
        return Event.create(eventId, eventClass, type, severity).putEventDate(eventDate);
    }

    public Event putId(String eventID) {
        this.setEventId(eventID);
        return this;
    }

    public Event putEventClass(String evtClass) {
        this.setEventClass(evtClass);
        return this;
    }

    public Event putType(EventType type) {
        this.setType(type);
        return this;
    }

    public Event addIndication(Indication indication) {
        initializeIndications();
        this.getIndications().add(indication);
        return this;
    }
    
    public Event addIndication(String name, Value.Type valueType, String value){
        initializeIndications();
        this.getIndications().add(Indication.create(name, valueType, value));
        return this;
    }

    public void initializeIndications() {
        if (this.indications == null) {
            this.indications = new ArrayList<>();
        }
    }

    public Event putSeverity(Severity severity) {
        this.setSeverity(severity);
        return this;
    }

    public Event putReceiveDate(Calendar rcvDate) {
        this.setReceiveDate(rcvDate);
        return this;
    }

    public Event putEventDate(Calendar evtDate) {
        this.setEventDate(eventDate);
        return this;
    }

    @Enumerated(EnumType.STRING)
    @Column(length = 30, nullable = false)
    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    static public enum EventType {

        STATE_CHANGE,
        STATE_UPDATE,
        TRESHOLD_TRIGGER,
        SAMPLING,
        FAULT
        
    }

    static public enum Severity {

        ERROR,
        WARNING,
        INFO,
        DEBUG;
    }

    public Event putComponent(Component comp) {
        this.setComponent(comp);
        return this;
    }

    @Override
    public String toString() {
        return "Event{ " + super.toString() + " component=" + component + ", eventId=" + eventId + ", eventClass=" + eventClass + ", severity=" + severity + ", eventDate=" + eventDate + ", receiveDate=" + receiveDate + ", indications=" + indications + '}';
    }

}
