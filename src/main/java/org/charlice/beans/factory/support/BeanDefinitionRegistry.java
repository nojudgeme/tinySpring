package org.charlice.beans.factory.support;

import org.charlice.beans.BeanDefinition;

public interface BeanDefinitionRegistry {

    BeanDefinition getBeanDefinition(String beanId);

    void register(String beanId,BeanDefinition beanDefinition);
}
