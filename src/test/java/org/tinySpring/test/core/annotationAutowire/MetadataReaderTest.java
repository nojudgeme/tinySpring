package org.tinySpring.test.core.annotationAutowire;

import org.junit.Assert;
import org.junit.Test;
import org.tinySpring.beans.core.annotation.AnnotationAttributes;
import org.tinySpring.beans.core.io.ClassPathResource;
import org.tinySpring.beans.core.type.AnnotationMetadata;
import org.tinySpring.beans.core.type.MetadataReader;
import org.tinySpring.beans.core.type.classreading.SimpleMetadataReader;
import org.tinySpring.stereotype.Component;

import java.io.IOException;

public class MetadataReaderTest {

    @Test
    public void testGetMetadata() throws IOException {
        ClassPathResource resource = new ClassPathResource("org/tinySpring/test/service/PetStoreService.class");
        MetadataReader reader = new SimpleMetadataReader(resource);
        //ClassMetadata classMetadata = reader.getClassMetadata();
        AnnotationMetadata annotationMetadata = reader.getAnnotationMetadata();
        String annotation = Component.class.getName();

        Assert.assertTrue(annotationMetadata.hasAnnotation(annotation));
        AnnotationAttributes annotationAttributes = annotationMetadata.getAnnotationAttributes(annotation);
        Assert.assertEquals("petStore",annotationAttributes.get("value"));

        Assert.assertFalse(annotationMetadata.isAbstract());
        Assert.assertFalse(annotationMetadata.isFinal());
        Assert.assertEquals("org.tinySpring.test.service.PetStoreService",annotationMetadata.getClassName());
    }
}
