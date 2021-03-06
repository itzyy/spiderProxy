package com.spiderProxy.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 创建工具类，主要为了需要多次初始化httpclient的时候，减少冗余代码
 */
public class PageUtils {

    private static RedisUtils redisUtils = new RedisUtils();
    /**
     * 根据url获取页面内容
     *
     * @param url
     * @return
     */
    public static String getContext(String url) {
        Logger logger = LoggerFactory.getLogger(PageUtils.class);
        //获取httpclient对象（可以任务是获取到了一个浏览器对象）
        HttpClientBuilder builder = HttpClients.custom();
        //设置代理ip,不能直接写死，建议从ip代理库获取
        HttpHost host = new HttpHost("180.201.30.108", 8998);
        CloseableHttpClient client =  builder.setProxy(host).build();

//        String proxy = redisUtils.pull(RedisUtils.proxy_info);
//        String[] proxyArray= proxy.split(":");
//        String ip=proxyArray[0];
//        String port=proxyArray[1];
//        HttpHost host = new HttpHost(ip, Integer.parseInt(port));
//        CloseableHttpClient client =  builder.setProxy(host).build();
//        System.out.println("=========="+proxy);

//        CloseableHttpClient client =  builder.build();

        //封装get请求
//        HttpGet httpGet = new HttpGet(url);;
        HttpPost httpGet = new HttpPost(url);;
        //设置header请求(西刺要加上头部信息)
        httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
        httpGet.setHeader("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
        httpGet.setHeader("Accept-Encoding", "gzip, deflate");
        httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");

        String content="";
        try {
            long startTime = System.currentTimeMillis();
//            RequestConfig requestConfig = RequestConfig.custom()
//                    .setConnectTimeout(5000)//设置连接超时时间，单位毫秒
//                    .setConnectionRequestTimeout(5000)//设置连接超时时间，单位毫秒
//                    .setSocketTimeout(10000)//设置连接超时时间，单位毫秒
//                    .build();
//            httpGet.setConfig(requestConfig);
            //执行请求，获取response内容
            CloseableHttpResponse response = client.execute(httpGet);
            int statuscode =response.getStatusLine().getStatusCode();
            if(statuscode==503 || statuscode==301|| statuscode==400){
//                logger.error("页面下载失败，statuscode：{}，代理IP：{}，url:{}",statuscode,proxy,url);
                return "";
            }
            //获取页面实体对象
            HttpEntity entity = response.getEntity();
            //只能使用一次toString ，再次使用会发生stream closed的问题
            content = EntityUtils.toString(entity);
//            logger.info("页面下载成功，代理IP：{}，消耗时间：{}，url:{}",proxy,System.currentTimeMillis()-startTime,url);
            logger.info("页面下载成功，代理IP：{}，消耗时间：{}，url:{}","".concat(":").concat(""),System.currentTimeMillis()-startTime,url);
        } catch (IOException e) {
            //可以在这里把失效的代理ip从本地代理库中删除掉，或者记住后，让后面的其他程序分析日志进行处理
//            logger.error("页面下载失败，代理IP：{}，url:{}",proxy,url);
            logger.error("页面下载失败，url:{}",url);
            e.printStackTrace();
        }
        return content;
    }
}
