package com.thea.itailor.recommend.bean;

/**
 * Created by Thea on 2015/9/12 0012.
 */
public class Exponent {
    private String name;
    private String value;
    private String detail;

    public Exponent() {
    }

    public Exponent(String name, String value, String detail) {
        this.name = name;
        this.value = value;
        this.detail = detail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
