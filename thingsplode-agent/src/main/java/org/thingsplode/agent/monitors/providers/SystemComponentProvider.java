/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.agent.monitors.providers;

import java.io.File;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.thingsplode.agent.infrastructure.EventQueueManager;
import org.thingsplode.agent.infrastructure.StaticProvider;
import org.thingsplode.core.EnabledState;
import org.thingsplode.core.StatusInfo;
import org.thingsplode.core.Value;
import org.thingsplode.core.entities.Capability;
import org.thingsplode.core.entities.Capability.CapabilityBuilder;
import org.thingsplode.core.entities.Component;
import org.thingsplode.core.entities.Event;

/**
 *
 * @author tamas.csaba@gmail.com
 */
@org.springframework.stereotype.Component
public class SystemComponentProvider extends StaticProvider<Component> {

    private static final Logger logger = Logger.getLogger(SystemComponentProvider.class);

    @Autowired(required = true)
    private EventQueueManager eventQueueManager;

    @Override
    public List<Component> collect() {
        List<Component> components = new ArrayList<>();

        components.add(Component.create("java_vm", Component.Type.SOFTWARE, EnabledState.ENABLED).addCapabilities(
                Capability.CapabilityBuilder.newBuilder().
                add("os_data_model", Capability.Type.READ, true, Value.Type.TEXT, System.getProperty("sun.arch.data.model")).
                add("java_vm_version", Capability.Type.READ, true, Value.Type.TEXT, System.getProperty("java.vm.version")).
                add("java_home", Capability.Type.READ, true, Value.Type.TEXT, System.getProperty("java.home")).
                add("java_vm_vendor", Capability.Type.READ, true, Value.Type.TEXT, System.getProperty("java.vm.vendor")).
                add("max_jvm_memory", Capability.Type.READ, true, Value.Type.TEXT, String.valueOf(Runtime.getRuntime().maxMemory())).
                build()
        ));

        components.add(Component.create("os", Component.Type.SOFTWARE, EnabledState.ENABLED).addCapabilities(
                Capability.CapabilityBuilder.newBuilder().
                add("os_name", Capability.Type.READ, true, Value.Type.TEXT, System.getProperty("os.name")).
                add("os_arch", Capability.Type.READ, true, Value.Type.TEXT, System.getProperty("os.arch")).
                add("os_version", Capability.Type.READ, true, Value.Type.TEXT, System.getProperty("os.version")).
                build()
        ));

        Component computer = Component.create("computing_unit", Component.Type.HARDWARE, EnabledState.ENABLED).addCapabilities(
                Capability.CapabilityBuilder.newBuilder().
                add("cpus", Capability.Type.READ, true, Value.Type.NUMBER, String.valueOf(Runtime.getRuntime().availableProcessors())).
                build()
        );
        computer.addComponents(getStorage());
        computer.addComponents(getNetwork());
        components.add(computer);

        return components;
    }

    private List<Component> getNetwork() {
        try {
            List<Component> networkComps = new ArrayList<>();
            Collections.list(NetworkInterface.getNetworkInterfaces()).forEach(n -> {
                try {

                    CapabilityBuilder cb = Capability.CapabilityBuilder.newBuilder();
                    cb.add("hw_address", Capability.Type.READ, true, Value.Type.TEXT, Arrays.toString(n.getHardwareAddress())).
                            add("hw_index", Capability.Type.READ, true, Value.Type.TEXT, String.valueOf(n.getIndex()));

                    if (n.getInetAddresses() != null) {
                        Collections.list(n.getInetAddresses()).forEach(ia -> {
                            cb.add("host_address", Capability.Type.READ, true, Value.Type.TEXT, ia.getHostAddress());
                        });
                    }

                    Component nic = Component.create(n.getDisplayName(), Component.Type.HARDWARE, EnabledState.ENABLED).
                            putStatusInfo(n.isUp() ? StatusInfo.ONLINE : StatusInfo.OFFLINE).addCapabilities(cb.build());

                    networkComps.add(nic);

                } catch (SocketException ex) {
                    logAndSendError(ex);
                }
            });
            return networkComps;
        } catch (SocketException ex) {
            logAndSendError(ex);
            return null;
        }
    }

    private void logAndSendError(Exception ex) {
        String e = String.format("%s error caught while reading networks states with message: %s", ex.getClass().getSimpleName(), ex.getMessage());
        logger.error(e, ex);
        eventQueueManager.sendFaultEvent(Event.Severity.WARNING, e);
    }

    private List<Component> getStorage() {
        List<Component> storageComps = new ArrayList<>();
        Arrays.asList(File.listRoots()).forEach(r -> {
            Component rootFsComp = Component.create(r.getName(), Component.Type.HARDWARE, EnabledState.ENABLED);
            rootFsComp.putStatusInfo(StatusInfo.ONLINE).addCapabilities(
                    Capability.CapabilityBuilder.newBuilder().
                    add("absolut_path", Capability.Type.READ, true, Value.Type.TEXT, r.getAbsolutePath()).
                    add("free_space", Capability.Type.READ, true, Value.Type.NUMBER, String.valueOf(r.getFreeSpace())).
                    add("total_space", Capability.Type.READ, true, Value.Type.NUMBER, String.valueOf(r.getTotalSpace())).
                    add("can_read", Capability.Type.READ, true, Value.Type.BOOLEAN, String.valueOf(r.canRead())).
                    add("can_write", Capability.Type.READ, true, Value.Type.BOOLEAN, String.valueOf(r.canWrite())).
                    build()
            );
            storageComps.add(rootFsComp);
        });

        return storageComps;
    }

}
