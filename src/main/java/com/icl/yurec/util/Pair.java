package com.icl.yurec.util;

/**
 * Created by anton.petrov on 24.11.2017.
 */
public class Pair {
    private String event_name;
    private String description;

    public Pair(String event_name, String description) {
        this.event_name = event_name;
        this.description = description;
    }

    public Pair() {
    }

    public String getEvent_name() {
        return event_name;
    }

    public String getDescription() {
        return description;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
