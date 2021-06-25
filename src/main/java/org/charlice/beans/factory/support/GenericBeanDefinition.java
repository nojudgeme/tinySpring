package org.charlice.beans.factory.support;

import org.charlice.beans.BeanDefinition;

public class GenericBeanDefinition implements BeanDefinition {

    public static final String SCOPE_PROTOTYPE = "prototype";

    public static final String SCOPE_SINGLETON = "singleton";

    public static final String SCOPE_DEFAULT = "";

    private String beanId;

    private String beanClassName;

    private String scope = SCOPE_DEFAULT;

    private boolean isSingleton;

    private boolean isProtoype;

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
        this.isProtoype = SCOPE_PROTOTYPE.equals(scope);
        this.isSingleton = SCOPE_DEFAULT.equals(scope) || SCOPE_SINGLETON.equals(scope);
    }

    public boolean isSingleton() {
        return isSingleton;
    }

    public void setIsSingleton(boolean isSingleton) {
        this.isSingleton = isSingleton;
    }

    public boolean isPrototype() {
        return isProtoype;
    }

    public void setIsProtoype(boolean isProtoype) {
        this.isProtoype = isProtoype;
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
