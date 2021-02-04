package com.anyu.studydemo.util;


import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class CommonUtilsTest {

    @Test
    public void testGetLowest() {
        for (int i = 0; i < 100; i++) {
            float highest = CommonUtils.getRandomTemperature(null);
            float lowest = CommonUtils.getRandomTemperature(highest);
            System.out.println("lowest:" + lowest + "---->highest:" + highest);
        }

    }

    @Test
    public void testCommonUtils() {
        for (int i = 0; i < 20; i++) {
            LocalDateTime a = CommonUtils.getRandomDateTime();
            String date = a.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String desp = CommonUtils.getDateDespByNow(a);
            System.out.println("time: " + date + "===desption: " + desp);
        }


    }

}
