package io.sumac.propertyutils.model;


import io.sumac.propertyutils.annotations.Property;

import java.util.Date;

public class Model11 {

    private Date date;

    public Date getDate() {
        return date;
    }

    @Property(name = "test.found.string")
    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Model10 [date=" + date + "]";
    }

}