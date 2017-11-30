package com.icl.yurec.datamodel;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class Answer implements Serializable {

    @XmlElement(name = "id")
    private int id;
    @XmlElement(name = "result")
    private int result;
    @XmlElement(name = "event_name")
    private String eventName;
    @XmlElement(name = "description")
    private String description;

    public Answer() {}

    public Answer(int id, int result, String eventName, String description) {
        this.id = id;
        this.result = result;
        this.eventName = eventName;
        this.description = description;
    }

    public Answer(int id, int result) {
        this(id, result, null, null);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
