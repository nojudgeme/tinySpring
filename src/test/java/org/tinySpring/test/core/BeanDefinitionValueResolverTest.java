package org.tinySpring.test.core;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.tinySpring.beans.core.io.ClassPathResource;
import org.tinySpring.beans.factory.config.RuntimeBeanReference;
import org.tinySpring.beans.factory.config.TypedStringValue;
import org.tinySpring.beans.factory.support.BeanDefinitionValueResolver;
import org.tinySpring.beans.factory.support.DefaultBeanFactory;
import org.tinySpring.beans.xml.XMLBeanDefinitionReader;
import org.tinySpring.test.dao.AccountDao;

public class BeanDefinitionValueResolverTest {

    DefaultBeanFactory factory = null;

    @Before
    public void setUp() {
        factory = new DefaultBeanFactory();
        XMLBeanDefinitionReader reader = new XMLBeanDefinitionReader(factory);
        reader.loadBeanDefinition(new ClassPathResource("petStore.xml"));
    }

    @Test
    public void tesResolveRuntimeReference() {
        BeanDefinitionValueResolver resolver = new BeanDefinitionValueResolver(factory);
        RuntimeBeanReference accountDao = new RuntimeBeanReference("accountDao");
        Object value = resolver.resolveValueIfNecessary(accountDao);
        Assert.assertNotNull(value);
        Assert.assertTrue(value instanceof AccountDao);
    }

    @Test
    public void tesResolveTypedStringValue() {
        BeanDefinitionValueResolver resolver = new BeanDefinitionValueResolver(factory);
        TypedStringValue test = new TypedStringValue("test");
        Object value = resolver.resolveValueIfNecessary(test);
        Assert.assertNotNull(value);
        Assert.assertEquals(value,"test");
    }

}
