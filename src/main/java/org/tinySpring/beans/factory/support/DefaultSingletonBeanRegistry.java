package org.tinySpring.beans.factory.support;

import org.tinySpring.beans.factory.config.SingletonBeanRegistry;
import org.tinySpring.utils.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    private final Map<String,Object> singletonObjects = new ConcurrentHashMap<String,Object>();

    public void registerSingleton(String beanId, Object singletonObject) {
        Assert.notNull(beanId,"beanName can't be null");

        Object oldObject = this.singletonObjects.get("beanName");
        if(oldObject != null){
            throw new IllegalStateException("couldn't register "+beanId+",there is already an object:"+oldObject);
        }
        this.singletonObjects.put(beanId,singletonObject);
    }

    public Object getSingleton(String beanId) {
        return this.singletonObjects.get(beanId);
    }
}
