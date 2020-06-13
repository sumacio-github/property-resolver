package io.sumac.propertyutils.model;

import io.sumac.propertyutils.annotations.Property;

public class Model6 {

    private String notFoundString;

    public Model6(@Property(name = "test.not_found.string") String notFoundString) {
        super();
        this.notFoundString = notFoundString;
    }

    public String getNotFoundString() {
        return notFoundString;
    }

    public void setNotFoundString(String notFoundString) {
        this.notFoundString = notFoundString;
    }

}