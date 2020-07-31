package io.sumac.devtools.propertyresolver.model;

import io.sumac.devtools.propertyresolver.annotations.Property;

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
