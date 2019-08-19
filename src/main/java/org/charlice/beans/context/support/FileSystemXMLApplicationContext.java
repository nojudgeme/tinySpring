package org.charlice.beans.context.support;

import org.charlice.beans.context.ApplicationContext;
import org.charlice.beans.core.io.FileSystemResource;
import org.charlice.beans.core.io.Resource;

public class FileSystemXMLApplicationContext extends AbstractApplicationContext implements ApplicationContext {

    public FileSystemXMLApplicationContext(String configFile) {
        super(configFile);
    }

    protected Resource getResourceByPath(String path) {
        return new FileSystemResource(path);
    }
}
