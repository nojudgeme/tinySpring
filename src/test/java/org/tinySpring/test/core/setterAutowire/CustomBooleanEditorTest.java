package org.tinySpring.test.core.setterAutowire;

import org.junit.Assert;
import org.junit.Test;
import org.tinySpring.beans.propertieseditors.CustomBooleanEditor;

public class CustomBooleanEditorTest {

    @Test
    public void testConvertString(){
        CustomBooleanEditor editor = new CustomBooleanEditor(true);

        editor.setAsText("true");
        Assert.assertEquals(true,((Boolean) editor.getValue()).booleanValue());
        editor.setAsText("false");
        Assert.assertEquals(false,((Boolean) editor.getValue()).booleanValue());

        editor.setAsText("on");
        Assert.assertEquals(true,((Boolean) editor.getValue()).booleanValue());
        editor.setAsText("off");
        Assert.assertEquals(false,((Boolean) editor.getValue()).booleanValue());

        editor.setAsText("yes");
        Assert.assertEquals(true,((Boolean) editor.getValue()).booleanValue());
        editor.setAsText("no");
        Assert.assertEquals(false,((Boolean) editor.getValue()).booleanValue());

        try {
            editor.setAsText("3.1");
        }catch (IllegalArgumentException e){
            return;
        }
        Assert.fail();
    }
}
