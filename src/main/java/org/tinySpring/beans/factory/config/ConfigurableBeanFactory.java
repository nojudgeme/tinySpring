package org.tinySpring.beans.factory.config;

import org.tinySpring.beans.factory.BeanFactory;

public interface ConfigurableBeanFactory extends BeanFactory {

    void setBeanClassLoader(ClassLoader beanClassLoader);

    ClassLoader getBeanClassLoader();
}
