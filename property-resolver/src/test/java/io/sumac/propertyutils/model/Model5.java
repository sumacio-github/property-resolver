package io.sumac.propertyutils.model;

import io.sumac.propertyutils.annotations.Property;

public class Model5 {

    private String notFoundString;

    public String getNotFoundString() {
        return notFoundString;
    }

    @Property(name = "test.not_found.string")
    public void setNotFoundString(String notFoundString) {
        this.notFoundString = notFoundString;
    }

}
