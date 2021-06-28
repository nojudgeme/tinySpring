package org.tinySpring.beans.context.support;

import org.tinySpring.beans.context.ApplicationContext;
import org.tinySpring.beans.core.io.FileSystemResource;
import org.tinySpring.beans.core.io.Resource;

public class FileSystemXMLApplicationContext extends AbstractApplicationContext implements ApplicationContext {

    public FileSystemXMLApplicationContext(String configFile) {
        super(configFile);
    }

    protected Resource getResourceByPath(String path) {
        return new FileSystemResource(path);
    }
}
