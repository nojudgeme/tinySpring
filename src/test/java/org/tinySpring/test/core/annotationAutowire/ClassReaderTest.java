package org.tinySpring.test.core.annotationAutowire;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.asm.ClassReader;
import org.tinySpring.beans.core.annotation.AnnotationAttributes;
import org.tinySpring.beans.core.io.ClassPathResource;
import org.tinySpring.beans.core.type.classreading.AnnotationMetadataReadingVisitor;
import org.tinySpring.beans.core.type.classreading.ClassMetadataReadingVisitor;

import java.io.IOException;

public class ClassReaderTest {

    @Test
    public void testGetClassMeteData() throws IOException {
        ClassPathResource resource = new ClassPathResource("org/tinySpring/test/service/PetStoreService.class");
        ClassReader reader = new ClassReader(resource.getInputStream());
        ClassMetadataReadingVisitor visitor = new ClassMetadataReadingVisitor();
        reader.accept(visitor,ClassReader.SKIP_DEBUG);
        Assert.assertFalse(visitor.isAbstract());
        Assert.assertFalse(visitor.isInterface());
        Assert.assertFalse(visitor.isFinal());
        Assert.assertEquals("org.tinySpring.test.service.PetStoreService",visitor.getClassName());
        Assert.assertEquals("java.lang.Object",visitor.getSuperClassName());
        Assert.assertEquals(0,visitor.getInterfaceNames().length);
    }

    @Test
    public void testGetAnnotation() throws IOException {
        ClassPathResource resource = new ClassPathResource("org/tinySpring/test/service/PetStoreService.class");
        ClassReader reader = new ClassReader(resource.getInputStream());
        AnnotationMetadataReadingVisitor visitor = new AnnotationMetadataReadingVisitor();
        reader.accept(visitor,ClassReader.SKIP_DEBUG);
        String annotation = "org.tinySpring.stereotype.Component";
        Assert.assertTrue(visitor.hasAnnotation(annotation));
        AnnotationAttributes annotationAttributes = visitor.getAnnotationAttributes(annotation);
        Assert.assertEquals("petStore",annotationAttributes.get("value"));
    }
}
