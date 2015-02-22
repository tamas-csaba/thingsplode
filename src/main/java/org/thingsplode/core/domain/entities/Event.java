/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.core.domain.entities;

import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import static org.thingsplode.core.domain.entities.Persistable.COL_ID;

/**
 *
 * @author tam
 */
@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Event extends Persistable<Long> {

    private String eventId;
    private String eventClass;
    private Collection<Indication> indications;
    private Severity severity;
    private Calendar eventDate;
    @XmlTransient
    private Calendar receiveDate;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Generated(value = GenerationTime.INSERT)
    @Column(name = COL_ID, updatable = false, insertable = false)
    @Override
    public Long getId() {
        return super.getId();
    }

    /**
     * @return the eventId
     */
    public String getEventId() {
        return eventId;
    }

    /**
     * @param eventId the eventId to set
     */
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    /**
     * @return the eventClass
     */
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
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "EVT_ID")
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
    
    public static Event create(String eventId, String eventClass, Severity severity){
        return Event.create().putId(eventId).putClass(eventClass).putSeverity(severity);
    }

    public Event putId(String eventID) {
        this.setEventId(eventId);
        return this;
    }
    
    public Event putClass(String evtClass){
        this.setEventClass(eventClass);
        return this;
    }
    
    public Event addIndications(Indication ... indicationArray){
        Collections.addAll(this.indications, indicationArray);
        return this;
    }
    
    public Event putSeverity(Severity severity){
        this.setSeverity(severity);
        return this;
    }
    
    static public enum Severity {

        ERROR,
        WARNING,
        INFO,
        DEBUG;
    }
}
