package com.icl.yurec.datamodel;

/**
 * Created by anton.petrov on 21.11.2017.
 */
public class DataPojo {
    private int id;
    private String code;
    private String type;

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getType() {
        return type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Override
    public String toString() {
        return "DataPojo{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public String numberKey() {
        return code.concat(type);
    }

}
