package com.anyu.studydemo.service.impl;

import com.anyu.studydemo.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class WeatherServiceImplTest {
    @Resource
    private WeatherService weatherService;

    @Test
    public void testBackupData() {
        weatherService.backup("a.txt",false);
    }

    @Test
    public void testRecovery() {
        weatherService.recovery("a.text");
    }
}