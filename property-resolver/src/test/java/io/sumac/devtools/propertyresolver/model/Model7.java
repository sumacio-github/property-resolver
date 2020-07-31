package io.sumac.devtools.propertyresolver.model;

import io.sumac.devtools.propertyresolver.annotations.Property;

public class Model7 {
    @Property(name = "test.found.string", optional = true)
    private String foundString;
    @Property(name = "test.not_found.string", optional = true)
    private String notFoundString;

    public String getFoundString() {
        return foundString;
    }

    public void setFoundString(String foundString) {
        this.foundString = foundString;
    }

    public String getNotFoundString() {
        return notFoundString;
    }

    public void setNotFoundString(String notFoundString) {
        this.notFoundString = notFoundString;
    }

}