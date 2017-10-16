package com.spiderProxy.utils;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class RedisUtils {

    public static String proxy_info = Config.proxy_info;
    public static String proxy_url = Config.proxy_url;

    JedisPool jedisPool = null;

    public RedisUtils() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(Config.redis_maxIdle);
        poolConfig.setMaxTotal(Config.redis_maxTotal);
        poolConfig.setMaxWaitMillis(Config.redis_maxWaitMillis);
        poolConfig.setTestOnBorrow(Config.redis_testOnBorrow);
        jedisPool = new JedisPool(poolConfig, Config.redis_host, Config.redis_port);
    }


    public List<String> lrange(String key, int start, int end) {
        Jedis resource = jedisPool.getResource();
        Set<String> smembers = resource.smembers(key);
        return Arrays.asList(smembers.toArray(new String[smembers.size()]));
    }
    public  void add(String lowKey, String url) {
        Jedis resource = jedisPool.getResource();
        resource.sadd(lowKey, url);
        resource.close();
    }

    public  String pull(String key) {
        Jedis resource = jedisPool.getResource();
        String result = resource.spop(key);
        resource.close();
        return result;
    }
//public void add(String lowKey, String url) {
//    Jedis resource = jedisPool.getResource();
//    resource.lpush(lowKey, url);
//    resource.close();
//}
//    public String pull(String key) {
//        Jedis resource = jedisPool.getResource();
//        String result = resource.rpop(key);
//        resource.close();
//        return result;
//    }


}
