/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.server.repositories;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;
import junit.framework.TestCase;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.thingsplode.core.domain.Address;
import org.thingsplode.core.domain.EnabledState;
import org.thingsplode.core.domain.Location;
import org.thingsplode.core.domain.Model;
import org.thingsplode.core.domain.StatusInfo;
import org.thingsplode.core.domain.entities.Device;
import org.thingsplode.server.BaseConfig;
import org.thingsplode.server.JpaConfig;

/**
 *
 * @author tam
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {BaseConfig.class, JpaConfig.class})
@TestPropertySource("file:C:\\Code\\research\\thingsplode\\src\\test\\resources\\test.properties")
//@ActiveProfiles({"dev", "integration"})
@TransactionConfiguration(transactionManager = "txMgr", defaultRollback = true)
//@TestExecutionListeners(listeners = {}, mergeMode = MergeMode.MERGE_WITH_DEFAULTS)
public class RepositoryTest extends TestCase {

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private DeviceRepository deviceRepo;

    public RepositoryTest(){
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
    
//    public RepositoryTest(String testName) {
//        super(testName);
//    }

    @Test
    public void testBasics() throws UnknownHostException {
        Device d = Device.create("test_device", EnabledState.ENABLED, StatusInfo.OFFLINE);
        d.
                putIpAddress(InetAddress.getLocalHost()).
                putLastHeartbeat(Calendar.getInstance()).putLocation(Location.create("default", Address.create().putCity("some_city"))).
                putModel(Model.create().putManufacturer("some_manifacturer").putType("some_type")).
                putStartupDate(Calendar.getInstance());
        deviceRepo.save(d);
    }
}
