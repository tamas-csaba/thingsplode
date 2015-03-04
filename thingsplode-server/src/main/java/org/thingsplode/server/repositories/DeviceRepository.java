/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.server.repositories;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.thingsplode.core.domain.entities.Device;

/**
 *
 * @author tamas.csaba@gmail.com
 * @param <Device>
 */
interface DeviceRepository extends PagingAndSortingRepository<Device, Long> {

    @CacheEvict("bydeviceId")
    @Override
    <S extends Device> S save(S entity);

    @CacheEvict("bydeviceId")
    @Override
    <S extends Device> Iterable<S> save(Iterable<S> entities);

    @Cacheable("bydeviceId")
    Device findBydeviceId(String deviceId);

}
