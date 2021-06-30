package org.tinySpring.beans;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ConstructorArgument {

    private final List<ValueHolder> argumentsValues = new LinkedList<>();

    public void addArgumentValues(ValueHolder valueHolder){
        argumentsValues.add(valueHolder);
    }

    public List<ValueHolder> getArgumentsValues() {
        return Collections.unmodifiableList(argumentsValues);
    }

    public int getArgumentCount(){
        return argumentsValues.size();
    }

    public boolean isEmpty(){
        return argumentsValues.isEmpty();
    }

    public void clear(){
        argumentsValues.clear();
    }

    public static class ValueHolder {

        private String name;

        private String type;

        private int index;

        private Object value;

        public ValueHolder(Object value) {
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }
}
