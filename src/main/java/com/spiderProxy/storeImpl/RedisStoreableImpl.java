package com.spiderProxy.storeImpl;

import com.spiderProxy.store.Storeable;
import com.spiderProxy.utils.RedisUtils;

/**
 * Created by Zouyy on 2017/10/13.
 */
public class RedisStoreableImpl implements Storeable {

    private RedisUtils redisUtils = new RedisUtils();

    /**
     * 将prxoy信息保持到redis,数据格式:192.168.10:8181
     * @param proxy
     */
    public void storeProxy(String proxy) {
        this.redisUtils.add(RedisUtils.proxy_info,proxy);
    }

    /**
     * 将代理网站的分页url，进行保存
     * @param url
     */
    public void storeUrl(String url){
        this.redisUtils.add(RedisUtils.proxy_url,url);
    }

}
