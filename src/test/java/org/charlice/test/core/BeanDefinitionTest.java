package org.charlice.test.core;

import org.charlice.beans.BeanDefinition;
import org.charlice.beans.PropertyValue;
import org.charlice.beans.factory.config.RuntimeBeanReference;
import org.charlice.beans.core.io.ClassPathResource;
import org.charlice.beans.factory.support.DefaultBeanFactory;
import org.charlice.beans.xml.XMLBeanDefinitionReader;
import org.junit.Assert;
import org.junit.Test;

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
