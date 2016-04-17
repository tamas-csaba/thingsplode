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
import org.thingsplode.core.Value;
import org.thingsplode.core.entities.Capability;
import org.thingsplode.core.entities.Component;
import org.thingsplode.core.entities.Configuration;
import org.thingsplode.core.entities.Device;
import org.thingsplode.core.entities.Event;
import org.thingsplode.core.entities.Treshold;
import org.thingsplode.core.ValueType;
import org.thingsplode.core.entities.Indication;
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
import org.thingsplode.server.repositories.DeviceRepository;

/**
 *
 * @author Csaba Tamas
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {BaseConfig.class, JpaConfig.class, BusConfig.class})
@TestPropertySource("classpath:/test.properties")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
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
//    @Autowired(required = true)
//    private EventRepository eventRepo;
    @Autowired(required = true)
    private DeviceRepository deviceRepo;

    public ExecutorTest() {
        super();
    }

    @Before
    public void setupTest() throws UnknownHostException, SrvExecutionException {
        deviceService.setEnableAutoRegistration(true);
        if (testDevice == null || testDevice.getId() == null) {
            testDevice = deviceRepo.findByIdentification(testDeviceID);
            if (testDevice == null) {
                testDevice = TestFactory.createDevice(testDeviceID, "123456789", "1");
                testDevice = deviceService.registerOrUpdate(testDevice);
                Assert.assertTrue("deletable_config should exists", testDevice.getConfigurationByKey("deletable_config") != null);
                Assert.assertTrue("the shutdown_timeout should be " + TestFactory.DEFAULT_SHUTDOWN_TIMEOUT, testDevice.getConfigurationByKey("shutdown_timeout").getValue().equalsIgnoreCase(TestFactory.DEFAULT_SHUTDOWN_TIMEOUT));
            }
        }
    }

    @Test
    public void testBootNotificationRequest() throws UnknownHostException, InterruptedException, ExecutionException, TimeoutException, MessageConversionException {
        Device d = TestFactory.createDevice("test_device_2", "987654321", "1");
        Future<Response> rspHandle = requestGw.execute(new BootNotificationRequest(d.getIdentification(), Calendar.getInstance().getTimeInMillis(), d));
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
        boolean updated = deviceService.setDeviceEnabledState(testDevice.getIdentification(), EnabledState.DISABLED);
        Assert.assertTrue("The device should have been found.", updated);
        Future<Response> rspHandle = requestGw.execute(new BootNotificationRequest(testDevice.getIdentification(), Calendar.getInstance().getTimeInMillis(), testDevice));
        Response rsp = rspHandle.get(30, TimeUnit.SECONDS);
        Assert.assertTrue("the request should be declined", rsp.getRequestStatus() == ExecutionStatus.DECLINED);
        Assert.assertTrue("the request should be declined with permission denied", rsp.getResponseCode() == ResponseCode.PERMISSION_DENIED);
        System.out.println("\n\n\n ***** RESULT MESSAGE \n " + rsp.getResultMessage());
        updated = deviceService.setDeviceEnabledState(testDevice.getIdentification(), EnabledState.ENABLED);
        Assert.assertTrue("The device should have been found.", updated);

    }

    @Test
    public void testBootNotificationReqyestWithNewConfigs() throws InterruptedException, ExecutionException, TimeoutException, MessageConversionException, UnknownHostException {
        //pre-preparation of the database
        String testHostAddress = "111.111.111.111";
        boolean updated = deviceService.setDeviceEnabledState(testDevice.getIdentification(), EnabledState.ENABLED);
        Assert.assertTrue("The device should have been found.", updated);
        List<Configuration> cfgs = Configuration.ConfigurationBuilder.newBuilder().
                addConfiguration("key_1", ValueType.STRING, "woops1").
                addConfiguration("key_2", ValueType.STRING, "woops2").
                addConfiguration("shutdown_timeout", ValueType.NUMBER, "4000").build();
        deviceService.setConfigurationForDevices(cfgs);
        //preparation of request
        Device bootingDevice = TestFactory.createDevice(testDeviceID, "123456789", "1");//testDevice
        bootingDevice.
                putIpAddress(testHostAddress).removeCapabilities("door_control").
                removeTresholds("alarm_high").
                addOrUpdateTresholds(Treshold.TresholdBuilder.newBuilder().
                        add("auxiliary_alarm", Treshold.Type.HIGH, Value.Type.NUMBER, "2000").
                        add("temperature_activity", Treshold.Type.HIGH, Value.Type.BOOLEAN, "true").
                        add("alarm_low", Treshold.Type.LOW, Value.Type.NUMBER, "200").build()
                ).
                addOrUpdateCapabilies(Capability.create(Capability.Type.WRITE_OR_EXECUTE, "auxiliary_control", true)).
                setCapabilityAcitvity("meter_value", false).
                removeConfigurations("deletable_config").
                addOrUpdateConfigurations(Configuration.create("booting_device_time", Long.toString(Calendar.getInstance().getTimeInMillis()), ValueType.NUMBER));
        Future<Response> rspHandle = requestGw.execute(new BootNotificationRequest(testDevice.getIdentification(), Calendar.getInstance().getTimeInMillis(), bootingDevice));
        //Response rsp = rspHandle.get(30, TimeUnit.SECONDS);
        Response rsp = rspHandle.get();
        System.out.println("\n\n RESPONSE: \n" + rsp != null ? rsp.toString() : "<NULL>");
        // -------------------------------------
        // -- Basic Assertions on the response
        // -------------------------------------
        Assert.assertNotNull("the type should be boot notification response", rsp.expectTypeSafely(BootNotificationResponse.class));
        Assert.assertTrue("the configruation must not be empty", !rsp.expectMessageByType(BootNotificationResponse.class).getConfiguration().isEmpty());
        this.listAMap("CONFIGURATION: ", rsp.expectMessageByType(BootNotificationResponse.class).getConfiguration());
        this.listAMap("TRESHOLDS: ", rsp.expectMessageByType(BootNotificationResponse.class).getTresholds());
        BootNotificationResponse bnr = rsp.expectMessageByType(BootNotificationResponse.class);
        Assert.assertTrue("the configuration on the boot notification response cannot be empty", bnr.getConfiguration() != null && !bnr.getConfiguration().isEmpty());
        Assert.assertTrue("the configuration on the boot notification response cannot be empty", bnr.getTresholds() != null && !bnr.getTresholds().isEmpty());
        // -------------------------------------
        // -- Assertions on the device state in the DB
        // -------------------------------------
        int orphanConfigs = this.getCountWhere(Configuration.TABLE_NAME, Component.COMP_REF + " is null");
        Assert.assertTrue("the orphan configs should be null", orphanConfigs == 0);

        Device assertableDevice = deviceService.getInitializedDeviceByDbId(rsp.expectMessageByType(BootNotificationResponse.class).getRegistrationID());
        Assert.assertTrue("the device must exist in the database", assertableDevice != null);
        Assert.assertTrue("the address should be " + testHostAddress, assertableDevice.getHostAddress().equalsIgnoreCase(testHostAddress));
        Assert.assertTrue("there should be 4 configs", assertableDevice.getConfiguration().size() == 4);
        Assert.assertTrue("the booting_device_time configuration must exist on the entity", assertableDevice.getConfigurationByKey("booting_device_time") != null);
        Assert.assertTrue("the shutdown_timeout should be 4000", assertableDevice.getConfigurationByKey("shutdown_timeout").getValue().equalsIgnoreCase("4000"));
        Assert.assertTrue("key_1 with woops1 value must exist", assertableDevice.getConfigurationByKey("key_1") != null && assertableDevice.getConfigurationByKey("key_1").getValue().equalsIgnoreCase("woops1"));
        Assert.assertTrue("deletable_config should have disappeared at this stage", assertableDevice.getConfigurationByKey("deletable_config") == null);
        //------- Capability tests
        int orphanCapabilities = this.getCountWhere(Capability.TABLE_NAME, Component.COMP_REF + " is null");
        Assert.assertTrue("the orphan capabilities should be null", orphanCapabilities == 0);
        Assert.assertTrue("auxiliary_control capability should exist at this stage", assertableDevice.getCapabilityByName("auxiliary_control") != null);
        Assert.assertTrue("meter_value should be inactive", assertableDevice.getCapabilityByName("meter_value") != null && !assertableDevice.getCapabilityByName("meter_value").isActive());
        Assert.assertTrue("door_control capability should not exist at this stage", assertableDevice.getCapabilityByName("door_control") == null);
        //------- Tresholds tests
        int orphanTresholds = this.getCountWhere(Treshold.TABLE_NAME, Component.COMP_REF + " is null");
        Assert.assertTrue("the orphan tresholds should be null", orphanTresholds == 0);
        //set the treshold overwrite to true

        Assert.assertTrue("auxiliary_alarm treshold should exist at this stage", assertableDevice.getTresholdByName("auxiliary_alarm") != null);
        Assert.assertTrue("temperature_activity treshold should exist at this stage", assertableDevice.getTresholdByName("temperature_activity") != null);
        Assert.assertTrue("alarm_low treshold should exist at this stage", assertableDevice.getTresholdByName("alarm_low") != null);
//-->   set synced the config which comes with boot notification (mean that the device is also aware of it)        
//-->   If the boot notification request will overwrite the tresholds        
//-->   Assert.assertTrue("alarm_low treshold should have the value 200", assertableDevice.getTresholdByName("alarm_low").getTresholdValue().getContent().equalsIgnoreCase("200"));
//-->   Assert.assertTrue("alarm_high treshold should not exist at this stage", assertableDevice.getTresholdByName("alarm_high") == null);

//continue here  ... also with the components 
        // expected test outcomes:
        //what happens if the device is disabled
        ///!!!! logical problem: non-synced configuration which is not coming from the device need not to be removed, because it can be configured by the user;
        // it would be nice to decide how this stuff shoudl look like -> who is leading the configuration
        //why would overwrite a boot notification a confguration which may have been reset on purpose by the user lately
        //test it with persisted and non persisted device as well
        //remove transaction and request timeouts
    }

    @Test
    public void testEventSync() {
        int expectedNrOfEvents = 1;
        int expectedNrOfIndications = 2;
        Event evt = new Event().putId("some_event_id").putEventClass("some_class").putSeverity(Event.Severity.DEBUG).putEventDate(Calendar.getInstance()).
                addIndication("indication1", Value.Type.TEXT, "some value").addIndication("indication2", Value.Type.TEXT, "some other value");
        syncGw.process(new EventSync(testDevice.getIdentification(), testDevice.getIdentification(), evt, Calendar.getInstance().getTimeInMillis()));
        Assert.assertTrue("There should be " + expectedNrOfEvents + " events in the database at this stage.", getCount(Event.TABLE_NAME) == expectedNrOfEvents);
        Assert.assertTrue("There should be " + expectedNrOfIndications + " indications in the database at this stage.", getCount(Indication.TABLE_NAME) == expectedNrOfIndications);

    }
    //todo: test exceptions and error messages
    //http://xpadro.blogspot.co.at/2013/11/how-error-handling-works-in-spring.html

    private void anonymizeComponent(Component<?> component) {
        component.setId(null);
        if (component.getComponents() != null && !component.getComponents().isEmpty()) {
            component.getComponents().forEach(c -> c.setId(null));
        }
    }

}
