package com.anyu.studydemo.util;


import org.junit.jupiter.api.Test;


public class CommonUtilsTest {

    @Test
    public void testGetLowest() {
        for (int i = 0; i < 100; i++) {
            float highest = CommonUtils.getRandomTemperature(null);
            float lowest = CommonUtils.getRandomTemperature(highest);
            System.out.println("lowest:"+lowest+"---->highest:"+highest);
        }

    }

}
