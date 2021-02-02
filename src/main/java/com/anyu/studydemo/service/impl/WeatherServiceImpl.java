package com.anyu.studydemo.service.impl;

import com.anyu.studydemo.mapper.WeatherMapper;
import com.anyu.studydemo.model.entity.Weather;
import com.anyu.studydemo.service.WeatherService;
import com.anyu.studydemo.util.CommonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * weather 服务层（业务逻辑处理）
 *
 * @author Anyu
 * @since 2021/2/2 下午12:57
 */
@Service("weatherService")
public class WeatherServiceImpl implements WeatherService {
    private final static Logger logger = LoggerFactory.getLogger(WeatherServiceImpl.class);

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
     * @param backupFileName 备份文件名
     */
    @Override
//    @SuppressWarnings("unchecked")
    public boolean recovery(String backupFileName) {
        final InputStream stream = this.getClass().getClassLoader().getResourceAsStream(backupFileName);
        if (stream == null) {
            logger.info("未发现备份资源");
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
            List<Weather> list = jsonMapper.convertValue(res, new TypeReference<List<Weather>>(){});
            return weatherMapper.saveWeathers(list);
        } catch (Exception e) {
            logger.info("恢复失败，信息：{}", e.getMessage());
            return false;
        }
    }

    /**
     * 备份
     * @param backupFileName 备份文件名
     */
    @Override
    public boolean backupWeathers(String backupFileName) {
        List<Weather> weathers = weatherMapper.listWeathers();
        JsonMapper jsonMapper = new JsonMapper();
        try {
            final String weathersData = jsonMapper.writeValueAsString(weathers);
            return CommonUtils.writeFileToDisk( BACKUP_FILE_PATH + backupFileName, weathersData);
        } catch (IOException e) {
            logger.info("备份失败，信息：{}", e.getMessage());
            return false;
        }
    }
}
