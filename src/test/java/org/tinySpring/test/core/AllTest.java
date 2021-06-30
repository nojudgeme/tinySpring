package org.tinySpring.test.core;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

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
        ConstructorResolverTest.class
})
public class AllTest {
}
