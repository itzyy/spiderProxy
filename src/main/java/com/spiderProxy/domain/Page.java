package com.spiderProxy.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zouyy on 2017/10/12.
 */
public class Page implements Serializable{
    /**
     * url
     */
    private String url;
    /**
     * 页面内容
     */
    private String context;
    /**
     * proxy ip集合
     */
    private List<String> proxys = new ArrayList<String>();
    /**
     * 下一页ip汇总
     */
    private String nextUrl = new String();

    /**
     * 添加代理ip
     * @param url
     */
    public void addProxy(String url){
        this.proxys.add(url);
    }

    public String getNextUrl() {
        return nextUrl;
    }

    public void setNextUrl(String nextUrl) {
        this.nextUrl = nextUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public List<String> getProxys() {
        return proxys;
    }
}
