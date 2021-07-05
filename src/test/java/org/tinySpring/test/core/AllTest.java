package org.tinySpring.test.core;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.tinySpring.test.core.annotationAutowire.ClassReaderTest;
import org.tinySpring.test.core.annotationAutowire.MetadataReaderTest;
import org.tinySpring.test.core.annotationAutowire.PackageResourceLoaderTest;
import org.tinySpring.test.core.applicationContext.ApplicationContextTest;
import org.tinySpring.test.core.beanDefinition.BeanDefinitionTest;
import org.tinySpring.test.core.beanDefinition.BeanDefinitionValueResolverTest;
import org.tinySpring.test.core.beanFactory.BeanFactoryTest;
import org.tinySpring.test.core.resource.ResourceTest;
import org.tinySpring.test.core.setterAutowire.ConstructorResolverTest;
import org.tinySpring.test.core.setterAutowire.CustomBooleanEditorTest;
import org.tinySpring.test.core.setterAutowire.CustomNumberEditorTest;
import org.tinySpring.test.core.setterAutowire.TypeConverterTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ApplicationContextTest.class,
        BeanDefinitionTest.class,
        BeanFactoryTest.class,
        ResourceTest.class,
        CustomBooleanEditorTest.class,
        CustomNumberEditorTest.class,
        TypeConverterTest.class,
        BeanDefinitionValueResolverTest.class,
        ConstructorResolverTest.class,
        PackageResourceLoaderTest.class,
        ClassReaderTest.class,
        MetadataReaderTest.class
})
public class AllTest {
}
