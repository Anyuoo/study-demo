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
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

    @Override
    public List<WeatherDTO> listWeathersByCityName(@NotBlank String cityName) {
        return weatherMapper.listWeathersByCityName(cityName)
                .stream()
                .map(weather -> WeatherDTO.getInstance()
                        .setCityName(weather.getCityName())
                        .setDateTime(CommonUtils.getDateTimeString(weather.getDateTime()))
                        .setDateDesc(CommonUtils.getDateDespByNow(weather.getDateTime()))
                        .setHigh(weather.getHigh())
                        .setHigh(weather.getLow()))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<WeatherDTO> listWeathers() {
        return weatherMapper.listWeathers()
                .stream()
                .map(weather -> WeatherDTO.getInstance()
                        .setCityName(weather.getCityName())
                        .setDateTime(CommonUtils.getDateTimeString(weather.getDateTime()))
                        .setDateDesc(CommonUtils.getDateDespByNow(weather.getDateTime()))
                        .setHigh(weather.getHigh())
                        .setLow(weather.getLow()))
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
    public boolean recovery(String backupFileName) {
        //如果文件名为空，使用默认
        if (CommonUtils.isBlank(backupFileName)) {
            backupFileName = backupProperties.getFileName();
        }
        var backFilePath = backupProperties.getFullPath(backupFileName);
        var resource = new ClassPathResource(backFilePath);
        if (!resource.exists()) {
            logger.info("未发现备份资源");
            return false;
        }
        InputStream stream;
        try {
            stream = resource.getInputStream();
        } catch (IOException e) {
            logger.info("未发现备份资源");
            throw GlobalException.causeBy(ResultType.FAILED);
        }
        final BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        try (stream; reader) {
            var buffers = new char[1024];
            String res = "";
            while (reader.read(buffers) != -1) {
                res = new String(buffers);
            }
            if (CommonUtils.isBlank(res)) return false;
            var objectMapper = new ObjectMapper();
            var type = objectMapper.getTypeFactory()
                    .constructParametricType(List.class, Weather.class);
            final ArrayList<Weather> weathers = objectMapper.readValue(res, type);
            return weatherMapper.saveWeathers(weathers);

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
    public boolean backup(String backupFileName) {
        var weathers = weatherMapper.listWeathers();
        var jsonMapper = new JsonMapper();
        try {
            var weathersData = jsonMapper.writeValueAsString(weathers);
            if (CommonUtils.isBlank(backupFileName)) {
                backupFileName = backupProperties.getFileName();
            }
            var backFilePath = backupProperties.getFullPath(backupFileName);
            return CommonUtils.writeFileToDisk(backFilePath, weathersData);
        } catch (IOException e) {
            logger.info("备份失败，信息：{}", e.getMessage());
            throw GlobalException.causeBy(ResultType.FAILED);
        }
    }
}
