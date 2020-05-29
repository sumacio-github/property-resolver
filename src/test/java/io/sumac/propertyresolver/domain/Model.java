package io.sumac.propertyresolver.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import java.util.List;

public class Model extends Fields {
    @JsonProperty("object")
    private Fields objectVal;
    @JacksonXmlElementWrapper(useWrapping = true)
    @JsonProperty("list")
    private List<Fields> list;
    @JsonProperty("interpolated")
    private String interpolated;
    @JsonProperty("uninterpolated")
    private String uninterpolated;

    public Fields getObjectVal() {
        return objectVal;
    }

    public void setObjectVal(Fields objectVal) {
        this.objectVal = objectVal;
    }

    public List<Fields> getList() {
        return list;
    }

    public void setList(List<Fields> list) {
        this.list = list;
    }

    public String getInterpolated() {
        return interpolated;
    }

    public void setInterpolated(String interpolated) {
        this.interpolated = interpolated;
    }

    public String getUninterpolated() {
        return uninterpolated;
    }

    public void setUninterpolated(String uninterpolated) {
        this.uninterpolated = uninterpolated;
    }

    @Override
    public String toString() {
        return "Model{" +
                "objectVal=" + objectVal +
                ", list=" + list +
                ", interpolated='" + interpolated + '\'' +
                ", uninterpolated='" + uninterpolated + '\'' +
                '}';
    }
}
