package org.tinySpring.test.core;

import org.junit.Assert;
import org.junit.Test;
import org.tinySpring.beans.BeanDefinition;
import org.tinySpring.beans.PropertyValue;
import org.tinySpring.beans.core.io.ClassPathResource;
import org.tinySpring.beans.factory.config.RuntimeBeanReference;
import org.tinySpring.beans.factory.support.DefaultBeanFactory;
import org.tinySpring.beans.xml.XMLBeanDefinitionReader;

import java.util.List;

public class BeanDefinitionTest {

    @Test
    public void testGetBeanDefinition(){
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XMLBeanDefinitionReader reader = new XMLBeanDefinitionReader(factory);
        reader.loadBeanDefinition(new ClassPathResource("petStore.xml"));

        BeanDefinition beanDefinition = factory.getBeanDefinition("petStore");
        List<PropertyValue> propertyValues = beanDefinition.getPropertyValues();
        Assert.assertTrue(propertyValues.size() == 2);
        {
            PropertyValue propertyValue = this.getPropertyValue("accountDao", propertyValues);
            Assert.assertNotNull(propertyValue);
            Assert.assertTrue(propertyValue.getValue() instanceof RuntimeBeanReference);
        }
        {
            PropertyValue propertyValue = this.getPropertyValue("itemDao", propertyValues);
            Assert.assertNotNull(propertyValue);
            Assert.assertTrue(propertyValue.getValue() instanceof RuntimeBeanReference);
        }
    }

    private PropertyValue getPropertyValue(String name, List<PropertyValue> propertyValues) {
        for (PropertyValue propertyValue : propertyValues) {
            if(name.equals(propertyValue.getName())){
                return propertyValue;
            }
        }
        return null;
    }
}
