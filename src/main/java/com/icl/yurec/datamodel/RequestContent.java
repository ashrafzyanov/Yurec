package com.icl.yurec.datamodel;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class RequestContent {

    @XmlElement(name = "gumcid")
    private String gumcid;

    @XmlElement(name = "arr")
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
