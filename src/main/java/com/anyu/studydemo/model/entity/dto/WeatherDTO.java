package com.anyu.studydemo.model.entity.dto;

import java.time.LocalDateTime;

/**
*
 * 用于传输对象信息
* @author Anyu
* @since 2021/2/3 下午3:14
*/
public class WeatherDTO {
    private String cityName;
    private String dateTime;
    private String dateDesc;
    private Float low;
    private Float high;


    public static WeatherDTO getInstance() {
        return new WeatherDTO();
    }
    public String getCityName() {
        return cityName;
    }

    public String getDateTime() {
        return dateTime;
    }

    public WeatherDTO setDateTime(String dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    public WeatherDTO setCityName(String cityName) {
        this.cityName = cityName;
        return this;
    }

    public String getDateDesc() {
        return dateDesc;
    }

    public WeatherDTO setDateDesc(String dateDesc) {
        this.dateDesc = dateDesc;
        return this;
    }

    public Float getLow() {
        return low;
    }

    public WeatherDTO setLow(Float low) {
        this.low = low;
        return this;
    }

    public Float getHigh() {
        return high;
    }

    public WeatherDTO setHigh(Float high) {
        this.high = high;
        return this;
    }
}
