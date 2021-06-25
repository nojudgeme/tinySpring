package org.charlice.test.core;

import org.charlice.beans.BeanDefinition;
import org.charlice.beans.core.io.ClassPathResource;
import org.charlice.beans.exception.BeansException;
import org.charlice.beans.factory.support.BeanDefinitionRegistry;
import org.charlice.beans.factory.support.DefaultBeanFactory;
import org.charlice.beans.factory.support.GenericBeanDefinition;
import org.charlice.beans.xml.XMLBeanDefinitionReader;
import org.charlice.test.service.PetStoreService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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

        assertEquals("org.charlice.test.service.PetStoreService",beanDefinition.getBeanClassName());

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
