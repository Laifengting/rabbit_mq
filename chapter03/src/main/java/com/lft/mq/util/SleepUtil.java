package com.lft.mq.util;

/**
 * 睡眠工具类
 */
public class SleepUtil {
    public static void sleep(Integer seconds) {
        try {
            Thread.sleep(1000 * seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
