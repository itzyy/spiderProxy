package com.spiderProxy.utils;

/**
 * Created by Administrator on 2017/10/7.
 */
public class SleepUtils {

    public static void sleep(long s){
        try {
            Thread.sleep(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
