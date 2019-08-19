package org.charlice.beans;

public interface BeanDefinition {

    String getBeanClassName();

    String getScope();

    boolean isSingleton();

    boolean isProtoype();
}
