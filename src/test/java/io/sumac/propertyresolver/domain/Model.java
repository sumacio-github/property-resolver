package io.sumac.propertyresolver.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Model extends Fields {
    @JsonProperty("object")
    private Fields objectVal;
    @JacksonXmlElementWrapper(useWrapping = true)
    @JsonProperty("list")
    private List<Fields> list;

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
}
