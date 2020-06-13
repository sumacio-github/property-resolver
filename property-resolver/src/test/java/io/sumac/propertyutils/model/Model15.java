package io.sumac.propertyutils.model;

import io.sumac.propertyutils.annotations.Property;

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