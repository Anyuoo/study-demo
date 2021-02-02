package com.anyu.studydemo.controller;


import com.anyu.studydemo.model.CommonResult;
import com.anyu.studydemo.model.entity.Weather;
import com.anyu.studydemo.model.enums.ResultType;
import com.anyu.studydemo.service.WeatherService;
import com.anyu.studydemo.util.CommonUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(name = "天气有关的Api",path = "/weather")
public class WeatherController {

    @Resource
    private WeatherService weatherService;

    @GetMapping(name = "查询某个城市所有天气信息",path = "/city/{cityName}")
    public CommonResult<List<Weather>> getWeathersByCityName(@PathVariable String cityName) {
        if (CommonUtils.isBlank(cityName)) {
            return CommonResult.with(ResultType.FAILED, null);
        }
        return CommonResult.with(ResultType.SUCCESS,weatherService.listWeathersByCityName(cityName));
    }

    @GetMapping(name = "查询所有天气",path = "/all")
    public CommonResult<List<Weather>> listAllWeathers() {
        return CommonResult.with(ResultType.SUCCESS, weatherService.listWeathers());
    }

    @GetMapping(name = "查询某个城市最高天气",path = "/highest/{cityName}")
    public CommonResult<Weather> getHighestOfCity(@PathVariable String cityName) {
        if (CommonUtils.isBlank(cityName)) {
            return CommonResult.with(ResultType.FAILED, null);
        }
        Weather weatherOfCity = weatherService.getHighestWeatherOfCity(cityName);
        return CommonResult.with(ResultType.SUCCESS, weatherOfCity);
    }

}
