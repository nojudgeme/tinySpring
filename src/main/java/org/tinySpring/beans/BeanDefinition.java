package org.tinySpring.beans;

import java.util.List;

public interface BeanDefinition {

    String SCOPE_PROTOTYPE = "prototype";

    String SCOPE_SINGLETON = "singleton";

    String SCOPE_DEFAULT = "";

    String getBeanClassName();

    String getScope();

    void setScope(String scope);

    boolean isSingleton();

    boolean isPrototype();

    List<PropertyValue> getPropertyValues();

    ConstructorArgument getConstructorArgument();

    String getID();

    boolean hasConstructorArgumentValues();
}
