package org.tinySpring.beans.core.io;

import org.tinySpring.utils.ClassUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ClassPathResource implements Resource {

    private String filePath;

    private ClassLoader classLoader;

    public ClassPathResource(String filePath) {
        this(filePath,null);
    }

    public ClassPathResource(String filePath, ClassLoader classLoader) {
        this.filePath = filePath;
        this.classLoader = (classLoader!=null?classLoader: ClassUtils.getDefaultClassLoader());
    }

    public InputStream getInputStream() throws IOException {
        InputStream inputStream = this.classLoader.getResourceAsStream(this.filePath);
        if(inputStream == null){
            throw new FileNotFoundException(this.filePath+" can't be found");
        }
        return inputStream;
    }

    public String getDescription() {
        return this.filePath;
    }
}
