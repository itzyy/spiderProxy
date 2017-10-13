package com.spiderProxy.utils;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Zouyy on 2017/10/13.
 */
public class Config {

    private static Properties prop;

    static {
        prop = new Properties();
        try {
            //读取文件流之后，对prop进行加载
            prop.load(Config.class.getClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 线程
    public static int nThread= Integer.parseInt(prop.getProperty("nThread"));
    public static int million_1= Integer.parseInt(prop.getProperty("million_1"));
    public static int million_5= Integer.parseInt(prop.getProperty("million_5"));
    //redis-config
    public static String redis_host= prop.getProperty("redis.host");
    public static int redis_port= Integer.parseInt(prop.getProperty("redis.port"));
    public static int redis_maxIdle= Integer.parseInt(prop.getProperty("redis.maxIdle"));
    public static int redis_maxTotal= Integer.parseInt(prop.getProperty("redis.maxTotal"));
    public static int redis_maxWaitMillis= Integer.parseInt(prop.getProperty("redis.maxWaitMillis"));
    public static Boolean redis_testOnBorrow= Boolean.parseBoolean(prop.getProperty("redis.testOnBorrow"));
    public static String proxy_info= prop.getProperty("proxy_info");
    public static String proxy_url= prop.getProperty("proxy_url");




}
