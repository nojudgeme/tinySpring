package org.charlice.test.core;

import org.charlice.beans.context.ApplicationContext;
import org.charlice.beans.context.support.ClassPathXMLReaderContext;
import org.charlice.beans.context.support.FileSystemXMLApplicationContext;
import org.charlice.test.service.PetStoreService;
import org.junit.Assert;
import org.junit.Test;

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
