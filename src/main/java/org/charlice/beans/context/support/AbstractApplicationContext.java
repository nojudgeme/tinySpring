package org.charlice.beans.context.support;

import org.charlice.beans.context.ApplicationContext;
import org.charlice.beans.core.io.Resource;
import org.charlice.beans.factory.support.DefaultBeanFactory;
import org.charlice.beans.xml.XMLBeanDefinitionReader;
import org.charlice.utils.ClassUtils;

public abstract class AbstractApplicationContext implements ApplicationContext {

    private DefaultBeanFactory defaultBeanFactory;

    private ClassLoader classLoader;

    public AbstractApplicationContext(String configFile) {
        defaultBeanFactory = new DefaultBeanFactory();
        XMLBeanDefinitionReader definitionReader = new XMLBeanDefinitionReader(defaultBeanFactory);
        Resource resource = getResourceByPath(configFile);
        definitionReader.loadBeanDefinition(resource);
        defaultBeanFactory.setBeanClassLoader(this.getBeanClassLoader());
    }

    public Object getBean(String beanId) {
        return this.defaultBeanFactory.getBean(beanId);
    }

    protected abstract Resource getResourceByPath(String path);

    public ClassLoader getBeanClassLoader() {
        return  classLoader==null? ClassUtils.getDefaultClassLoader():classLoader;
    }

    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }
}
