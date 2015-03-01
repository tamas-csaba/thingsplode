/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode;

import java.io.File;
import junit.framework.TestCase;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author tamas.csaba@gmail.com
 */
public abstract class TestBase extends TestCase {

    @Autowired
    private ApplicationContext applicationContext;

    public TestBase() {
        File f = new File(System.getProperty("java.io.tmpdir") + "/thingsplode_h2db_test.h2.db");
        System.out.println("\n\n ***** \n REMOVING PREVIOUS DATABASE: ("+f.getAbsolutePath()+")\n *****");
        if (f.exists()) {
            try {
                boolean delete = f.delete();
            } catch (Exception e) {
                System.out.println("DATABASE couldn't be removed due to:" + e.getMessage());
                fail();
            }
        } else {
            System.out.println("\n\n **** WARNING: test database file was not found and not deleted. ("+f.getAbsolutePath()+")");
        }

        Logger.getRootLogger().removeAllAppenders();
        BasicConfigurator.configure();
        Logger.getRootLogger().setLevel(Level.DEBUG);
        Logger.getLogger(org.springframework.beans.factory.support.DefaultListableBeanFactory.class).setLevel(Level.DEBUG);
        Logger.getLogger(org.springframework.beans.factory.xml.XmlBeanDefinitionReader.class).setLevel(Level.INFO);
        Logger.getLogger(org.springframework.test.context.TestContextManager.class).setLevel(Level.DEBUG);
        Logger.getLogger("org.hibernate.cfg").setLevel(Level.ERROR);
        Logger.getLogger("org.hibernate.validator").setLevel(Level.ERROR);
        Logger.getLogger("org.hibernate.id").setLevel(Level.ERROR);
        Logger.getLogger(org.hibernate.cfg.annotations.PropertyBinder.class).setLevel(Level.WARN);
        Logger.getLogger(org.hibernate.cfg.annotations.SimpleValueBinder.class).setLevel(Level.WARN);
        Logger.getLogger(org.hibernate.cfg.annotations.TableBinder.class).setLevel(Level.WARN);
        Logger.getLogger(org.hibernate.validator.engine.resolver.DefaultTraversableResolver.class).setLevel(Level.WARN);
        Logger.getLogger(org.hibernate.validator.metadata.ConstraintDescriptorImpl.class).setLevel(Level.WARN);
        //Logger.getLogger(org.hibernate.id.factory.DefaultIdentifierGeneratorFactory.class).setLevel(Level.WARN);
        Logger.getLogger(org.hibernate.cfg.Configuration.class).setLevel(Level.WARN);
        Logger.getLogger(org.hibernate.persister.entity.AbstractEntityPersister.class).setLevel(Level.WARN);
        Logger.getLogger(org.hibernate.loader.collection.OneToManyLoader.class).setLevel(Level.WARN);
        Logger.getLogger(org.hibernate.loader.collection.OneToManyLoader.class).setLevel(Level.WARN);
        Logger.getLogger(org.hibernate.cfg.Ejb3Column.class).setLevel(Level.WARN);
        //Logger.getLogger(org.hibernate.cfg.search.HibernateSearchEventListenerRegister.class).setLevel(Level.WARN);
        Logger.getLogger(org.hibernate.validator.xml.ValidationXmlParser.class).setLevel(Level.WARN);
        Logger.getLogger(com.mchange.v2.resourcepool.BasicResourcePoolFactory.class).setLevel(Level.WARN);
        Logger.getLogger(com.mchange.v2.async.ThreadPoolAsynchronousRunner.class).setLevel(Level.WARN);
        Logger.getLogger(org.hibernate.tool.hbm2ddl.SchemaExport.class).setLevel(Level.INFO);
    }
}
