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

import com.acme.synapse.testdata.services.CrudTestEndpointService;
import com.acme.synapse.testdata.services.DummyMarkedEndpoint;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import com.acme.synapse.testdata.services.RpcEndpointImpl;
import com.acme.synapse.testdata.services.TestSecondEndpointService;

/**
 *
 * @author tamas.csaba@gmail.com
 */
public class InternalServiceRegistryTest {

    private InternalServiceRegistry registry = new InternalServiceRegistry();
    
    public InternalServiceRegistryTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testRegistration() {
        registry.register(new RpcEndpointImpl());
        registry.register(new DummyMarkedEndpoint());
        registry.register(new TestSecondEndpointService());
        registry.register(new CrudTestEndpointService());
    }
}
