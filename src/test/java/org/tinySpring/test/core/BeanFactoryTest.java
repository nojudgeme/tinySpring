package org.tinySpring.test.core;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.tinySpring.beans.BeanDefinition;
import org.tinySpring.beans.core.io.ClassPathResource;
import org.tinySpring.beans.exception.BeansException;
import org.tinySpring.beans.factory.support.BeanDefinitionRegistry;
import org.tinySpring.beans.factory.support.DefaultBeanFactory;
import org.tinySpring.beans.factory.support.GenericBeanDefinition;
import org.tinySpring.beans.xml.XMLBeanDefinitionReader;
import org.tinySpring.test.service.PetStoreService;

import static org.junit.Assert.*;

public class BeanFactoryTest {

    private DefaultBeanFactory factory = null;

    private XMLBeanDefinitionReader xmlBeanDefinitionReader = null;

    @Before
    public void setUp(){
        factory = new DefaultBeanFactory();
        xmlBeanDefinitionReader = new XMLBeanDefinitionReader(factory);
        xmlBeanDefinitionReader.loadBeanDefinition(new ClassPathResource("petStore.xml"));
    }

    @Test
    public void getBeanTest(){
        BeanDefinition beanDefinition = factory.getBeanDefinition("petStore");

        assertTrue(beanDefinition.isSingleton());

        assertFalse(beanDefinition.isPrototype());

        assertEquals(GenericBeanDefinition.SCOPE_SINGLETON,beanDefinition.getScope());

        assertEquals("org.tinySpring.test.service.PetStoreService",beanDefinition.getBeanClassName());

        PetStoreService petStoreService = (PetStoreService)factory.getBean("petStore");
        assertNotNull(petStoreService);
    }

    @Test
    public void invalidBeanTest(){
        try {
            PetStoreService petStoreService = (PetStoreService)factory.getBean("petStore2");
        }catch (BeansException e){
            e.printStackTrace();
            return;
        }
        Assert.fail();
    }

    @Test
    public void invalidBeanFactoryTest(){
        try {
            BeanDefinitionRegistry factory = new DefaultBeanFactory();
            XMLBeanDefinitionReader xmlBeanDefinitionReader = new XMLBeanDefinitionReader(factory);
            xmlBeanDefinitionReader.loadBeanDefinition(new ClassPathResource("petStore2.xml"));
        }catch (BeansException e){
            e.printStackTrace();
            return;
        }
        Assert.fail();
    }
}
