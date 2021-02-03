package com.anyu.studydemo.util;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * 公共工具类
 *
 * @author Anyu
 * @since 2021/2/2 下午1:01
 */
public class CommonUtils {

    /**
     * 生辰随机温度
     *
     * @param highest 最高温度，如果为null 生成最高温度
     */
    public static float getRandomTemperature(Float highest) {
        final Random random = new Random();
        int integer = random.nextInt(50);
        int decimal = random.nextInt(9);
        float temperature = Float.parseFloat(integer + "." + decimal);
        if (highest == null) {
            return temperature;
        }
        final String lowest = String.format("%.2f", highest - Float.parseFloat(integer + "." + decimal));
        return Float.parseFloat(lowest);
    }


    /**
     * 判断字符串是否不为空
     *
     * @return 为空返回 false ; 不为空返回 true
     */
    public static boolean isNotBlank(String target) {
        return target != null && target.trim().length() >= 1;
    }

    public static boolean isBlank(String target) {
        return target == null || target.trim().length() < 1;
    }

    /**
     * 想磁盘写入文件
     *
     * @param fileName 文件
     * @param data     数据
     */
    public static boolean writeFileToDisk(String fileName, String data) throws IOException {
        File file = new File(fileName);
        if (!file.exists() && !file.createNewFile())
            return false;
        FileOutputStream stream = new FileOutputStream(file);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stream));
        try (stream; writer) {
            writer.write(data);
            writer.flush();
            return true;
        }
    }

    /**
     * 生成时间 昨天，今天，明天
     */
    public static LocalDateTime getRandomDateTime() {
        final LocalDateTime now = LocalDateTime.now();
        final Random random = new Random();
        final int hours = random.nextInt(48);
        return now.minusDays(1).plusHours(hours);
    }



    /**
     * 通过日期的到昨天今天明天描述
     */
    public static String getDateDespByNow(LocalDateTime time) {
        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime today = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 0, 0, 0, 0);
        time = LocalDateTime.of(time.getYear(), time.getMonth(), time.getDayOfMonth(), 0, 0, 0, 0);
        switch (time.compareTo(today)) {
            case -1:
                return "昨天";
            case 0:
                return "今天";
            case 1:
                return "明天";
            default:
                return "未知";
        }
    }

    /**
     * 返回固定格式日期
     */
    public static String getDateTimeString(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
