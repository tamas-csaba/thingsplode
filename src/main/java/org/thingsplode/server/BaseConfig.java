/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.thingsplode.server;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 *
 * @author tam
 */
@Configuration
@ComponentScan
@PropertySource(value = {"classpath*:/META-INF/thingsplode.properties"}, ignoreResourceNotFound = true)
//@EnableLoadTimeWeaving
public class BaseConfig {
    
}
