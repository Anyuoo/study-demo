package com.anyu.studydemo.service.impl;

import com.anyu.studydemo.config.backup.BackupProperties;
import com.anyu.studydemo.exception.GlobalException;
import com.anyu.studydemo.mapper.WeatherMapper;
import com.anyu.studydemo.model.entity.Weather;
import com.anyu.studydemo.model.entity.dto.WeatherDTO;
import com.anyu.studydemo.model.enums.ResultType;
import com.anyu.studydemo.service.WeatherService;
import com.anyu.studydemo.util.CommonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    @Resource
    private BackupProperties backupProperties;
    @Resource
    private ObjectMapper objectMapper;

    @Override
    public List<WeatherDTO> listWeathersByCityName(@NotBlank String cityName) {
        return weatherMapper.listWeathersByCityName(cityName)
                .stream()
                .map(this::ConvertWeatherDtoToDo)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<WeatherDTO> listWeathers() {
        return weatherMapper.listWeathers()
                .stream()
                .map(this::ConvertWeatherDtoToDo)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Weather getHighestWeatherOfCity(String cityName) {
        return weatherMapper.getHighestWeatherOfCity(cityName);
    }

    /**
     * 备份恢复
     *
     * @param backupFileName 备份文件名
     */
    @Override
    public int recovery(String backupFileName) {
        //如果文件名为空，使用默认
        if (CommonUtils.isBlank(backupFileName)) {
            backupFileName = backupProperties.getFileName();
        }
        var resource = new ClassPathResource(backupProperties.getFullFileName(backupFileName));
        if (!resource.exists()) {
            logger.info("未发现备份资源");
            return 0;
        }
        InputStream stream;
        try {
            stream = resource.getInputStream();
        } catch (IOException e) {
            logger.info("读取备份资源失败");
            throw GlobalException.causeBy(ResultType.FAILED);
        }
        final BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        try (stream; reader) {
            var type = objectMapper.getTypeFactory()
                    .constructParametricType(List.class, Weather.class);
            String line;
            ArrayList<Weather> weathers;
            int num = 0;
            while ((line = reader.readLine()) != null) {
                if (CommonUtils.isBlank(line)) continue;
                 weathers  = objectMapper.readValue(line, type);
                if (weatherMapper.saveWeathers(weathers)){
                   num += weathers.size();
                }
            }
            return num;
        } catch (Exception e) {
            logger.info("恢复失败，信息：{}", e.getMessage());
            throw GlobalException.causeBy(ResultType.FAILED);
        }
    }

    /**
     * 备份
     *
     * @param backupFileName 备份文件名
     */
    @Override
    public int backup(String backupFileName, boolean append) {
        var weathers = weatherMapper.listWeathers();
        try {
            var weathersData = objectMapper.writeValueAsString(weathers);
            if (CommonUtils.isBlank(backupFileName)) {
                backupFileName = backupProperties.getFileName();
            }
            var backFilePath = backupProperties.getFullPath(backupFileName);
            if (CommonUtils.writeFileToDisk(backFilePath, append, weathersData)) {
                return weathers.size();
            }
            return -1;
        } catch (IOException e) {
            logger.info("备份失败，信息：{}", e.getMessage());
            throw GlobalException.causeBy(ResultType.FAILED);
        }
    }


    private WeatherDTO ConvertWeatherDtoToDo(Weather weather) {
        var weatherDTO = WeatherDTO.getInstance();
        //BeanUtils 复制相同类型，相同成员名的属性值
        BeanUtils.copyProperties(weather, weatherDTO);
        weatherDTO.setDateTime(CommonUtils.getDateTimeString(weather.getDateTime()))
                .setDateDesc(CommonUtils.getDateDespByNow(weather.getDateTime()));
        return weatherDTO;
    }
}
