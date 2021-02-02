package com.anyu.studydemo.service.impl;

import com.anyu.studydemo.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WeatherServiceImplTest  {
    @Resource
    private WeatherService weatherService;

    @Test
    public void testBackupData() {
        weatherService.backupWeathers("./a.txt");
    }

    @Test
    public void testRecovery() {
        weatherService.recovery("a.text");
    }
}