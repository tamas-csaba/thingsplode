/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.server.executors;

import java.net.UnknownHostException;
import java.util.Calendar;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.thingsplode.TestBaseWithRepos;
import org.thingsplode.core.entities.Device;
import org.thingsplode.core.protocol.request.RegistrationRequest;
import org.thingsplode.domain.TestFactory;
import org.thingsplode.server.BaseConfig;
import org.thingsplode.server.BusConfig;
import org.thingsplode.server.JpaConfig;
import org.thingsplode.server.bus.TestRequestService;

/**
 *
 * @author tamas.csaba@gmail.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {BaseConfig.class, JpaConfig.class, BusConfig.class})
@TestPropertySource("file:C:\\Code\\research\\thingsplode\\thingsplode-server\\src\\test\\resources\\test.properties")
@TransactionConfiguration(transactionManager = "txMgr", defaultRollback = false)
public class ExecutorTest extends TestBaseWithRepos {

    @Autowired(required = true)
    private TestRequestService service;
    
    public ExecutorTest() {
        super();
    }
    
    @Test
    public void testRegistration() throws UnknownHostException{
        Device d = TestFactory.createDevice("test_device_1", "1231234235", "1");
        service.execute(new RegistrationRequest(d.getDeviceId(), Calendar.getInstance(), d));
    }

}
