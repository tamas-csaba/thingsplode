/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import junit.framework.TestCase;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author tamas.csaba@gmail.com
 */
@Ignore
public abstract class TestBase extends TestCase {

    @Autowired
    private ApplicationContext applicationContext;

    public TestBase() {
        super();
        Logger.getRootLogger().removeAllAppenders();
        BasicConfigurator.configure();
        Logger.getRootLogger().setLevel(Level.DEBUG);
        Logger.getLogger(org.springframework.beans.factory.support.DefaultListableBeanFactory.class).setLevel(Level.DEBUG);
        Logger.getLogger(org.springframework.beans.factory.xml.XmlBeanDefinitionReader.class).setLevel(Level.INFO);
        Logger.getLogger("org.springframework.beans.factory.config").setLevel(Level.TRACE);
        Logger.getLogger(org.springframework.test.context.TestContextManager.class).setLevel(Level.DEBUG);
        Logger.getLogger("org.hibernate.cfg").setLevel(Level.ERROR);
        Logger.getLogger("org.hibernate.validator").setLevel(Level.ERROR);
        Logger.getLogger("org.hibernate.id").setLevel(Level.ERROR);
        Logger.getLogger(org.hibernate.cfg.annotations.PropertyBinder.class).setLevel(Level.WARN);
        Logger.getLogger(org.hibernate.cfg.annotations.SimpleValueBinder.class).setLevel(Level.WARN);
        Logger.getLogger(org.hibernate.cfg.annotations.TableBinder.class).setLevel(Level.WARN);
        Logger.getLogger(org.hibernate.validator.engine.resolver.DefaultTraversableResolver.class).setLevel(Level.WARN);
        Logger.getLogger(org.hibernate.validator.metadata.ConstraintDescriptorImpl.class).setLevel(Level.WARN);
        //Logger.getLogger(org.hibernate.id.factory.DefaultIdentifierGeneratorFactory.class).setLevel(Level.WARN);
        Logger.getLogger(org.hibernate.cfg.Configuration.class).setLevel(Level.WARN);
        Logger.getLogger(org.hibernate.persister.entity.AbstractEntityPersister.class).setLevel(Level.WARN);
        Logger.getLogger(org.hibernate.loader.collection.OneToManyLoader.class).setLevel(Level.WARN);
        Logger.getLogger(org.hibernate.loader.collection.OneToManyLoader.class).setLevel(Level.WARN);
        Logger.getLogger(org.hibernate.cfg.Ejb3Column.class).setLevel(Level.WARN);
        //Logger.getLogger(org.hibernate.cfg.search.HibernateSearchEventListenerRegister.class).setLevel(Level.WARN);
        Logger.getLogger(org.hibernate.validator.xml.ValidationXmlParser.class).setLevel(Level.WARN);
        Logger.getLogger(com.mchange.v2.resourcepool.BasicResourcePoolFactory.class).setLevel(Level.WARN);
        Logger.getLogger(com.mchange.v2.async.ThreadPoolAsynchronousRunner.class).setLevel(Level.WARN);
        Logger.getLogger(org.hibernate.tool.hbm2ddl.SchemaExport.class).setLevel(Level.INFO);
        Logger.getLogger("com.mchange.v2.c3p0").setLevel(Level.INFO);
        Logger.getLogger("com.mchange.v2.resourcepool").setLevel(Level.INFO);
        Logger.getLogger("org.hibernate.persister.walking").setLevel(Level.INFO);
        Logger.getLogger("org.hibernate.type.BasicTypeRegistry").setLevel(Level.INFO);
        
        
        
        
    }

    /**
     * Similar in concept to the {@link #listACollection(java.lang.String, java.util.Collection)
     * } but works with arrays.
     *
     * @param prefix a prefix which you want to be listed before each line
     * @param array the array to be listed
     * @see #listACollection(java.lang.String, java.util.Collection)
     */
    public static void listACollection(String prefix, Object[] array) {
        listACollection(prefix, Arrays.asList(array));
    }

    /**
     * A lot of times you need to list a collection of something for debugging
     * purposes.<br>
     * Isn't it boring to write a display code all the time? <br>
     * This is what this method provides for you.
     *
     * @param prefix - a prefix string which will appear before any of the
     * elements from the list will be listed
     * @param collection - the collection of whose elements you want to list
     */
    public static void listACollection(String prefix, Collection collection) {
        if (collection != null) {
            System.out.println("\n*** Elements of a collection ***\n");
            for (Object thisObject : collection) {
                System.out.println("{" + (prefix != null ? (prefix + " :> ") : "--> ") + (thisObject != null ? thisObject.toString() : "<NULL>") + "}");
            }
            System.out.println("----------------------------------");
        } else {
            System.out.println("***\n<EMPTY COLLECTION, NOTHING TO SHOW.>\n***");
        }
    }

