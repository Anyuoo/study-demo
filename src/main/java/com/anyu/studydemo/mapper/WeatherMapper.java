package com.anyu.studydemo.mapper;

import com.anyu.studydemo.model.entity.Weather;
import com.anyu.studydemo.util.GlobalConstant;

import java.util.List;

/**
 * weather 数据访问层
 *
 * @author Anyu
 * @since 2021/2/2 下午12:59
 */

public interface WeatherMapper extends GlobalConstant {

    /**
     * 查询所有天气数据
     */
    List<Weather> listWeathers();

    /**
     * 根据城市名查询天气
     * @param cityName 需要查询的城市
     * @return 该城市的天气数据
     */
    List<Weather> listWeathersByCityName(String cityName);


    /**
     * 获取某个城市最高温度信息
     * @param cityName 需要查询的城市
     * @return 该城市的天气数据
     */
   Weather getHighestWeatherOfCity(String cityName);


    boolean saveWeathers(List<Weather> weathers);
}
