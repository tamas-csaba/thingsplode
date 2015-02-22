package org.thingsplode.server;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Application startup
 * Activating profiles from the command line
 * -Dspring.profiles.active="prod,embedded_db"
 *
 */
@Configuration
@EnableJpaRepositories(Bootstrap.REPOS)
@EnableTransactionManagement
@EnableLoadTimeWeaving
@ComponentScan
@PropertySource("classpath*:/META-INF/thingsplode.properties")
public class Bootstrap {

    public static final String REPOS = "org.thingsplode.server.repositories";
    private static final String ENTITIES = "org.thingsplode.core.domain.entities";
    
    @Bean
    @Profile("embedded_db")
    public DataSource dataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder.setType(EmbeddedDatabaseType.H2).setName("base").build();
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan(Bootstrap.ENTITIES);
        factory.setDataSource(dataSource());
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory());
        return txManager;
    }

    public static void main(String[] args) {        
        System.out.println("starting thingsplode...");
        AnnotationConfigApplicationContext annCtx = new AnnotationConfigApplicationContext(Bootstrap.class);
        ConfigurableEnvironment env = annCtx.getEnvironment();
        System.out.println("active profiles: "+env.getActiveProfiles());
        //for (env.ge){}
        annCtx.register(Bootstrap.class);
        annCtx.refresh();
    }
}
