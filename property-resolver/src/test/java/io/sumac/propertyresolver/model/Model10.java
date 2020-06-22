package io.sumac.propertyresolver.model;

import io.sumac.propertyresolver.annotations.Property;

import java.util.Date;

public class Model10 {

    @Property(name = "test.found.string")
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Model10 [date=" + date + "]";
    }

}