/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Ignore;

/**
 *
 * @author Csaba Tamas
 */
@Ignore()
public abstract class TestBase extends AbstractTestRoot {

    public TestBase() {
        super();
        Logger.getRootLogger().removeAllAppenders();
        BasicConfigurator.configure();
        Logger.getRootLogger().setLevel(Level.DEBUG);
        Logger.getLogger(org.springframework.beans.factory.support.DefaultListableBeanFactory.class).setLevel(Level.DEBUG);
        Logger.getLogger(org.springframework.beans.factory.xml.XmlBeanDefinitionReader.class).setLevel(Level.INFO);
        Logger.getLogger("org.springframework.beans.factory.config").setLevel(Level.TRACE);
        //Logger.getLogger(org.springframework.test.context.TestContextManager.class).setLevel(Level.DEBUG);
    }

}
