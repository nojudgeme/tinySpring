package org.tinySpring.beans.core.type.classreading;

import org.springframework.asm.AnnotationVisitor;
import org.springframework.asm.Type;
import org.tinySpring.beans.core.annotation.AnnotationAttributes;
import org.tinySpring.beans.core.type.AnnotationMetadata;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class AnnotationMetadataReadingVisitor extends ClassMetadataReadingVisitor implements AnnotationMetadata {

    private final Set<String> annotationSet = new LinkedHashSet<>(4);
    private final Map<String, AnnotationAttributes> attributesMap = new LinkedHashMap<>(4);

    public AnnotationMetadataReadingVisitor() {
    }

    @Override
    public AnnotationVisitor visitAnnotation(final String desc, boolean visible) {
        String className = Type.getType(desc).getClassName();
        this.annotationSet.add(className);
        return new AnnotationAttributesReadingVisitor(className,this.attributesMap);
    }

    @Override
    public Set<String> getAnnotationTypes() {
        return annotationSet;
    }

    public boolean hasAnnotation(String annotationType) {
        return this.annotationSet.contains(annotationType);
    }

    public AnnotationAttributes getAnnotationAttributes(String annotationType) {
        return this.attributesMap.get(annotationType);
    }
}