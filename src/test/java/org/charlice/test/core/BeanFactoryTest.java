package org.charlice.test.core;

import org.charlice.beans.BeanDefinition;
import org.charlice.beans.exception.BeansException;
import org.charlice.beans.factory.BeanFactory;
import org.charlice.beans.factory.support.DefaultBeanFactory;
import org.charlice.test.service.PetStoreService;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BeanFactoryTest {

    @Test
    public void getBeanTest(){
        BeanFactory factory = new DefaultBeanFactory("petStore.xml");
        BeanDefinition beanDefinition = factory.getBeanDefinition("petStore");
        assertEquals("org.charlice.test.service.PetStoreService",beanDefinition.getBeanClassName());

        PetStoreService petStoreService = (PetStoreService)factory.getBean("petStore");
        assertNotNull(petStoreService);
    }

    @Test
    public void invalidBeanTest(){
        BeanFactory factory = new DefaultBeanFactory("petStore.xml");
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
            BeanFactory factory = new DefaultBeanFactory("petStore2.xml");
        }catch (BeansException e){
            e.printStackTrace();
            return;
        }
        Assert.fail();
    }
}
