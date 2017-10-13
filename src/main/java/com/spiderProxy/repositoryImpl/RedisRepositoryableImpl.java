package com.spiderProxy.repositoryImpl;

import com.spiderProxy.repository.Repositoryable;
import com.spiderProxy.utils.RedisUtils;

/**
 * Created by Zouyy on 2017/10/13.
 */
public class RedisRepositoryableImpl implements Repositoryable {
    private RedisUtils redisUtils = new RedisUtils();

    /**
     * 保存下一页
     * @param url
     */
    public void add(String url) {
        redisUtils.add(RedisUtils.proxy_url,url);
    }

    public String pull() {
        return redisUtils.pull(RedisUtils.proxy_url);
    }
}
