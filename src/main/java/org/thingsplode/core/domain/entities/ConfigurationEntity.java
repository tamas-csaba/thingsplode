/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.core.domain.entities;

import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.thingsplode.core.domain.Configuration;

/**
 *
 * @author tam
 */
@Entity
public class ConfigurationEntity extends Configuration {


    private Calendar commitDate;

    @Temporal(TemporalType.TIMESTAMP)
    public Calendar getCommitDate() {
        return commitDate;
    }

    public void setCommitDate(Calendar commitDate) {
        this.commitDate = commitDate;
    }
    
    static enum Type {
        STRING,
        BOOLEAN,
        NUMBER,
    }
}
