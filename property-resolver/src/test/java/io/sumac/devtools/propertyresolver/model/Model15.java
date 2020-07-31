package io.sumac.devtools.propertyresolver.model;

import io.sumac.devtools.propertyresolver.annotations.Property;

public class Model15 {

    private String str;

    public String getStr() {
        return str;
    }

    @Property(name = "test.found.string")
    public void setStr() {

    }

    @Override
    public String toString() {
        return "Model13 [str=" + str + "]";
    }

}