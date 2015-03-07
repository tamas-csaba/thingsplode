/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.testutils;

import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thingsplode.TestBase;
import org.thingsplode.server.BaseConfig;

/**
 *
 * @author tamas.csaba@gmail.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {BaseConfig.class})
@TestPropertySource("classpath:/test.properties")
//@PropertySource(value = {"classpath:/test.properties"}, ignoreResourceNotFound = false)
public class PopertyTest extends TestBase {

    @Value("${text.value:default}")
    private String valueObject;

    public PopertyTest() {
        super();
    }

    
    @Test()
    public void testProps() {
        System.out.println("Test Property ---------> [" + valueObject + "]");
        Assert.assertEquals("It should be a", "a", valueObject);
    }

}
