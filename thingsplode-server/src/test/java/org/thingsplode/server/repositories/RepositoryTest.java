/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.server.repositories;

import java.net.UnknownHostException;
import java.util.Calendar;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.thingsplode.TestBaseWithRepos;
import org.thingsplode.core.entities.Event;
import org.thingsplode.core.Value;
import org.thingsplode.core.entities.Component;
import org.thingsplode.core.entities.Configuration;
import org.thingsplode.core.entities.Device;
import org.thingsplode.core.entities.Indication;
import org.thingsplode.domain.TestFactory;
import org.thingsplode.server.BaseConfig;
import org.thingsplode.server.JpaConfig;

/**
 *
 * @author tamas.csaba@gmail.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {BaseConfig.class, JpaConfig.class})
@TestPropertySource("classpath:/test.properties")
//@ActiveProfiles({"dev", "integration"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
//@TestExecutionListeners(listeners = {}, mergeMode = MergeMode.MERGE_WITH_DEFAULTS)
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RepositoryTest extends TestBaseWithRepos {

    @Autowired(required = true)
    private DeviceRepository deviceRepo;
    @Autowired(required = true)
    private EventRepository eventRepo;
    @Autowired(required = true)
    private ComponentRepository componentRepo;

    public RepositoryTest() {
        super();
    }

//    public RepositoryTest(String testName) {
//        super(testName);
//    }
    @Test()
    public void test1Basics() throws UnknownHostException {
        Device d1 = TestFactory.createDevice("test_device_1", "1231234235", "1");
        Device d2 = TestFactory.createDevice("test_device_2", "1231234236", "2");

        d1 = deviceRepo.save(d1);
        d2 = deviceRepo.save(d2);

        deviceAssertions(2);

        deviceRepo.delete(d1);
        deviceAssertions(1);

        deviceRepo.delete(d2);
        deviceAssertions(0);
    }

    @Test
    public void testDeleteConfigs() throws UnknownHostException {
        Device d1 = deviceRepo.save(TestFactory.createDevice("test_device_1", "1231234235", "1"));
        d1 = deviceRepo.save(d1);
        //d1.getConfiguration().removeAll(d1.getConfiguration());
        d1.getConfiguration().clear();
        d1 = deviceRepo.save(d1);
        int orphanConfigs = this.getCountWhere(Configuration.TABLE_NAME, Component.COMP_REF + " is null");
        Assert.assertTrue("the orphan configs should be null", orphanConfigs == 0);
        d1.getComponents().forEach((c) -> {
            System.out.println("=============> Clearing configurations for component: " + c.getName());
            c.getConfiguration().clear();
        });
        d1 = deviceRepo.save(d1);
        Assert.assertTrue("the orphan configs should be null", orphanConfigs == 0);
    }

    @Test
    @Transactional
    public void test2Events() throws UnknownHostException {
        String deviceID = "test_device_1";
        String serialNumber = "12345";
        deviceRepo.save(TestFactory.createDevice(deviceID, serialNumber, "1"));
        Device d = deviceRepo.findBydeviceId(deviceID);
        Assert.assertTrue("The serial number should match", serialNumber.equalsIgnoreCase(d.getSerialNumber()));
        Assert.assertTrue("The version should be 1", "1".equalsIgnoreCase(d.getModel().getVersion()));
        d.getModel().setVersion("2");
        deviceRepo.save(d);
        d = deviceRepo.findBydeviceId(deviceID);
        Assert.assertTrue("The version should be 2", "2".equalsIgnoreCase(d.getModel().getVersion()));
        Assert.assertNotNull("The device id shall not be null at this stage", d.getId());
        for (int i = 1; i <= 100; i++) {
            Event devt = Event.create("some-special-event", "some-special-event-class", Event.Severity.INFO, Calendar.getInstance()).putComponent(d).putReceiveDate(Calendar.getInstance()).
                    addIndications(Indication.create("peak", Value.Type.NUMBER, Integer.toString(i)));
            Event cevt = Event.create("a component event", "comp event class", Event.Severity.ERROR, Calendar.getInstance()).putComponent((Component) d.getComponents().toArray()[0]).putReceiveDate(Calendar.getInstance()).
                    addIndications(Indication.create("peak", Value.Type.TEXT, "componnent indication"));
            eventRepo.save(devt);
            eventRepo.save(cevt);
        }

        int evtCount = (int) eventRepo.count();
        Assert.assertTrue("There should be 200 device events in the database at this stage, but there were: " + evtCount + ".", evtCount == 200);
        Page<Event> deviceEvtPage = eventRepo.findByComponent(d, new PageRequest(0, 300));
        int deviceEvtCount = deviceEvtPage.getContent().size();
        Assert.assertTrue("there should be 100 device events instead of [" + deviceEvtCount + "]", deviceEvtCount == 100);
        eventRepo.deleteAll();
        deviceRepo.delete(d);
        deviceAssertions(0);
    }

    private void deviceAssertions(int expectedNrOfDevices) {
        long deviceCnt = deviceRepo.count();
        Assert.assertTrue("There should be " + expectedNrOfDevices + " devices in the database at this stage instead [" + deviceCnt + "].", deviceCnt == expectedNrOfDevices);
        Assert.assertTrue("There should be " + expectedNrOfDevices + " models in the database at this stage.", getCount("MODEL") == expectedNrOfDevices);
        Assert.assertTrue("There should be " + expectedNrOfDevices * 2 + " tresholds in the database at this stage.", getCount("TRESHOLD") == expectedNrOfDevices * 2);
        Assert.assertTrue("There should be " + expectedNrOfDevices * 3 + " configruations in the database at this stage.", getCount("CONFIGURATION") == expectedNrOfDevices * 4);
        int compNumber = componentRepo.findbyMainType(Component.MAIN_TYPE).size();
        Assert.assertTrue("There should be " + expectedNrOfDevices * 2 + " components in the database at this stage instead of [" + compNumber + "].", compNumber == expectedNrOfDevices * 2);
        Assert.assertTrue("There should be " + expectedNrOfDevices * 3 + " capabilities in the database at this stage.", getCount("CAPABILITY") == expectedNrOfDevices * 3);
    }
}
