/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.server.executors;

import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.List;
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
import org.thingsplode.core.EnabledState;
import org.thingsplode.core.entities.Configuration;
import org.thingsplode.core.entities.Device;
import org.thingsplode.core.entities.Event;
import org.thingsplode.core.exceptions.MessageConversionException;
import org.thingsplode.core.exceptions.SrvExecutionException;
import org.thingsplode.core.protocol.ExecutionStatus;
import org.thingsplode.core.protocol.Response;
import org.thingsplode.core.protocol.ResponseCode;
import org.thingsplode.core.protocol.request.BootNotificationRequest;
import org.thingsplode.core.protocol.response.BootNotificationResponse;
import org.thingsplode.core.protocol.sync.EventSync;
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
        deviceService.setEnableAutoRegistration(true);
        if (testDevice == null || testDevice.getId() == null) {
            testDevice = TestFactory.createDevice(testDeviceID, "123456789", "1");
            testDevice = deviceService.registerOrUpdate(testDevice);
        }
    }

    @Test
    public void testBootNotificationRequest() throws UnknownHostException, InterruptedException, ExecutionException, TimeoutException, MessageConversionException {
        Device d = TestFactory.createDevice("test_device_2", "987654321", "1");
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

    @Test
    public void testBootNotificationRequestWithDisabledDevice() throws InterruptedException, ExecutionException, TimeoutException {
        boolean updated = deviceService.setDeviceEnabledState(testDevice.getDeviceId(), EnabledState.DISABLED);
        Assert.assertTrue("The device should have been found.", updated);
        Future<Response> rspHandle = requestGw.execute(new BootNotificationRequest(testDevice.getDeviceId(), Calendar.getInstance().getTimeInMillis(), testDevice));
        Response rsp = rspHandle.get(30, TimeUnit.SECONDS);
        Assert.assertTrue("the request should be declined", rsp.getRequestStatus() == ExecutionStatus.DECLINED);
        Assert.assertTrue("the request should be declined with permission denied", rsp.getResponseCode() == ResponseCode.PERMISSION_DENIED);
        System.out.println("\n\n\n ***** RESULT MESSAGE \n " + rsp.getResultMessage());
    }

    @Test
    public void testBootNotificationReqyestWithNewConfigs() throws InterruptedException, ExecutionException, TimeoutException, MessageConversionException {
        boolean updated = deviceService.setDeviceEnabledState(testDevice.getDeviceId(), EnabledState.ENABLED);
        Assert.assertTrue("The device should have been found.", updated);
        List<Configuration> cfgs = Configuration.ConfigurationBuilder.newBuilder().addConfiguration("key_1", Configuration.Type.STRING, "woops1").addConfiguration("key_2", Configuration.Type.STRING, "woops2").addConfiguration("shutdown_timeout", Configuration.Type.NUMBER, "4000").build();
        deviceService.setConfigurationForDevices(cfgs);
        Future<Response> rspHandle = requestGw.execute(new BootNotificationRequest(testDevice.getDeviceId(), Calendar.getInstance().getTimeInMillis(), testDevice));
        Response rsp = rspHandle.get(30, TimeUnit.SECONDS);
        System.out.println("\n\n RESPONSE: \n" + rsp != null ? rsp.toString() : "<NULL>");
        Assert.assertNotNull("the type should be boot notification response", rsp.expectTypeSafely(BootNotificationResponse.class));
        Assert.assertTrue("the configruation must not be empty", !rsp.expectMessageByType(BootNotificationResponse.class).getConfiguration().isEmpty());
        this.listACollection("CONFIGURATION: ", rsp.expectMessageByType(BootNotificationResponse.class).getConfiguration());
        
        // expected test outcomes:
            //new configs are available -> key 1 and key 2
            //deletable config must disappear
            //shutdown_timeout should be overwritten from 3000 to 4000 
            //update existing capability / remove and add a capability
    }

    @Test
    public void testEventSync() {
        Event evt = new Event().putId("some_event_id").putClass("some_class").putSeverity(Event.Severity.DEBUG).putEventDate(Calendar.getInstance()).putReceiveDate(Calendar.getInstance());
        syncGw.process(new EventSync(testDevice.getDeviceId(), testDevice.getName(), evt, Calendar.getInstance().getTimeInMillis()));
    }
    //todo: test exceptions and error messages
    //http://xpadro.blogspot.co.at/2013/11/how-error-handling-works-in-spring.html

}
