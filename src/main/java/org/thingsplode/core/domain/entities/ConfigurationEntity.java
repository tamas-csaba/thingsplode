/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.core.domain.entities;

import java.util.Calendar;
import javax.persistence.Entity;
import org.thingsplode.core.domain.Configuration;

/**
 *
 * @author tam
 */
@Entity
public class ConfigurationEntity extends Configuration {

    private Calendar commitDate;

    static enum Type {
        STRING,
        BOOLEAN,
        NUMBER,
    }
}
