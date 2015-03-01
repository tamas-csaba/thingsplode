/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.server.repositories;

import java.net.UnknownHostException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.thingsplode.TestBase;
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
public class RepositoryTest extends TestBase {

    @Autowired
    private DeviceRepository deviceRepo;

    public RepositoryTest() {
        super();
    }

//    public RepositoryTest(String testName) {
//        super(testName);
//    }
    @Test
    public void testBasics() throws UnknownHostException {
        deviceRepo.save(TestFactory.createDevice("test_device_1"));
        deviceRepo.save(TestFactory.createDevice("test_device_2"));
    }
}
