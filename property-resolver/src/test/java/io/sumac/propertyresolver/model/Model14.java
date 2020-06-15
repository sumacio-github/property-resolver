package io.sumac.propertyresolver.model;

import io.sumac.propertyresolver.annotations.Property;

public class Model14 {

    private String str;

    public Model14(@Property(name = "test.found.string") String str, String someOtherString) {
        this.str = str;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return "Model13 [str=" + str + "]";
    }

}
