package com.icl.yurec.datamodel;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * Created by anton.petrov on 21.11.2017.
 */

public class RequestContent {
    @JsonProperty("gumcid")
    private String gumcid;

    @JsonProperty("arr")
    private List<DataPojo> list;


    public List<DataPojo> getList() {
        return list;
    }

    public void setList(List<DataPojo> list) {
        this.list = list;
    }

    public String getGumcid() {
        return gumcid;
    }

    public void setGumcid(String gumcid) {
        this.gumcid = gumcid;
    }
}
