package org.charlice.beans.context.support;

import org.charlice.beans.context.ApplicationContext;
import org.charlice.beans.core.io.ClassPathResource;
import org.charlice.beans.core.io.Resource;

public class ClassPathXMLReaderContext extends AbstractApplicationContext implements ApplicationContext {

    public ClassPathXMLReaderContext(String configFile) {
        super(configFile);
    }

    protected Resource getResourceByPath(String path) {
        return new ClassPathResource(path,this.getBeanClassLoader());
    }
}
