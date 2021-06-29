package org.tinySpring.test.core;

import org.junit.Assert;
import org.junit.Test;
import org.tinySpring.beans.SimpleTypeConverter;
import org.tinySpring.beans.TypeConverter;
import org.tinySpring.beans.exception.TypeMismatchException;

public class TypeConverterTest {

    @Test
    public void testConverterStringToInt(){
        TypeConverter converter = new SimpleTypeConverter();
        Integer i = converter.convertIfNecessary("3",Integer.class);
        Assert.assertEquals(3,i.intValue());

        try {
            converter.convertIfNecessary("3.1",Integer.class);
        }catch (TypeMismatchException e){
            return;
        }
        Assert.fail();
    }

    @Test
    public void testConverterStringToBoolean(){
        TypeConverter converter = new SimpleTypeConverter();
        Boolean i = converter.convertIfNecessary("true",Boolean.class);
        Assert.assertEquals(true,i.booleanValue());

        try {
            converter.convertIfNecessary("xxyy",Boolean.class);
        }catch (TypeMismatchException e){
            return;
        }
        Assert.fail();
    }
}
