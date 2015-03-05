/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.thingsplode.server;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;

/**
 *
 * @author tamas.csaba@gmail.com
 */
@Configuration
@ComponentScan("org.thingsplode.server")
@PropertySource(value = {"classpath*:/META-INF/thingsplode.properties"}, ignoreResourceNotFound = true)
@EnableIntegration
@IntegrationComponentScan("org.thingsplode.server.bus")
//@Import
//@EnableLoadTimeWeaving
public class BaseConfig {
    
}
