package org.tinySpring.beans.core.type;

import org.tinySpring.beans.core.annotation.AnnotationAttributes;

import java.util.Set;

public interface AnnotationMetadata extends ClassMetadata{

    Set<String> getAnnotationTypes();

    boolean hasAnnotation(String annotationType);

    AnnotationAttributes getAnnotationAttributes(String annotationType);
}
