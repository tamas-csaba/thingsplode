/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.server;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.Properties;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.thingsplode.core.entities.Device;

/**
 *
 * @author tamas.csaba@gmail.com
 */
@Configuration
@EnableJpaRepositories(Bootstrap.REPOS)
@EnableTransactionManagement
public class JpaConfig {

    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource() throws PropertyVetoException, SQLException {
        ComboPooledDataSource ds = new ComboPooledDataSource();
        ds.setJdbcUrl(env.getProperty("datasource.driver.url"));
        ds.setDriverClass(env.getProperty("datasource.driver.class"));
        ds.setUser(env.getProperty("datasource.driver.user"));
        ds.setPassword(env.getProperty("datasource.driver.password"));
        ds.setAcquireIncrement(env.getProperty("datasource.acquire.increment", Integer.class));
        ds.setInitialPoolSize(env.getProperty("datasource.initial.pool.size", Integer.class));
        ds.setMinPoolSize(env.getProperty("datasource.min.pool.size", Integer.class));
        ds.setMaxPoolSize(env.getProperty("datasource.max.pool.size", Integer.class));
        ds.setMaxStatementsPerConnection(env.getProperty("datasource.max.statements.per.connection", Integer.class));
        ds.setLoginTimeout(env.getProperty("datasource.login.timeout", Integer.class));
        ds.setAcquireRetryAttempts(env.getProperty("datasource.acquire.retry.attempts", Integer.class));
        ds.setNumHelperThreads(env.getProperty("datasource.num.helper.threads", Integer.class));
        ds.setAutomaticTestTable(env.getProperty("datasource.testtable.name", String.class));
        ds.setIdleConnectionTestPeriod(env.getProperty("datasource.idle.connection.test.period", Integer.class));
        ds.setTestConnectionOnCheckin(env.getProperty("datasource.test.connection.on.checkin", Boolean.class));
        /*
         Use only if necessary. Expensive. If true, an operation will be performed at every connection checkout to verify
         that the connection is valid. Better choice: verify connections periodically using idleConnectionTestPeriod. Also, setting
         an automaticTestTable or preferredTestQuery will usually speed up all connection tests.
         */
        ds.setTestConnectionOnCheckout(false);
        ds.setCheckoutTimeout(env.getProperty("connection.acquire.timeout", Integer.class));
        /*
         Number of seconds that Connections in excess of minPoolSize should be permitted to remain idle in the pool before being culled. Intended for applications that wish to aggressively minimize the number of open Connections, shrinking the pool back towards minPoolSize if, following a spike, the load level diminishes and Connections acquired are no longer needed. If maxIdleTime is set, maxIdleTimeExcessConnections should be smaller if the parameter is to have any effect. Zero means no enforcement, excess Connections are not idled out.
         */
        ds.setMaxIdleTimeExcessConnections(0);
        ds.setMaxConnectionAge(env.getProperty("datasource.max.age.per.connection", Integer.class));
        return ds;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() throws PropertyVetoException, SQLException {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(env.getProperty("hibernate.generate.ddl", Boolean.class));
        vendorAdapter.setShowSql(env.getProperty("hibernate.show.sql", Boolean.class));

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        
        factory.setPackagesToScan(Bootstrap.ENTITIES, Device.class.getPackage().getName());
        
        factory.setDataSource(dataSource());
        factory.setJpaProperties(getProperties());
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager() throws PropertyVetoException, SQLException {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory());
        return txManager;
    }

    Properties getProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        properties.setProperty("hibernate.connection.autocommit", env.getProperty("hibernate.connection.autocommit"));
        properties.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
        properties.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
        properties.setProperty("hibernate.format_sql", env.getProperty("hibernate.format_sql"));
        properties.setProperty("hibernate.jdbc.batch_size", env.getProperty("hibernate.jdbc.batch_size"));
        properties.setProperty("hibernate.validator.apply_to_ddl", env.getProperty("hibernate.validator.apply_to_ddl"));
        properties.setProperty("persistence.validation.mode", env.getProperty("persistence.validation.mode"));
        return properties;
    }
}
