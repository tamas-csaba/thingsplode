package org.thingsplode.agent;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * Hello world!
 *
 */
public class Bootstrap {

    public static void main(String[] args) {
        System.out.println("starting thingsplode agent...");
        AnnotationConfigApplicationContext annCtx = new AnnotationConfigApplicationContext(AgentConfig.class);
        ConfigurableEnvironment env = annCtx.getEnvironment();
        System.out.println("active profiles: " + env.getActiveProfiles());
        annCtx.register(Bootstrap.class);
        annCtx.refresh();
    }

}
