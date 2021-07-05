package org.tinySpring.beans.core.io;

import org.tinySpring.utils.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileSystemResource implements Resource {

    private String filePath;
    private File file;

    public FileSystemResource(String filePath) {
        Assert.notNull(filePath,"filePath can't be empty");
        this.filePath = filePath;
        this.file = new File(filePath);
    }

    public FileSystemResource(File file) {
        Assert.notNull(file,"file can't be empty");
        this.filePath = file.getPath();
        this.file = file;
    }

    public InputStream getInputStream() throws IOException {
        return new FileInputStream(file);
    }

    public String getDescription() {
        return "file:{"+this.file.getAbsolutePath()+"}";
    }
}
