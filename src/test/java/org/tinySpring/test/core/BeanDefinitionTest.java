package org.tinySpring.test.core;

import org.junit.Assert;
import org.junit.Test;
import org.tinySpring.beans.BeanDefinition;
import org.tinySpring.beans.ConstructorArgument;
import org.tinySpring.beans.PropertyValue;
import org.tinySpring.beans.core.io.ClassPathResource;
import org.tinySpring.beans.factory.config.RuntimeBeanReference;
import org.tinySpring.beans.factory.config.TypedStringValue;
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
        Assert.assertTrue(propertyValues.size() == 4);
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

    @Test
    public void testConstructorArgument(){
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XMLBeanDefinitionReader reader = new XMLBeanDefinitionReader(factory);
        reader.loadBeanDefinition(new ClassPathResource("petStore-v2.xml"));

        BeanDefinition beanDefinition = factory.getBeanDefinition("petStore");
        Assert.assertEquals("org.tinySpring.test.service.PetStoreService",beanDefinition.getBeanClassName());

        ConstructorArgument args = beanDefinition.getConstructorArgument();
        List<ConstructorArgument.ValueHolder> valueHolders = args.getArgumentsValues();
        Assert.assertEquals(6,valueHolders.size());

        RuntimeBeanReference ref1 = (RuntimeBeanReference) valueHolders.get(0).getValue();
        Assert.assertEquals("accountDao",ref1.getBeanName());

        RuntimeBeanReference ref2 = (RuntimeBeanReference) valueHolders.get(1).getValue();
        Assert.assertEquals("itemDao",ref2.getBeanName());

        TypedStringValue versionAttr = (TypedStringValue) valueHolders.get(2).getValue();
        Assert.assertEquals("1",versionAttr.getValue());

        TypedStringValue yearsAttr = (TypedStringValue) valueHolders.get(3).getValue();
        Assert.assertEquals("2021",yearsAttr.getValue());

        TypedStringValue nameAttr = (TypedStringValue) valueHolders.get(4).getValue();
        Assert.assertEquals("来福专卖店",nameAttr.getValue());

        TypedStringValue owner = (TypedStringValue) valueHolders.get(5).getValue();
        Assert.assertEquals("charles",owner.getValue());
    }
}
