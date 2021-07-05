package org.tinySpring.beans.core.type.classreading;

import org.springframework.asm.AnnotationVisitor;
import org.springframework.asm.SpringAsmInfo;
import org.tinySpring.beans.core.annotation.AnnotationAttributes;

import java.util.Map;

public class AnnotationAttributesReadingVisitor extends AnnotationVisitor {

    private final String annotationType;

    private final Map<String, AnnotationAttributes> attributesMap;

    private AnnotationAttributes attributes = new AnnotationAttributes();

    public AnnotationAttributesReadingVisitor(String annotationType, Map<String, AnnotationAttributes> attributesMap) {
        super(SpringAsmInfo.ASM_VERSION);
        this.annotationType = annotationType;
        this.attributesMap = attributesMap;
    }

    @Override
    public void visit(String attributeName, Object attributeValue) {
        this.attributes.put(attributeName,attributeValue);
    }

    @Override
    public final void visitEnd() {
        this.attributesMap.put(this.annotationType,this.attributes);
    }
}
