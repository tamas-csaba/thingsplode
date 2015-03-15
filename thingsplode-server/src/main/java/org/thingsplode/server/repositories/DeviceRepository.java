/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.server.repositories;

import java.util.List;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.thingsplode.core.entities.Device;

/**
 *
 * @author tamas.csaba@gmail.com
 * @param <Device>
 */
public interface DeviceRepository extends JpaRepository<Device, Long> {

    @CacheEvict("bydeviceId")
    @Override
    <S extends Device> S save(S entity);

    @CacheEvict("bydeviceId")
    @Override
    <S extends Device> List<S> save(Iterable<S> itrbl);
    
    @Cacheable("bydeviceId")
    Device findBydeviceId(String deviceId);

}
