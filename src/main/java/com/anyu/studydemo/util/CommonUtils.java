package com.anyu.studydemo.util;

import java.io.*;
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
     * @param highest 最高温度，如果为null 生成最高温度
     */
    public static float getRandomTemperature(Float highest) {
        final Random random = new Random();
         int integer = random.nextInt(50);
         int decimal = random.nextInt(9);
        float  temperature = Float.parseFloat(integer + "." + decimal);
        if (highest == null) {
            return temperature;
        }
        final String lowest = String.format("%.2f", highest - Float.parseFloat(integer + "." + decimal));
        return Float.parseFloat(lowest);
    }


    /**
     * 判断字符串是否不为空
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
     * @param filePath 文件路径
     * @param data 数据
     */
    public static boolean writeFileToDisk(String filePath,String data) throws IOException {
        File file = new File(filePath);
        if (!file.exists() &&  !file.createNewFile())
            return false;

        FileOutputStream stream = new FileOutputStream(file);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stream));
        try (stream; writer) {
            writer.write(data);
            writer.flush();
            return true;
        } catch (Exception e) {
            throw e;
        }
    }

}
