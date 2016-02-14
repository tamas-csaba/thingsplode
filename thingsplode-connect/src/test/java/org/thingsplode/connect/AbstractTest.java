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
package org.thingsplode.connect;

import java.net.InetSocketAddress;
import org.junit.Rule;
import org.junit.rules.ExternalResource;
import org.thingsplode.connect.core.TestEndpoint;
import org.thingsplode.connect.core.Transport;
import org.thingsplode.connect.endpoint.Endpoint;
import org.thingsplode.connect.services.TestEndpointService;

/**
 *
 * @author tamas.csaba@gmail.com
 */
public abstract class AbstractTest {

    private Endpoint ep;

    @Rule
    public ExternalResource resource = new ExternalResource() {

        @Override
        protected void before() throws InterruptedException {
            TestEndpoint remoteService = new TestEndpointService();
            ep = Endpoint.create("test", new Transport(Transport.TransportType.SOCKET, new InetSocketAddress("0.0.0.0", 0))).publish("test_service", remoteService);
        }

        @Override
        protected void after() {
        }
    };

    public Endpoint getEndpoint() {
        return ep;
    }
}
