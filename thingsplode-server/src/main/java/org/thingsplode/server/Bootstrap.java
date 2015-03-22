package org.thingsplode.server;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * Application startup
 * Activating profiles from the command line
 * -Dspring.profiles.active="prod,embedded_db"
 *
 */
public class Bootstrap {

    public static final String REPOS = "org.thingsplode.server.repositories";
    public static final String ENTITIES = "org.thingsplode.core.domain.entities";

    public static void main(String[] args) {        
        System.out.println("starting thingsplode device server...");
        AnnotationConfigApplicationContext annCtx = new AnnotationConfigApplicationContext(Bootstrap.class);
        ConfigurableEnvironment env = annCtx.getEnvironment();
        System.out.println("active profiles: "+env.getActiveProfiles());
        annCtx.register(Bootstrap.class);
        annCtx.refresh();
    }
}
