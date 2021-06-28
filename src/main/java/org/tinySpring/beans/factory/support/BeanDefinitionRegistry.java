package org.tinySpring.beans.factory.support;

import org.tinySpring.beans.BeanDefinition;

public interface BeanDefinitionRegistry {

    BeanDefinition getBeanDefinition(String beanId);

    void register(String beanId,BeanDefinition beanDefinition);
}
