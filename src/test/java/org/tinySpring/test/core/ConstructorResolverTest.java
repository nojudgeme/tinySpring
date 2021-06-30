package org.tinySpring.test.core;

import org.junit.Assert;
import org.junit.Test;
import org.tinySpring.beans.BeanDefinition;
import org.tinySpring.beans.core.io.ClassPathResource;
import org.tinySpring.beans.factory.support.ConstructorResolver;
import org.tinySpring.beans.factory.support.DefaultBeanFactory;
import org.tinySpring.beans.xml.XMLBeanDefinitionReader;
import org.tinySpring.test.service.PetStoreService;

public class ConstructorResolverTest {

    @Test
    public void testAutowireConstructor(){
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XMLBeanDefinitionReader reader = new XMLBeanDefinitionReader(factory);
        reader.loadBeanDefinition(new ClassPathResource("petStore-v2.xml"));

        BeanDefinition beanDefinition = factory.getBeanDefinition("petStore");
        ConstructorResolver resolver = new ConstructorResolver(factory);
        PetStoreService petStoreService = (PetStoreService) resolver.autowireConstructor(beanDefinition);
        Assert.assertNotNull(petStoreService.getAccountDao());
        Assert.assertNotNull(petStoreService.getItemDao());
        Assert.assertEquals("来福专卖店",petStoreService.getName());
        Assert.assertEquals(2021,petStoreService.getYears());
        Assert.assertEquals("charles",petStoreService.getOwner());

    }
}
