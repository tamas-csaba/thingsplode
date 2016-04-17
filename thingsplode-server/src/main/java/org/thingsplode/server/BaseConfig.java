/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;

/**
 *
 * @author Csaba Tamas
 */
@Configuration
@ComponentScan("org.thingsplode.server")
@PropertySources({
    @PropertySource(value = {"classpath:/META-INF/thingsplode.properties"}, ignoreResourceNotFound = true),
    //@PropertySource(value = {"classpath:/test.properties"}, ignoreResourceNotFound = true)
})
@EnableIntegration
@IntegrationComponentScan("org.thingsplode.server.bus")
//@Import
//@EnableLoadTimeWeaving
public class BaseConfig {

    @Bean
    static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer ppc = new PropertySourcesPlaceholderConfigurer();
        return ppc;
    }

}
