package io.sumac.propertyresolver.model;

import io.sumac.propertyresolver.annotations.Property;

public class Model4 {
    @Property(name = "test.not_found.string")
    private String notFoundString;

    public String getNotFoundString() {
        return notFoundString;
    }

    public void setNotFoundString(String notFoundString) {
        this.notFoundString = notFoundString;
    }

}
