package org.charlice.beans.factory.support;

import org.charlice.beans.BeanDefinition;
import org.charlice.beans.exception.BeanCreationException;
import org.charlice.beans.factory.config.ConfigurableBeanFactory;
import org.charlice.utils.ClassUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultBeanFactory extends DefaultSingletonBeanRegistry implements ConfigurableBeanFactory,BeanDefinitionRegistry {

    private ClassLoader classLoader;

    private final Map<String,BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String,BeanDefinition>();

    public BeanDefinition getBeanDefinition(String beanId) {
        return this.beanDefinitionMap.get(beanId);
    }

    public void register(String beanId, BeanDefinition beanDefinition) {
        this.beanDefinitionMap.put(beanId,beanDefinition);
    }

    public Object getBean(String beanId) {
        BeanDefinition beanDefinition = this.getBeanDefinition(beanId);
        if(beanDefinition == null){
            return null;
        }
        if(beanDefinition.isSingleton()){
            Object bean = this.getSingleton(beanDefinition.getBeanClassName());
            if(bean ==null){
                 bean = this.createBean(beanDefinition);
                this.registerSingleton(beanId,bean);
            }
            return bean;
        }
        return this.createBean(beanDefinition);
    }

    public Object createBean(BeanDefinition beanDefinition){
        String beanClassName = beanDefinition.getBeanClassName();
        try {
            Class<?> loadClass = this.getBeanClassLoader().loadClass(beanClassName);
            return loadClass.newInstance();
        } catch (Exception e) {
            throw new BeanCreationException("beanClassName:" + beanClassName + " can't be found");
        }
    }

    public ClassLoader getBeanClassLoader() {
        return  classLoader==null?ClassUtils.getDefaultClassLoader():classLoader;
    }

    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }
}
