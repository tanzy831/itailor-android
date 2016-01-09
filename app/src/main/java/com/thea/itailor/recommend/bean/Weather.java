package com.thea.itailor.recommend.bean;

/**
 * Created by Thea on 2015/8/15 0015.
 */
public class Weather {
    private String city;
    private int highTemperature;
    private int lowTemperature;

    public Weather() {
    }

    public Weather(String city, int highTemperature, int lowTemperature) {
        this.city = city;
        this.highTemperature = highTemperature;
        this.lowTemperature = lowTemperature;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getHighTemperature() {
        return highTemperature;
    }

    public void setHighTemperature(int highTemperature) {
        this.highTemperature = highTemperature;
    }

    public int getLowTemperature() {
        return lowTemperature;
    }

    public void setLowTemperature(int lowTemperature) {
        this.lowTemperature = lowTemperature;
    }
}
