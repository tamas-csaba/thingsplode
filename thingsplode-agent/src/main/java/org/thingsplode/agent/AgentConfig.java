/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.agent;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.thingsplode.agent.infrastructure.BufferQueue;
import org.thingsplode.agent.infrastructure.InMemoryBufferQueue;
import org.thingsplode.core.entities.Event;

/**
 *
 * @author Csaba Tamas
 */
@Configuration
@ComponentScan("org.thingsplode.agent")
@PropertySources({
    @PropertySource(value = {"classpath:/META-INF/thingsplode.properties"}, ignoreResourceNotFound = true), //@PropertySource(value = {"classpath:/test.properties"}, ignoreResourceNotFound = true)
})
public class AgentConfig {

    @Bean
    static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer ppc = new PropertySourcesPlaceholderConfigurer();
        return ppc;
    }

    @Bean
    public BufferQueue<Event> eventQueue() {
        //todo: create different ones depending on the profile
        return new InMemoryBufferQueue<>();
    }

}
