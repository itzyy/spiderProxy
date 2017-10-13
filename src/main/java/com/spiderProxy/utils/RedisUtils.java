package com.spiderProxy.utils;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;

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
        List<String> list = resource.lrange(key, start, end);
        resource.close();
        return list;
    }

    public synchronized void add(String lowKey, String url) {
        Jedis resource = jedisPool.getResource();
        resource.lpush(lowKey, url);
        resource.close();
    }

    public synchronized String pull(String key) {
        Jedis resource = jedisPool.getResource();
        String result = resource.rpop(key);
        resource.close();
        return result;
    }

}
