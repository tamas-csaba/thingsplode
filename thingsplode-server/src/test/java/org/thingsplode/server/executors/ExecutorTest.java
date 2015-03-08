/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.server.executors;

import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import junit.framework.Assert;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.thingsplode.TestBaseWithRepos;
import org.thingsplode.core.entities.Device;
import org.thingsplode.core.exceptions.MessageConversionException;
import org.thingsplode.core.exceptions.SrvExecutionException;
import org.thingsplode.core.protocol.AbstractMessage;
import org.thingsplode.core.protocol.Response;
import org.thingsplode.core.protocol.request.BootNotificationRequest;
import org.thingsplode.core.protocol.response.BootNotificationResponse;
import org.thingsplode.domain.TestFactory;
import org.thingsplode.server.BaseConfig;
import org.thingsplode.server.BusConfig;
import org.thingsplode.server.JpaConfig;
import org.thingsplode.server.bus.gateways.RequestGateway;
import org.thingsplode.server.bus.gateways.SyncGateway;
import org.thingsplode.server.infrastructure.DeviceService;

/**
 *
 * @author tamas.csaba@gmail.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {BaseConfig.class, JpaConfig.class, BusConfig.class})
@TestPropertySource("classpath:/test.properties")
@TransactionConfiguration(transactionManager = "txMgr", defaultRollback = false)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class ExecutorTest extends TestBaseWithRepos {

    private static final Logger logger = Logger.getLogger(ExecutorTest.class);

    private final String testDeviceID = "test_device_1";
    private Device testDevice;
    @Autowired(required = true)
    private RequestGateway requestGw;
    @Autowired
    private SyncGateway syncGw;
    @Autowired(required = true)
    private DeviceService deviceService;

    public ExecutorTest() {
        super();
    }

    @Before
    public void setupTest() throws UnknownHostException, SrvExecutionException {
        if (testDevice == null || testDevice.getId() == null) {
            testDevice = TestFactory.createDevice(testDeviceID, "123456789", "1");
            testDevice = deviceService.registerOrUpdate(testDevice);
        }
    }

    @Test
    public void testBootNotificationRequest() throws UnknownHostException, InterruptedException, ExecutionException, TimeoutException, MessageConversionException {
        Device d = TestFactory.createDevice("test_device_2", "123456789", "1");
        Future<Response> rspHandle = requestGw.execute(new BootNotificationRequest(d.getDeviceId(), Calendar.getInstance().getTimeInMillis(), d));
        Response rsp = rspHandle.get(30, TimeUnit.SECONDS);
        Assert.assertNotNull("The resposne message cannot be null.", rsp);
        Assert.assertFalse(rsp.isErrorType());
        Assert.assertTrue(rsp.isAcknowledged());
        Assert.assertNotNull("The registration id cannot be null", rsp.expectMessageByType(BootNotificationResponse.class).getRegistrationID());
        System.out.println("\n\n RESPONSE");
        System.out.println(rsp.toString());
        System.out.println("\n\n ========");
    }
//    @Test
//    public void testEventSync() {
//        Event evt = new Event().putId("some_event_id").putClass("some_class").putSeverity(Event.Severity.DEBUG).putEventDate(Calendar.getInstance()).putReceiveDate(Calendar.getInstance());
//        syncGw.process(new EventSync(testDevice.getName(), evt));
//    }

}
