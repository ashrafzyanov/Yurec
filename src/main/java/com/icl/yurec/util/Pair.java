package com.icl.yurec.util;

public class Pair {
    private String eventName;
    private String description;

    public Pair(String eventName, String description) {
        this.eventName = eventName;
        this.description = description;
    }

    public Pair() {
    }

    public String getEventName() {
        return eventName;
    }

    public String getDescription() {
        return description;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
