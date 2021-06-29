package org.tinySpring.beans.factory.support;

import org.tinySpring.beans.factory.config.RuntimeBeanReference;
import org.tinySpring.beans.factory.config.TypedStringValue;

public class BeanDefinitionValueResolver {

    private final DefaultBeanFactory beanFactory;

    public BeanDefinitionValueResolver(DefaultBeanFactory factory) {
        this.beanFactory = factory;
    }

    public Object resolveValueIfNecessary(Object value) {
        if (value instanceof RuntimeBeanReference) {
            RuntimeBeanReference reference = (RuntimeBeanReference) value;
            Object bean = beanFactory.getBean(reference.getBeanName());
            return bean;
        } else if (value instanceof TypedStringValue) {
            return ((TypedStringValue) value).getValue();
        } else {
            //todo
            throw new RuntimeException("the value "+value+" has not yet implemented");
        }
    }
}
