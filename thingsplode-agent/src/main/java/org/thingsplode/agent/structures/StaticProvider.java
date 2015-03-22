/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.agent.structures;

import java.util.Collection;

/**
 *
 * @author tamas.csaba@gmail.com
 * @param <ITEM>
 */
public abstract class StaticProvider<ITEM> {

    public abstract Collection<ITEM> collect();
}
