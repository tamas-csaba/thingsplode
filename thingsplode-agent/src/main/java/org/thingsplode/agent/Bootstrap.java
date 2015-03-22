package org.thingsplode.agent;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * Hello world!
 *
 */
@Configuration
@ComponentScan("org.thingsplode.agent")
@PropertySources({
    @PropertySource(value = {"classpath:/META-INF/thingsplode.properties"}, ignoreResourceNotFound = true), //@PropertySource(value = {"classpath:/test.properties"}, ignoreResourceNotFound = true)
})
public class Bootstrap {

    public static void main(String[] args) {
        System.out.println("starting thingsplode agent...");
        AnnotationConfigApplicationContext annCtx = new AnnotationConfigApplicationContext(Bootstrap.class);
        ConfigurableEnvironment env = annCtx.getEnvironment();
        System.out.println("active profiles: " + env.getActiveProfiles());
        annCtx.register(Bootstrap.class);
        annCtx.refresh();
    }

    @Bean
    static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer ppc = new PropertySourcesPlaceholderConfigurer();
        return ppc;
    }
}
