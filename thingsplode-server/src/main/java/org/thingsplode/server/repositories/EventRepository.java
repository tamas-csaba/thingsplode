/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.server.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.thingsplode.core.entities.Component;
import org.thingsplode.core.entities.Event;

/**
 *
 * @author tamas.csaba@gmail.com
 */
public interface EventRepository extends JpaRepository<Event, Long> {

//    @Modifying
//    @Transactional
//    @Query("delete from Event e where e.component = false")@
    Page<Event> findByComponent(Component component, Pageable pageable);

    Long deleteByComponent(Component comp);

     //List<User> removeByLastname(String lastname);
}
