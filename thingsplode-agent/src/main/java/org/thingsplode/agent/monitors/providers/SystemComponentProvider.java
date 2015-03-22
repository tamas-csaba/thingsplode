/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.agent.monitors.providers;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.thingsplode.agent.structures.StaticProvider;
import org.thingsplode.core.EnabledState;
import org.thingsplode.core.StatusInfo;
import org.thingsplode.core.Value;
import org.thingsplode.core.entities.Capability;
import org.thingsplode.core.entities.Component;

/**
 *
 * @author tamas.csaba@gmail.com
 */
@org.springframework.stereotype.Component
public class SystemComponentProvider extends StaticProvider<Component> {

    @Override
    public Collection<Component> collect() {
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
        computer.addComponents((Component[]) getStorage().toArray());
        components.add(computer);

        return components;
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
