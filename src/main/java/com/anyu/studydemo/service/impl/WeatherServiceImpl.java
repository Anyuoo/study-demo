package com.anyu.studydemo.service.impl;

import com.anyu.studydemo.mapper.WeatherMapper;
import com.anyu.studydemo.model.entity.Weather;
import com.anyu.studydemo.service.WeatherService;
import com.anyu.studydemo.util.CommonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
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
    public boolean recovery(String backupFileName) {
        //如果文件名为空，使用默认
        if (CommonUtils.isBlank(backupFileName)) {
            backupFileName = DEFAULT_BACKUP_FILENAME;
        }
        String backFilePath =  backupFileName + "." + BACKUP_FILE_TYPE;
        ClassPathResource resource = new ClassPathResource(backFilePath);
        if (!resource.exists()) {
            return false;
        }
        InputStream stream;
        try {
             stream = resource.getInputStream();
        } catch (IOException e) {
            logger.info("未发现备份资源");
            return false;
        }
        final BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        try (stream; reader) {
           char[] buffers = new char[1024];
            String res = "";
            while (reader.read(buffers) != -1) {
                res = new String(buffers);
            }
            if (CommonUtils.isBlank(res)) {
                return false;
            }
            ObjectMapper objectMapper = new ObjectMapper();
            JavaType type = objectMapper.getTypeFactory().constructParametricType(List.class, Weather.class);
            ArrayList<Weather> weathers  = objectMapper.readValue(res, type);
            return weatherMapper.saveWeathers(weathers);

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
    public boolean backup(String backupFileName) {
        List<Weather> weathers = weatherMapper.listWeathers();
        JsonMapper jsonMapper = new JsonMapper();
        try {
            final String weathersData = jsonMapper.writeValueAsString(weathers);
            if (CommonUtils.isBlank(backupFileName)) {
                backupFileName = DEFAULT_BACKUP_FILENAME;
            }
            final String backFIlePath = BACKUP_FILE_PATH +
                    backupFileName +
                    "." +
                    BACKUP_FILE_TYPE;
            return CommonUtils.writeFileToDisk(backFIlePath, weathersData);
        } catch (IOException e) {
            logger.info("备份失败，信息：{}", e.getMessage());
            return false;
        }
    }
}
