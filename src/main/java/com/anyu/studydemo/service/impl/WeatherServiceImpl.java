package com.anyu.studydemo.service.impl;

import com.anyu.studydemo.mapper.WeatherMapper;
import com.anyu.studydemo.model.entity.Weather;
import com.anyu.studydemo.service.WeatherService;
import com.anyu.studydemo.util.CommonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import io.micrometer.core.instrument.util.JsonUtils;
import lombok.val;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.io.*;
import java.util.List;

/**
 * weather 服务层（业务逻辑处理）
 *
 * @author Anyu
 * @since 2021/2/2 下午12:57
 */
@Service("weatherService")
public class WeatherServiceImpl implements WeatherService {

    @Resource
    private WeatherMapper weatherMapper;

    @Override
    public List<Weather> listWeathersByCityName(@NotBlank String cityName) {
        return weatherMapper.listWeathersByCityName(cityName);
    }

    @Override
    public List<Weather> listWeathers() {
        return weatherMapper.listWeathers();
    }

    @Override
    public Weather getHighestWeatherOfCity(String cityName) {
        return weatherMapper.getHighestWeatherOfCity(cityName);
    }

    /**
     * 备份恢复
     * @param dataSourcePath 元备份地址
     */
    @Override
    public boolean recovery(String dataSourcePath) {
        final InputStream stream = this.getClass().getClassLoader().getResourceAsStream(dataSourcePath);
        if (stream == null) {
            return false;
        }
        final BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        try (stream; reader) {
           char[] buffers = new char[1024];
            String res = null;
            while (reader.read(buffers) != -1) {
                res = new String(buffers);
            }
            JsonMapper jsonMapper = new JsonMapper();
            List<Weather> list = (List<Weather>)jsonMapper.convertValue(res, List.class);
            System.out.println("========");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 备份
     * @param backupPath 备份地址
     */
    @Override
    public boolean backupWeathers(String backupPath) {
        List<Weather> weathers = weatherMapper.listWeathers();
        JsonMapper jsonMapper = new JsonMapper();
        try {
            final String weathersData = jsonMapper.writeValueAsString(weathers);
            return CommonUtils.writeFileToDisk(backupPath, weathersData);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
