package org.tinySpring.beans.core.type.classreading;

import org.springframework.asm.ClassReader;
import org.tinySpring.beans.core.io.Resource;
import org.tinySpring.beans.core.type.AnnotationMetadata;
import org.tinySpring.beans.core.type.ClassMetadata;
import org.tinySpring.beans.core.type.MetadataReader;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SimpleMetadataReader implements MetadataReader {

    private final Resource resource;
    private final ClassMetadata classMetadata;
    private final AnnotationMetadata annotationMetadata;

    public SimpleMetadataReader(Resource resource) throws IOException {
        InputStream inputStream = new BufferedInputStream(resource.getInputStream());
        ClassReader classReader;
        try{
            classReader = new ClassReader(inputStream);
        }finally {
            inputStream.close();
        }
        AnnotationMetadataReadingVisitor visitor = new AnnotationMetadataReadingVisitor();
        classReader.accept(visitor, ClassReader.SKIP_DEBUG);
        this.annotationMetadata = visitor;
        this.classMetadata = visitor;
        this.resource = resource;
    }

    @Override
    public Resource getResource() {
        return this.resource;
    }

    @Override
    public ClassMetadata getClassMetadata() {
        return this.classMetadata;
    }

    @Override
    public AnnotationMetadata getAnnotationMetadata() {
        return this.annotationMetadata;
    }
}
