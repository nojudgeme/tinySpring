package org.tinySpring.beans.factory.support;

import org.tinySpring.beans.BeanDefinition;
import org.tinySpring.beans.PropertyValue;
import org.tinySpring.beans.SimpleTypeConverter;
import org.tinySpring.beans.exception.BeanCreationException;
import org.tinySpring.beans.factory.config.ConfigurableBeanFactory;
import org.tinySpring.utils.ClassUtils;
import org.tinySpring.utils.CollectionUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.List;
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

    //两个步骤:实例化bean，注入属性
    public Object createBean(BeanDefinition beanDefinition){
        Object bean = instantiateBean(beanDefinition);
        populateBean(beanDefinition,bean);
        return bean;
    }

    public Object instantiateBean(BeanDefinition beanDefinition){
        String beanClassName = beanDefinition.getBeanClassName();
        try {
            Class<?> loadClass = this.getBeanClassLoader().loadClass(beanClassName);
            return loadClass.newInstance();
        } catch (Exception e) {
            throw new BeanCreationException("beanClassName:" + beanClassName + " can't be found");
        }
    }

    protected void populateBean(BeanDefinition beanDefinition, Object bean) {
        List<PropertyValue> propertyValues = beanDefinition.getPropertyValues();
        if(CollectionUtils.isEmpty(propertyValues)){
            return;
        }
        BeanDefinitionValueResolver valueResolver = new BeanDefinitionValueResolver(this);
        //因为此处无法处理除ref/String外类型,所以引入类型转化器(TypeConverter)的概念,将String类型转为具体的类型
        SimpleTypeConverter typeConverter = new SimpleTypeConverter();
        try{
            for (PropertyValue propertyValue : propertyValues) {
                String propertyName = propertyValue.getName();//xml中property的指定name->beanId
                Object originalValue = propertyValue.getValue();//两种类型 ref/String
                Object resolveValue = valueResolver.resolveValueIfNecessary(originalValue);//具体的类型ref/String的value
                //使用java.beans提供的Introspector类获取bean的类信息,使用PropertyDescriptor反射调用相同property的set方法
                BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
                for (PropertyDescriptor propertyDescriptor : beanInfo.getPropertyDescriptors()) {
                    if(propertyDescriptor.getName().equals(propertyName)){
                        //将xml中定义的StringValue转换为具体的正确的数据类型,如:"123"->123,"true"->true
                        Object convertedValue = typeConverter.convertIfNecessary(resolveValue, propertyDescriptor.getPropertyType());
                        propertyDescriptor.getWriteMethod().invoke(bean,convertedValue);
                        break;
                    }
                }
            }
        }catch (Exception e){
            throw new BeanCreationException("Failed to obtain BeanInfo for class {"+beanDefinition.getBeanClassName()+"}");
        }
    }

    public ClassLoader getBeanClassLoader() {
        return  classLoader==null?ClassUtils.getDefaultClassLoader():classLoader;
    }

    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }
}
