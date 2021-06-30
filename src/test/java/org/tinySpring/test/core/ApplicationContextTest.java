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
        Assert.assertNotNull(petStoreService.getItemDao());
        Assert.assertNotNull(petStoreService.getAccountDao());
        Assert.assertEquals("charles",petStoreService.getOwner());
        Assert.assertEquals(1,petStoreService.getVersion());
    }

    @Test
    public void classPathXMLApplicationContextTestV2(){
        ApplicationContext applicationContext = new ClassPathXMLReaderContext("petStore-v2.xml");
        PetStoreService petStoreService = (PetStoreService)applicationContext.getBean("petStore");
        Assert.assertNotNull(petStoreService.getItemDao());
        Assert.assertNotNull(petStoreService.getAccountDao());
        Assert.assertEquals(1,petStoreService.getVersion());
    }

    @Test
    public void fileSystemApplicationContextTest(){
        ApplicationContext applicationContext = new FileSystemXMLApplicationContext("src/test/resources/petStore.xml");
        PetStoreService petStoreService = (PetStoreService)applicationContext.getBean("petStore");
        Assert.assertNotNull(petStoreService);
    }

}
