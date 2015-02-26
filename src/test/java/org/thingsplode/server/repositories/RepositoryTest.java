/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.server.repositories;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.thingsplode.TestBase;
import org.thingsplode.core.domain.Address;
import org.thingsplode.core.domain.EnabledState;
import org.thingsplode.core.domain.Location;
import org.thingsplode.core.domain.entities.Model;
import org.thingsplode.core.domain.StatusInfo;
import org.thingsplode.core.domain.entities.Capability;
import org.thingsplode.core.domain.entities.Component;
import org.thingsplode.core.domain.entities.Configuration;
import org.thingsplode.core.domain.entities.Device;
import org.thingsplode.core.domain.entities.Event;
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
public class RepositoryTest extends TestBase {

    @Autowired
    private DeviceRepository deviceRepo;

    public RepositoryTest(){
        super();
    }
    
//    public RepositoryTest(String testName) {
//        super(testName);
//    }

    @Test
    public void testBasics() throws UnknownHostException {
        Device d = Device.create("test_device", EnabledState.ENABLED, StatusInfo.OFFLINE);
        d.
                putSerialNumber("1231234235").putPartNumber("123").
                putIpAddress(InetAddress.getLocalHost()).
                putLastHeartbeat(Calendar.getInstance()).
                putLocation(Location.create("default", Address.create().putCity("some_city").putCountry("Some Country").putState("some state").putHouseNumber("54").putPostCode("434545")).putLatitude(100.0).putLongitude(123.4)).
                putModel(Model.create().putManufacturer("some_manifacturer").putType("some_type").putVersion("12123213")).
                putStartupDate(Calendar.getInstance()).
                addCapabilities(Capability.create(Capability.Type.READ,"meter_value",true)).
                addCapabilities(Capability.create(Capability.Type.WRITE_OR_EXECUTE,"door_control",true)).
                addComponents(Component.create("card_reader", Component.Type.HARDWARE).putEnabledState(EnabledState.ENABLED).putStatusInfo(StatusInfo.ONLINE).
                        addConfigurations(Configuration.create("read_timeout", Configuration.Type.NUMBER).putValue("20000")).
                        addEvents(Event.create())
                );
                
        deviceRepo.save(d);
    }
}
