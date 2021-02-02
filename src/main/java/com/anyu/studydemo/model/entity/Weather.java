package com.anyu.studydemo.model.entity;


public class Weather {
    private String cityName;
    private String date;
    private Float low;
    private Float high;


    public static Weather getInstance() {
        return new Weather();
    }

    public String getCityName() {
        return cityName;
    }

    public Weather setCityName(String cityName) {
        this.cityName = cityName;
        return this;
    }

    public String getDate() {
        return date;
    }

    public Weather setDate(String date) {
        this.date = date;
        return this;
    }

    public Float getLow() {
        return low;
    }

    public Weather setLow(Float low) {
        this.low = low;
        return this;
    }

    public Float getHigh() {
        return high;
    }

    public Weather setHigh(Float high) {
        this.high = high;
        return this;
    }
}
