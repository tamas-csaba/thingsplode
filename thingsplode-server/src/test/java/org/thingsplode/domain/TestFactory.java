/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.domain;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;
import org.thingsplode.core.Address;
import org.thingsplode.core.EnabledState;
import org.thingsplode.core.Location;
import org.thingsplode.core.StatusInfo;
import org.thingsplode.core.Value;
import org.thingsplode.core.entities.Capability;
import org.thingsplode.core.entities.Component;
import org.thingsplode.core.entities.Configuration;
import org.thingsplode.core.entities.Device;
import org.thingsplode.core.entities.Model;
import org.thingsplode.core.entities.Treshold;
import org.thingsplode.core.ValueType;
import org.thingsplode.core.entities.Parameter;

/**
 *
 * @author tamas.csaba@gmail.com
 */
public class TestFactory {

    public static final String DEFAULT_SHUTDOWN_TIMEOUT = "3000";

    public static Device createDevice() throws UnknownHostException {
        return createDevice("default_test_device", "1231234235", "0");
    }

    public static Device createDevice(String deviceId, String serialNumber, String modelVersion) throws UnknownHostException {
        Device d = Device.create(deviceId, EnabledState.ENABLED, StatusInfo.OFFLINE);
        d.
                putSerialNumber(serialNumber).putPartNumber("123").
                putStatusInfo(StatusInfo.ONLINE).
                putIpAddress(InetAddress.getLocalHost()).
                putLastHeartbeat(Calendar.getInstance()).
                putLocation(Location.create("default", Address.create().putCity("some_city").putCountry("Some Country").putState("some state").putHouseNumber("54").putPostCode("434545")).putLatitude(100.0).putLongitude(123.4)).
                putModel(Model.create().putManufacturer("some_manufacturer").putType("some_type").putVersion(modelVersion)).
                putStartupDate(Calendar.getInstance()).addOrUpdateConfigurations(Configuration.create("shutdown_timeout", DEFAULT_SHUTDOWN_TIMEOUT, ValueType.NUMBER), Configuration.create("deletable_config", "1000", ValueType.NUMBER)).
                addCapabilities(Capability.CapabilityBuilder.newBuilder().
                        add("meter_value", Capability.Type.READ, true).
                        add("door_control", Capability.Type.WRITE_OR_EXECUTE, true, new Parameter("open", ValueType.BOOLEAN)).build()
                ).
                addComponents(Component.create("card_reader", Component.Type.HARDWARE).putEnabledState(EnabledState.ENABLED).putStatusInfo(StatusInfo.ONLINE).addOrUpdateConfigurations(Configuration.create("read_timeout", ValueType.NUMBER).putValue("20000")).
                        addTresholds(Treshold.create("nr_of_transactions", Treshold.Type.HIGH, Value.Type.NUMBER, "100000")).
                        addCapabilities(Capability.create(Capability.Type.WRITE_OR_EXECUTE, "read_card", true)).
                        addComponents(Component.create("EMC68", Component.Type.HARDWARE, EnabledState.ENABLED).addOrUpdateConfigurations(Configuration.create("chip_installed", "true", ValueType.BOOLEAN)).
                                putEnabledState(EnabledState.ENABLED).
                                putStatusInfo(StatusInfo.ONLINE)
                        )
                ).
                //addTresholds(Treshold.create("alarm_high", Treshold.Type.HIGH, Value.Type.NUMBER, "10000"), Treshold.create("alarm_low", Treshold.Type.LOW, Value.Type.NUMBER, "100")).
                addOrUpdateTresholds(Treshold.TresholdBuilder.newBuilder().
                        add("alarm_high", Treshold.Type.HIGH, Value.Type.NUMBER, "10000").
                        add("alarm_low", Treshold.Type.LOW, Value.Type.NUMBER, "100").build()).
                putType(Component.Type.HARDWARE);
        return d;
    }

}
