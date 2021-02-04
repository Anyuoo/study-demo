package com.anyu.studydemo.mapper.impl;

import com.anyu.studydemo.mapper.WeatherMapper;
import com.anyu.studydemo.model.entity.Weather;
import com.anyu.studydemo.util.CommonUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component("weatherMapper")
public class WeatherMapperImpl implements WeatherMapper {
    //存储天气数据
    private final List<Weather> weathers = new ArrayList<>();

    /**
     * 初始化数据
     */
    @PostConstruct
    private void initWeathersData() {
        final List<String> cities = new ArrayList<>();
        cities.add("成都");
        cities.add("上海");
        cities.add("广州");
        cities.add("北京");
        final List<String> dates = new ArrayList<>();
        dates.add("昨天");
        dates.add("今天");
        dates.add("明天");

        for (String city : cities) {
            dates.forEach(date -> {
                //生成温度
                final float highest = CommonUtils.getRandomTemperature(null);
                final float lowest = CommonUtils.getRandomTemperature(highest);
                //生成天气数据
                Weather weather = Weather.getInstance()
                        .setId(CommonUtils.generateUUID(WEATHER_ID_SIZE))
                        .setCityName(city)
                        .setDateTime(CommonUtils.getRandomDateTime())
                        .setHigh(highest)
                        .setLow(lowest);
                this.weathers.add(weather);
            });
        }


    }

    @Override
    public List<Weather> listWeathers() {
        return weathers;
    }

    @Override
    public List<Weather> listWeathersByCityName(String cityName) {
        return weathers.stream()
                .filter(weather -> cityName.equals(weather.getCityName()))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Weather getHighestWeatherOfCity(String cityName) {
        Optional<Weather> highestWeather = weathers.stream()
                .filter(weather -> cityName.equals(weather.getCityName()))
                .max(Comparator.comparingDouble(Weather::getHigh));
        return highestWeather.orElse(Weather.getInstance());
    }

    @Override
    public boolean saveWeathers(List<Weather> weathers) {
        return this.weathers.addAll(weathers);
    }
}
