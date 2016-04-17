/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.thingsplode.core.entities.Model;

/**
 *
 * @author Csaba Tamas
 */
public interface ModelRepository extends JpaRepository<Model, Long> {

    Model findByManufacturerAndTypeAndVersion(String manufacturer, String type, String version);
}
