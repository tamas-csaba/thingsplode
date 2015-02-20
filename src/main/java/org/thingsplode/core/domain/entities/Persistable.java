/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.thingsplode.core.domain.entities;

import java.io.Serializable;

/**
 *
 * @author tam
 * @param <ID>
 */
public abstract class Persistable<ID extends Serializable> implements Serializable{
    public static final String COL_ID = "id";
    public abstract ID getId();
}
