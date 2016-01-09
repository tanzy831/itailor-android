package com.thea.itailor.recommend.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thea on 2015/9/12 0012.
 */
public class DetailWeather {
    private String city;
    private int temperature;
    private String wind;
    private String humidity;
    private List<Exponent> exponents = new ArrayList<>();

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public List<Exponent> getExponents() {
        return exponents;
    }

    public void setExponents(List<Exponent> exponents) {
        this.exponents = exponents;
    }

    public Exponent getExponent(int index) {
        return exponents.get(index);
    }

    public void addExponent(Exponent exponent) {
        exponents.add(exponent);
    }

    public Exponent getExponent(String name) {
        for (Exponent e : exponents) {
            if (e.getName().equalsIgnoreCase(name))
                return e;
        }
        return null;
    }
}
