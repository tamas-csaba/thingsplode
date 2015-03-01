/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.server.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.thingsplode.core.domain.entities.ComponentEvent;

/**
 *
 * @author tamas.csaba@gmail.com
 */
public interface ComponentEventRepository extends PagingAndSortingRepository<ComponentEvent, Long> {

}
