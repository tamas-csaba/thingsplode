/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.domain;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;
import org.thingsplode.core.domain.Address;
import org.thingsplode.core.domain.EnabledState;
import org.thingsplode.core.domain.Event;
import org.thingsplode.core.domain.Location;
import org.thingsplode.core.domain.StatusInfo;
import org.thingsplode.core.domain.Value;
import org.thingsplode.core.domain.entities.Capability;
import org.thingsplode.core.domain.entities.Component;
import org.thingsplode.core.domain.entities.ComponentEvent;
import org.thingsplode.core.domain.entities.Configuration;
import org.thingsplode.core.domain.entities.Device;
import org.thingsplode.core.domain.entities.DeviceEvent;
import org.thingsplode.core.domain.entities.Indication;
import org.thingsplode.core.domain.entities.Model;
import org.thingsplode.core.domain.entities.Treshold;

/**
 *
 * @author tamas.csaba@gmail.com
 */
public class TestFactory {

    public static Device createDevice() throws UnknownHostException {
        return createDevice("default_test_device", "1231234235");
    }

    public static Device createDevice(String deviceId, String serialNumber) throws UnknownHostException {
        Device d = Device.create(deviceId, EnabledState.ENABLED, StatusInfo.OFFLINE);
        d.
                putSerialNumber(serialNumber).putPartNumber("123").
                putStatusInfo(StatusInfo.ONLINE).
                putIpAddress(InetAddress.getLocalHost()).
                putLastHeartbeat(Calendar.getInstance()).
                putLocation(Location.create("default", Address.create().putCity("some_city").putCountry("Some Country").putState("some state").putHouseNumber("54").putPostCode("434545")).putLatitude(100.0).putLongitude(123.4)).
                putModel(Model.create().putManufacturer("some_manufacturer").putType("some_type").putVersion("12123213")).
                putStartupDate(Calendar.getInstance()).
                addConfigurations(Configuration.create("shutdown_timeout", "3000", Configuration.Type.NUMBER)).
                addCapabilities(Capability.create(Capability.Type.READ, "meter_value", true)).
                addCapabilities(Capability.create(Capability.Type.WRITE_OR_EXECUTE, "door_control", true)).
                addComponents(Component.create("card_reader", Component.Type.HARDWARE).putEnabledState(EnabledState.ENABLED).putStatusInfo(StatusInfo.ONLINE).
                        addConfigurations(Configuration.create("read_timeout", Configuration.Type.NUMBER).putValue("20000")).
                        addEvents((ComponentEvent) ComponentEvent.create("some_event", "some_event_class", Event.Severity.ERROR, Calendar.getInstance()).
                                addIndications(Indication.create("evendInd", Value.Type.TEXT, "some text value"))
                        ).
                        addTresholds(Treshold.create("nr_of_transactions", Treshold.Type.HIGH, Value.Type.NUMBER, "100000")).
                        addCapabilities(Capability.create(Capability.Type.WRITE_OR_EXECUTE, "read_card", true)).
                        addSubComponents(Component.create("EMC68", Component.Type.HARDWARE, EnabledState.ENABLED).
                                addConfigurations(Configuration.create("chip_installed", "true", Configuration.Type.BOOLEAN)).
                                putEnabledState(EnabledState.ENABLED).
                                putStatusInfo(StatusInfo.ONLINE)
                        )
                ).
                addEvents((DeviceEvent) DeviceEvent.create("some_device_event", "some_event_class", Event.Severity.WARNING, Calendar.getInstance()).
                        addIndications(Indication.create("some other indication", Value.Type.NUMBER, "300"))).
                addTresholds(Treshold.create("alarm", Treshold.Type.HIGH, Value.Type.NUMBER, "10000"));

        return d;
    }

}
