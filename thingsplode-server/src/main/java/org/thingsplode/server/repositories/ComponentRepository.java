/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.server.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.thingsplode.core.entities.Component;

/**
 *
 * @author Csaba Tamas
 */
public interface ComponentRepository extends PagingAndSortingRepository<Component, Long> {

    @Query("select c from Component c where " + Component.DISCRIMINATOR + "= ?1")
    List<Component> findbyMainType(String typeValue);
    
}
