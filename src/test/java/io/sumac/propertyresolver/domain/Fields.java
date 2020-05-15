package io.sumac.propertyresolver.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Fields {
    @JsonProperty("string")
    private String stringVal;
    @JsonProperty("int")
    private Integer intVal;
    @JsonProperty("long")
    private Long longVal;
    @JsonProperty("boolean")
    private Boolean booleanVal;
    @JsonProperty("double")
    private Double doubleVal;
    @JsonProperty("float")
    private Float floatVal;
    @JsonProperty("date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", timezone = "America/Chicago")
    private Date dateVal;
    @JsonProperty("empty")
    private Object emptyVal;

    public String getStringVal() {
        return stringVal;
    }

    public void setStringVal(String stringVal) {
        this.stringVal = stringVal;
    }

    public Integer getIntVal() {
        return intVal;
    }

    public void setIntVal(Integer intVal) {
        this.intVal = intVal;
    }

    public Long getLongVal() {
        return longVal;
    }

    public void setLongVal(Long longVal) {
        this.longVal = longVal;
    }

    public Boolean getBooleanVal() {
        return booleanVal;
    }

    public void setBooleanVal(Boolean booleanVal) {
        this.booleanVal = booleanVal;
    }

    public Double getDoubleVal() {
        return doubleVal;
    }

    public void setDoubleVal(Double doubleVal) {
        this.doubleVal = doubleVal;
    }

    public Float getFloatVal() {
        return floatVal;
    }

    public void setFloatVal(Float floatVal) {
        this.floatVal = floatVal;
    }

    public Date getDateVal() {
        return dateVal;
    }

    public void setDateVal(Date dateVal) {
        this.dateVal = dateVal;
    }

    public Object getEmptyVal() {
        return emptyVal;
    }

    public void setEmptyVal(Object emptyVal) {
        this.emptyVal = emptyVal;
    }
}
