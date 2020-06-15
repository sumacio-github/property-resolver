package io.sumac.propertyresolver.model;

import io.sumac.propertyresolver.annotations.Property;

import java.util.Date;

public class Model12 {

    private Date date;

    public Model12(@Property(name = "test.found.string") Date date) {
        this.date = date;
    }

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