    /**
     * A utility method for enabling to access private object on some classes.
     * This is a very helpful method to be able to do assertions on instances,
     * which do have a private field and has not accessible accessor methods for
     * it. In such cases you can access with this method that private object and
     * you can check its value.
     *
     * @param instance the instance which does have the private object you want
     * to verify
     * @param fieldName the name of the private field which you want to check
     * (please note, is case sensitive)
     * @return the Object reference representing the private field
     * @throws Exception if something goes wrong in the accessibility process
     */
    public static Object getPrivateObjectFromInstance(Object instance, String fieldName) throws Exception {
        Field[] fields = returnAllFieldsOfAClass(instance.getClass());
        for (Field thisField : fields) {
            if (thisField.getName().equals(fieldName)) {
                try {
                    thisField.setAccessible(true);
                    Object fieldReference = thisField.get(instance);
                    return fieldReference;
                } catch (Exception ex) {
                    throw new Exception(ex.getMessage(), ex);
                }
            }
        }
        throw new Exception("The field identified by field name [" + fieldName + "] can not be accessed on the provided object.");
    }

    /**
     * Similar to the {@link #getPrivateObjectFromInstance(java.lang.Object, java.lang.String)
     * } in concept. It can be used to access some private methods for checking
     * them from your unit tests.
     *
     * @param instance the instance of which you'd like to invoke the method
     * @param methodName the name of the method (please note: its case
     * sensitive)
     * @param methodArguments the arguments which you'd like to use for the
     * method call
     * @return the return value for the method.
     */
    public static Object getResultOfPrivateMethodInvokation(Object instance, String methodName, Object... methodArguments) throws Exception {
        Method[] methods = returnAllMethodsOfAClass(instance.getClass());
        for (Method thisMethod : methods) {
            if (thisMethod.getName().equals(methodName)) {
                boolean thatsTheMethod = false;
                Class[] parameterTypes = thisMethod.getParameterTypes();
                if (methodArguments != null && methodArguments.length > 0 && parameterTypes != null && parameterTypes.length > 0) {
                    thatsTheMethod = true;
                } else if (methodArguments == null && parameterTypes == null) {
                    thatsTheMethod = true;
                } else if (existsOnlyOnce(extractMethodNames(methods), methodName)) {
                    thatsTheMethod = true;
                }

                if (thatsTheMethod) {
                    try {
                        thisMethod.setAccessible(true);
                        Object methodReturnValue = thisMethod.invoke(instance, methodArguments);
                        return methodReturnValue;
                    } catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                        throw new Exception(ex.getMessage(), ex);
                    }
                }
            }
        }
        throw new Exception("The required method [" + methodName + "] with the given parameters can not be invoked on the given instance of type [" + instance.getClass().getSimpleName() + "]");
    }

    public static Method[] returnAllMethodsOfAClass(Class someClass) {
        ArrayList<Method> allMethods = new ArrayList<>();
        Collections.addAll(allMethods, someClass.getDeclaredMethods());
        if (someClass.getSuperclass() != null) {
            Collections.addAll(allMethods, returnAllMethodsOfAClass(someClass.getSuperclass()));
        }
        return allMethods.toArray(new Method[allMethods.size()]);

    }

    public static Field[] returnAllFieldsOfAClass(Class someClass) {
        ArrayList<Field> allFields = new ArrayList<>();
        Collections.addAll(allFields, someClass.getDeclaredFields());
        if (someClass.getSuperclass() != null) {
            Collections.addAll(allFields, returnAllFieldsOfAClass(someClass.getSuperclass()));
        }
        return allFields.toArray(new Field[allFields.size()]);
    }

    public static String[] extractMethodNames(Method[] methods) {
        ArrayList<String> methodNames = new ArrayList<>();
        for (Method thisMethod : methods) {
            methodNames.add(thisMethod.getName());
        }
        return methodNames.toArray(new String[methodNames.size()]);
    }

    /**
     * Checks that in a list of strings a specified one exists only once
     *
     * @param theList the String array list
     * @param criteria the supposed to be unique String
     * @return true if exists only once
     */
    public static boolean existsOnlyOnce(String[] theList, String criteria) {
        boolean once = false;
        boolean twice = false;
        for (String verifyable : theList) {
            if (verifyable.equals(criteria)) {
                if (once) {
                    twice = true;
                } else {
                    once = true;
                }

            }
        }
        return once & twice;
    }
}
