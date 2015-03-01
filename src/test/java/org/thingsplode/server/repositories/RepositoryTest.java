/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.server.repositories;

import java.net.UnknownHostException;
import java.util.List;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.thingsplode.TestBaseWithRepos;
import org.thingsplode.core.domain.entities.Device;
import org.thingsplode.domain.TestFactory;
import org.thingsplode.server.BaseConfig;
import org.thingsplode.server.JpaConfig;

/**
 *
 * @author tamas.csaba@gmail.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {BaseConfig.class, JpaConfig.class})
@TestPropertySource("file:C:\\Code\\research\\thingsplode\\src\\test\\resources\\test.properties")
//@ActiveProfiles({"dev", "integration"})
@TransactionConfiguration(transactionManager = "txMgr", defaultRollback = true)
//@TestExecutionListeners(listeners = {}, mergeMode = MergeMode.MERGE_WITH_DEFAULTS)
public class RepositoryTest extends TestBaseWithRepos {

    @Autowired(required = true)
    private DeviceRepository deviceRepo;
    @Autowired(required = true)
    private DeviceEventRepository deviceEventRepo;
    @Autowired(required = true)
    private ComponentEventRepository componentEventRepo;
    @Autowired(required = true)
    private ComponentRepository componentRepo;

    public RepositoryTest() {
        super();
    }

//    public RepositoryTest(String testName) {
//        super(testName);
//    }
    @Test
    public void testBasics() throws UnknownHostException {
        Device d1 = deviceRepo.save(TestFactory.createDevice("test_device_1", "1231234235"));
        Device d2 = deviceRepo.save(TestFactory.createDevice("test_device_2", "1231234236"));

        Assert.assertTrue("There should be 2 devices in the database at this stage.", deviceRepo.count() == 2);
        Assert.assertTrue("There should be 2 component events in the database at this stage.", componentEventRepo.count() == 2);
        Assert.assertTrue("There should be 2 device events in the database at this stage.", deviceEventRepo.count() == 2);
        Assert.assertTrue("There should be 4 indications in the database at this stage.", getCount("INDICATION") == 4);
        Assert.assertTrue("There should be 4 models in the database at this stage.", getCount("MODEL") == 2);
        Assert.assertTrue("There should be 4 tresholds in the database at this stage.", getCount("TRESHOLD") == 4);
        Assert.assertTrue("There should be 6 configruations in the database at this stage.", getCount("CONFIGURATION") == 6);
        Assert.assertTrue("There should be 4 components in the database at this stage.", componentRepo.count() == 4);
        Assert.assertTrue("There should be 6 capabilities in the database at this stage.", getCount("CAPABILITY") == 6);
        System.out.println("****  DELETING ****");
        deviceRepo.delete(d1);
    }
}
