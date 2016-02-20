/*
 * Copyright 2016 tamas.csaba@gmail.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.thingsplode.synapse.endpoint.srvreg;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;
import org.thingsplode.synapse.core.SynapseEndpointServiceMarker;
import org.thingsplode.synapse.core.Uri;
import org.thingsplode.synapse.core.annotations.PathVariable;
import org.thingsplode.synapse.core.annotations.RequestBody;
import org.thingsplode.synapse.core.annotations.RequestMapping;
import org.thingsplode.synapse.core.annotations.RequestParam;
import org.thingsplode.synapse.core.annotations.Service;
import org.thingsplode.synapse.core.domain.AbstractMessage;
import org.thingsplode.synapse.core.domain.RequestMethod;
import org.thingsplode.synapse.util.Util;

/**
 *
 * @author tamas.csaba@gmail.com
 */
class InternalServiceRegistry {

    private Routes routes;

    class Routes {

        private Map<String, SortedMap<String, MethodContext>> rootCtxes = new HashMap<>();
        private Set<String> paths = new HashSet<>();

        synchronized void put(String rootCtx, String methodName, MethodContext mc) {
            String rootRegexp = rootCtx.replaceAll("\\{.*\\}", ".*");
            methodName = methodName.startsWith("/") ? methodName.substring(1) : methodName;
            SortedMap<String, MethodContext> rootCtxMethods = rootCtxes.get(rootRegexp);
            if (rootCtxMethods != null) {
                rootCtxMethods.put(methodName, mc);
            } else {
                SortedMap<String, MethodContext> map = new TreeMap<>();
                map.put(methodName, mc);
                rootCtxes.put(rootRegexp, map);
            }
            paths.add(rootCtx + "/" + methodName);
        }
    }

    public InternalServiceRegistry() {
        routes = new Routes();
    }

    public void register(Object serviceInstance) {
        Class<?> srvClass = serviceInstance.getClass();
        String rootContext;
        List<Class> markedInterfaces = getMarkedInterfaces(srvClass.getInterfaces());
        Set<Method> methods = new HashSet<>();
        if (!srvClass.isAnnotationPresent(Service.class)) {
            if ((markedInterfaces == null || markedInterfaces.isEmpty())) {
                throw new IllegalArgumentException("The service instance must be annotated with: " + Service.class.getSimpleName() + "or the " + SynapseEndpointServiceMarker.class.getSimpleName() + " marker interface must be used.");
            }
            //Marked with marker interface
            rootContext = "/" + srvClass.getCanonicalName().replaceAll("\\.", "/");
            markedInterfaces.forEach(i -> {
                methods.addAll(Arrays.asList(i.getMethods()));
            });
            methods.addAll(Arrays.asList(srvClass.getDeclaredMethods())
                    .stream()
                    .filter(m -> Modifier.isPublic(m.getModifiers()))
                    .collect(Collectors.toList()));
        } else {
            //Annotated with @Service
            rootContext = srvClass.getAnnotation(Service.class).value();
            methods.addAll(Arrays.asList(srvClass.getMethods()).stream()
                    .filter(m -> {
                        return m.isAnnotationPresent(RequestMapping.class) || containsMessageClass(m.getParameterTypes()) || AbstractMessage.class.isAssignableFrom(m.getReturnType());
                    })
                    .collect(Collectors.toList()));
        }

        if (rootContext.endsWith("/")) {
            rootContext = rootContext.substring(0, rootContext.length() - 1);
        }
        if (Util.isEmpty(rootContext)) {
            rootContext = "/";
        }

        populateMethods(rootContext, methods);
        System.out.println("sss");
    }

    public MethodContext getMethodContext(Uri uri) {
        return null;
    }

