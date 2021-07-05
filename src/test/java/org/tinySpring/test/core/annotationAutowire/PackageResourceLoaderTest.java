package org.tinySpring.test.core.annotationAutowire;

import org.junit.Assert;
import org.junit.Test;
import org.tinySpring.beans.core.io.Resource;
import org.tinySpring.beans.core.io.support.PackageResourceLoader;

public class PackageResourceLoaderTest {

    @Test
    public void testGetResource(){
        PackageResourceLoader resourceLoader = new PackageResourceLoader();
        Resource[] resource =  resourceLoader.load("org.tinySpring.test.dao");
        Assert.assertEquals(2,resource.length);

        resource =  resourceLoader.load("org.tinySpring.test.service");
        Assert.assertEquals(1,resource.length);
    }
}
