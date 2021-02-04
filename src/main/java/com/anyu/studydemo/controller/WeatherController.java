package com.anyu.studydemo.controller;


import com.anyu.studydemo.controller.advice.annotation.CommonResultType;
import com.anyu.studydemo.model.CommonResult;
import com.anyu.studydemo.model.entity.Weather;
import com.anyu.studydemo.model.entity.dto.WeatherDTO;
import com.anyu.studydemo.model.enums.ResultType;
import com.anyu.studydemo.service.WeatherService;
import com.anyu.studydemo.util.CommonUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(name = "天气有关的Api", path = "/weather")
@CommonResultType
public class WeatherController {

    @Resource
    private WeatherService weatherService;

    @GetMapping(name = "查询某个城市所有天气信息", path = "/city/{cityName}")
    public List<WeatherDTO> getWeathersByCityName(@PathVariable String cityName) {
        if (CommonUtils.isBlank(cityName)) {
            return null;
        }
        return weatherService.listWeathersByCityName(cityName);
    }

    @GetMapping(name = "查询所有天气", path = "/all")
    public List<WeatherDTO> listAllWeathers() {
        return weatherService.listWeathers();
    }

    @GetMapping(name = "查询某个城市最高天气", path = "/highest/{cityName}")
    public Weather getHighestOfCity(@PathVariable String cityName) {
        if (CommonUtils.isBlank(cityName)) {
            return null;
        }
        return weatherService.getHighestWeatherOfCity(cityName);
    }

    @PostMapping(name = "天气数据备份", path = "/backup")
    public Integer backupWeatherData(String backupPath,boolean append) {
        return weatherService.backup(backupPath, append);
    }


    @PostMapping(name = "天气数据备份恢复", path = "/recover")
    public Integer recoverWeatherData(String recoverFilePath) {
        return weatherService.recovery(recoverFilePath);
    }
}
