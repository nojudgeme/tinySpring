package org.tinySpring.beans.context.support;

import org.tinySpring.beans.context.ApplicationContext;
import org.tinySpring.beans.core.io.ClassPathResource;
import org.tinySpring.beans.core.io.Resource;

public class ClassPathXMLReaderContext extends AbstractApplicationContext implements ApplicationContext {

    public ClassPathXMLReaderContext(String configFile) {
        super(configFile);
    }

    protected Resource getResourceByPath(String path) {
        return new ClassPathResource(path,this.getBeanClassLoader());
    }
}
