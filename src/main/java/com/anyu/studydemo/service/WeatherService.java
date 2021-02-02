package com.anyu.studydemo.service;

import com.anyu.studydemo.model.entity.Weather;

import javax.validation.constraints.NotBlank;
import java.util.List;

public interface WeatherService {
    List<Weather> listWeathersByCityName(@NotBlank String cityName);

    List<Weather> listWeathers();

    Weather getHighestWeatherOfCity(String cityName);

    boolean recovery(String dataSourcePath);

    boolean backupWeathers(String backupPath);
}
