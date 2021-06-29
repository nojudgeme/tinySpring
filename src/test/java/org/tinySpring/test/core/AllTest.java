package org.tinySpring.test.core;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ApplicationContextTest.class,
        BeanDefinitionTest.class,
        BeanDefinitionValueResolverTest.class,
        BeanFactoryTest.class,
        CustomBooleanEditorTest.class,
        CustomNumberEditorTest.class,
        ResourceTest.class,
        TypeConverterTest.class
})
public class AllTest {
}
