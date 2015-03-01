/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.thingsplode.core.domain.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

/**
 *
 * @author tamas.csaba@gmail.com
 * @param <ID>
 */
@MappedSuperclass
public abstract class Persistable<ID extends Serializable> implements Serializable{
    public static final String COL_ID = "id";
    
    @XmlTransient
    private ID id;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Generated(value = GenerationTime.INSERT)
    @Column(name = COL_ID, updatable = false, insertable = false)
    public ID getId() {
        return id;
    }
    
    /**
     * @param id the id to set
     */
    public void setId(ID id) {
        this.id = id;
    }
}
