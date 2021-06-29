package org.tinySpring.beans;

import org.tinySpring.beans.exception.TypeMismatchException;
import org.tinySpring.beans.propertieseditors.CustomBooleanEditor;
import org.tinySpring.beans.propertieseditors.CustomNumberEditor;
import org.tinySpring.utils.ClassUtils;

import java.beans.PropertyEditor;
import java.util.HashMap;
import java.util.Map;

public class SimpleTypeConverter implements TypeConverter {

    private Map<Class<?>, PropertyEditor> defaultEditors;

    @Override
    public <T> T convertIfNecessary(Object value, Class<T> requiredType) throws TypeMismatchException {
        if (ClassUtils.isAssignableValue(requiredType, value)) {
            return (T) value;
        } else {
            if (value instanceof String) {//暂只支持String类型
                PropertyEditor propertyEditor = findDefaultEditor(requiredType);
                try {
                    propertyEditor.setAsText((String) value);
                }catch (IllegalArgumentException e){
                    throw new TypeMismatchException(value,requiredType);
                }
                return (T) propertyEditor.getValue();
            } else {
                throw new RuntimeException("Todo: can't convert value for " + value + " class:" + requiredType);
            }
        }
    }

    private PropertyEditor findDefaultEditor(Class<?> requiredType) {
        PropertyEditor propertyEditor = this.getDefaultEditor(requiredType);
        if (propertyEditor == null) {
            throw new RuntimeException("Editor for " + requiredType + " has not yet been implemented");
        }
        return propertyEditor;
    }

    public PropertyEditor getDefaultEditor(Class<?> requiredType) {
        if (this.defaultEditors == null) {
            createDefaultEditors();
        }
        return this.defaultEditors.get(requiredType);
    }

    private void createDefaultEditors() {
        this.defaultEditors = new HashMap<>(64);
        //Spring's CustomBooleanEditor accepts more flag values than JDK's default editor.
        this.defaultEditors.put(boolean.class,new CustomBooleanEditor(false));
        this.defaultEditors.put(Boolean.class,new CustomBooleanEditor(true));

        //the JDK does not contain default editors for number wrapper types.
        //Override JDK primitive number editors with our own CustomNumberEditor.
        this.defaultEditors.put(int.class,new CustomNumberEditor(Integer.class,false));
        this.defaultEditors.put(Integer.class,new CustomNumberEditor(Integer.class,true));
        this.defaultEditors.put(long.class,new CustomNumberEditor(Long.class,false));
        this.defaultEditors.put(Long.class,new CustomNumberEditor(Long.class,true));
        //.......等等其他的Number子类以及基础数值类型
    }
}
