/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.core.domain.entities;

import java.util.Calendar;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    @XmlTransient
    private Long id;
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
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
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

    static enum Severity {
           ERROR,
           WARNING,
           INFO,
           DEBUG;
    }
}
