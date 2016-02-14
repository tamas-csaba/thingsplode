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
package org.thingsplode.connect.core;

/**
 *
 * @author tamas.csaba@gmail.com
 */
public class Transport {

    public enum TransportType {

        /**
         * Default 
         */
        SOCKET,
        /**
         * Unix Domain Socket - useful for host only IPC style communication
         */
        DOMAIN_SOCKET;
    }
    private final TransportType transportType;

    public Transport(TransportType transportType) {
        this.transportType = transportType;
    }

    public TransportType getTransportType() {
        return transportType;
    }

}
