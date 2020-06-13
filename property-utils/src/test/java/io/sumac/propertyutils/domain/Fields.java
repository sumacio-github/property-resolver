package io.sumac.propertyutils.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import java.util.Date;
import java.util.List;

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
    @JsonProperty("strings")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<String> strings;
    @JsonProperty("soloString")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<String> soloString;

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

    public List<String> getStrings() {
        return strings;
    }

    public void setStrings(List<String> strings) {
        this.strings = strings;
    }

    public List<String> getSoloString() {
        return soloString;
    }

    public void setSoloString(List<String> soloString) {
        this.soloString = soloString;
    }

    @Override
    public String toString() {
        return "Fields{" +
                "stringVal='" + stringVal + '\'' +
                ", intVal=" + intVal +
                ", longVal=" + longVal +
                ", booleanVal=" + booleanVal +
                ", doubleVal=" + doubleVal +
                ", floatVal=" + floatVal +
                ", dateVal=" + dateVal +
                ", emptyVal=" + emptyVal +
                ", strings=" + strings +
                ", soloString=" + soloString +
                '}';
    }
}
