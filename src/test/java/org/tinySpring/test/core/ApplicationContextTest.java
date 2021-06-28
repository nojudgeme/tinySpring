package org.tinySpring.test.core;

import org.junit.Assert;
import org.junit.Test;
import org.tinySpring.beans.context.ApplicationContext;
import org.tinySpring.beans.context.support.ClassPathXMLReaderContext;
import org.tinySpring.beans.context.support.FileSystemXMLApplicationContext;
import org.tinySpring.test.service.PetStoreService;

public class ApplicationContextTest {

    @Test
    public void classPathXMLApplicationContextTest(){
        ApplicationContext applicationContext = new ClassPathXMLReaderContext("petStore.xml");
        PetStoreService petStoreService = (PetStoreService)applicationContext.getBean("petStore");
        Assert.assertNotNull(petStoreService);
    }

    @Test
    public void fileSystemApplicationContextTest(){
        ApplicationContext applicationContext = new FileSystemXMLApplicationContext("D:\\tinySpring\\src\\test\\resources\\petStore.xml");
        PetStoreService petStoreService = (PetStoreService)applicationContext.getBean("petStore");
        Assert.assertNotNull(petStoreService);
    }

}
