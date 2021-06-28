package org.charlice.beans.factory.support;

import org.charlice.beans.BeanDefinition;
import org.charlice.beans.PropertyValue;

import java.util.ArrayList;
import java.util.List;

public class GenericBeanDefinition implements BeanDefinition {

    private String beanId;

    private String beanClassName;

    private String scope = SCOPE_DEFAULT;

    private boolean isSingleton;

    private boolean isPrototype;

    private List<PropertyValue> propertyValues = new ArrayList<>();

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
        this.isPrototype = SCOPE_PROTOTYPE.equals(scope);
        this.isSingleton = SCOPE_DEFAULT.equals(scope) || SCOPE_SINGLETON.equals(scope);
    }

    public boolean isSingleton() {
        return isSingleton;
    }

    public void setIsSingleton(boolean isSingleton) {
        this.isSingleton = isSingleton;
    }

    public boolean isPrototype() {
        return isPrototype;
    }

    @Override
    public List<PropertyValue> getPropertyValues() {
        return this.propertyValues;
    }

    public void setIsPrototype(boolean isProtoype) {
        this.isPrototype = isProtoype;
    }

    public GenericBeanDefinition(String beanId, String beanClassName) {
        this.beanId = beanId;
        this.beanClassName = beanClassName;
    }

    public GenericBeanDefinition(String beanId, String beanClassName, String scope) {
        this.beanId = beanId;
        this.beanClassName = beanClassName;
        this.scope = scope;
    }

    public String getBeanClassName() {
        return beanClassName;
    }

}