    private void populateMethods(String rootCtx, Set<Method> methods) {

        methods.stream().forEach((m) -> {
            MethodContext mc;
            mc = new MethodContext(rootCtx, m);
            mc.parameters.addAll(processParameters(m));

            if (m.isAnnotationPresent(RequestMapping.class)) {
                RequestMapping rm = m.getAnnotation(RequestMapping.class);
                Arrays.asList(rm.value()).forEach(ru -> {
                    mc.requestMethods.addAll(Arrays.asList(rm.method()));
                    routes.put(rootCtx, ru, mc);

                });
            } else {
                routes.put(rootCtx, m.getName(), mc);
            }
        });
    }

    private List<MethodParam> processParameters(Method m) {
        List<MethodParam> mps = new ArrayList<>();
        if (m.getParameterCount() > 0) {
            Arrays.asList(m.getParameters()).forEach((Parameter p) -> {
                boolean required = true;
                MethodParam<?> mp;

                if (p.isAnnotationPresent(RequestParam.class)) {
                    mp = new MethodParam(p, MethodParam.ParameterSource.URI_PARAM, p.getAnnotation(RequestParam.class).value());
                    required = p.getAnnotation(RequestParam.class).required();
                    if (!Util.isEmpty(p.getAnnotation(RequestParam.class).defaultValue())) {
                        Class valueClass = p.getType();
                        mp.defaultValue = generateDefaultValue(valueClass, p.getAnnotation(RequestParam.class).defaultValue());
                        mp.defaultValueClass = valueClass;
                    }
                } else if (p.isAnnotationPresent(RequestBody.class)) {
                    mp = new MethodParam(p, MethodParam.ParameterSource.BODY, p.getName());
                    required = p.getAnnotation(RequestBody.class).required();
                } else if (p.isAnnotationPresent(PathVariable.class)) {
                    mp = new MethodParam(p, MethodParam.ParameterSource.PATH_VARIABLE, p.getAnnotation(PathVariable.class).value());
                } else if (AbstractMessage.class.isAssignableFrom(p.getType())) {
                    mp = new MethodParam(p, MethodParam.ParameterSource.BODY, p.getName());
                } else {
                    mp = new MethodParam(p, MethodParam.ParameterSource.URI_PARAM, p.getName());
                }
                mp.required = required;
                mp.parameter = p;
                mps.add(mp);
            });
        }
        return mps;
    }

    private boolean containsMessageClass(Class<?>[] array) {
        Optional<Class<?>> result = Arrays.asList(array).stream().filter(c -> AbstractMessage.class.isAssignableFrom(c)).findFirst();
        return result.isPresent();
    }

    private List<Class> getMarkedInterfaces(Class<?>[] ifaces) {
        if (ifaces == null || ifaces.length == 0) {
            return null;
        }
        return Arrays.asList(ifaces).stream().filter(i -> SynapseEndpointServiceMarker.class.isAssignableFrom(i)).collect(Collectors.toList());
    }

    private <T> T generateDefaultValue(Class<T> type, String sValue) {
        if (type.equals(Integer.class)) {
            return type.cast(Integer.parseInt(sValue));
        } else if (type.equals(Long.class)) {
            return type.cast(Long.parseLong(sValue));
        } else if (type.equals(String.class)) {
            return type.cast(sValue);
        }
        return null;
    }

    class MethodContext {

        String rootCtx;
        Method method;
        List<RequestMethod> requestMethods = new ArrayList<>();
        List<MethodParam> parameters = new ArrayList<>();

        public MethodContext(String rootCtx, Method method) {
            this.rootCtx = rootCtx;
            this.method = method;
            this.requestMethods = new ArrayList<>();
        }

        public MethodContext(String rootCtx, Method method, List<RequestMethod> requestMethods) {
            if (requestMethods != null) {
                this.rootCtx = rootCtx;
                this.method = method;
                this.requestMethods.addAll(requestMethods);
            } else {
                this.rootCtx = rootCtx;
                this.method = method;
            }
        }

        void addRequestMethod(RequestMethod rm) {
            requestMethods.add(rm);
        }

        void addRequestMethods(List<RequestMethod> rms) {
            requestMethods.addAll(rms);
        }

        void addRequestMethods(RequestMethod[] rms) {
            addRequestMethods(Arrays.asList(rms));
        }
    }

}
