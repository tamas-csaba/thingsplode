package org.thingsplode.agent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thingsplode.TestBase;
import org.thingsplode.agent.monitors.providers.SystemComponentProvider;

/**
 * Unit test for simple App.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AgentConfig.class})
@TestPropertySource("classpath:/test.properties")
public class AgentTest extends TestBase {

    @Autowired(required = true)
    private SystemComponentProvider systemComponentProvider;

    @Test
    public void testSystemComponentProvider() {
        listACollection("Component", systemComponentProvider.collect());
    }
}
