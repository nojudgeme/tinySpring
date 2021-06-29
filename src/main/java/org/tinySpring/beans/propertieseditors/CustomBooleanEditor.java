package org.tinySpring.beans.propertieseditors;

import org.tinySpring.utils.StringUtils;

import java.beans.PropertyEditorSupport;

public class CustomBooleanEditor extends PropertyEditorSupport {

    private static final String VALUE_TRUE = "true";
    private static final String VALUE_FALSE = "false";
    private static final String VALUE_ON = "on";
    private static final String VALUE_OFF = "off";
    private static final String VALUE_YES = "yes";
    private static final String VALUE_NO = "no";
    private static final String VALUE_1 = "1";
    private static final String VALUE_0 = "0";

    private final boolean allowEmpty;

    public CustomBooleanEditor(boolean allowEmpty) {
        this.allowEmpty = allowEmpty;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        text = (text == null ? null : text.trim());
        if (allowEmpty && !StringUtils.hasText(text)) {
            setValue(null);
        } else if (VALUE_TRUE.equalsIgnoreCase(text) || VALUE_ON.equalsIgnoreCase(text) ||
                VALUE_YES.equalsIgnoreCase(text) || VALUE_1.equalsIgnoreCase(text)) {
            setValue(true);
        } else if (VALUE_FALSE.equalsIgnoreCase(text) || VALUE_OFF.equalsIgnoreCase(text) ||
                VALUE_NO.equalsIgnoreCase(text) || VALUE_0.equalsIgnoreCase(text)) {
            setValue(false);
        } else {
            throw new IllegalArgumentException("Invalid boolean value ["+text+"]");
        }
    }

    @Override
    public String getAsText() {
        if(Boolean.TRUE.equals(getValue())){
            return VALUE_TRUE;
        }else if(Boolean.FALSE.equals(getValue())){
            return VALUE_FALSE;
        }else {
            return "";
        }
    }
}
