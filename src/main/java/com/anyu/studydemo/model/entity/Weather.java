package com.anyu.studydemo.model.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 数据库实体对象
 *
 * @author Anyu
 * @since 2021/2/4 上午11:28
 */
public class Weather {
    private String id;
    private String cityName;
    private LocalDateTime dateTime;
    private Float low;
    private Float high;


    public static Weather getInstance() {
        return new Weather();
    }

    public String getId() {
        return id;
    }

    public Weather setId(String id) {
        this.id = id;
        return this;
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
