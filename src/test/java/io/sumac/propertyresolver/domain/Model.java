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
    @JsonProperty("strings")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<String> strings;
    @JsonProperty("soloString")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<String> soloString;

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
}
