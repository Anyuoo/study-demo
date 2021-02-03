package com.anyu.studydemo.model.entity;


import java.time.LocalDateTime;

public class Weather {
    private String cityName;
    private LocalDateTime dateTime;
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

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Weather setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
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
