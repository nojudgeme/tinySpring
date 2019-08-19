package org.charlice.beans.factory.support;

import org.charlice.beans.BeanDefinition;

public class GenericBeanDefinition implements BeanDefinition {

    private String beanId;

    private String beanClassName;

    public GenericBeanDefinition(String beanId, String beanClassName) {
        this.beanId = beanId;
        this.beanClassName = beanClassName;
    }

    public String getBeanClassName() {
        return beanClassName;
    }

}
