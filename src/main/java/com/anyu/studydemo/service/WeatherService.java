package com.anyu.studydemo.service;

import com.anyu.studydemo.model.entity.Weather;
import com.anyu.studydemo.util.GlobalConstant;

import javax.validation.constraints.NotBlank;
import java.util.List;

public interface WeatherService extends GlobalConstant {
    List<Weather> listWeathersByCityName(@NotBlank String cityName);

    List<Weather> listWeathers();

    Weather getHighestWeatherOfCity(String cityName);

    boolean recovery(String backupFileName);

    boolean backup(String backupFileName);
}
