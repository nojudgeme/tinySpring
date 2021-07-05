package org.tinySpring.test.core.resource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.tinySpring.beans.core.io.ClassPathResource;
import org.tinySpring.beans.core.io.FileSystemResource;

import java.io.IOException;
import java.io.InputStream;

public class ResourceTest {

    private String filePath = "";

    @Before
    public void setUp(){
        filePath = "petStore.xml";
    }

    @Test
    public void classPathSourceTest(){
        ClassPathResource classPathResource = new ClassPathResource(filePath);
        InputStream inputStream = null;
        try {
             inputStream = classPathResource.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.assertNotNull(inputStream);
    }

    @Test
    public void fileSystemSourceTest(){
        filePath = "src/test/resources/petStore.xml";
        FileSystemResource fileSystemResource = new FileSystemResource(filePath);
        InputStream inputStream = null;
        try {
            inputStream = fileSystemResource.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.assertNotNull(inputStream);
    }
}
