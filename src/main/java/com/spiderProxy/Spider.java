package com.spiderProxy;

import com.spiderProxy.domain.Page;
import com.spiderProxy.download.Downloadable;
import com.spiderProxy.downloadImpl.HttpClientDownloadableImpl;
import com.spiderProxy.process.Processable;
import com.spiderProxy.processableImpl.XiCiProcessableImpl;
import com.spiderProxy.repository.Repositoryable;
import com.spiderProxy.repositoryImpl.RedisRepositoryableImpl;
import com.spiderProxy.store.Storeable;
import com.spiderProxy.storeImpl.RedisStoreableImpl;
import com.spiderProxy.utils.Config;
import com.spiderProxy.utils.SleepUtils;
import com.spiderProxy.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 爬虫代理主程序
 */
public class Spider {

    private Logger logger = LoggerFactory.getLogger(Spider.class);

    private Downloadable<Page> downloadable;
    private Processable processable;
    private Storeable storeable;
    private Repositoryable repositoryable;
    private ExecutorService executor = Executors.newFixedThreadPool(Config.nThread);

    public static void main(String[] args) {
        Spider spider = new Spider();
        spider.setDownloadable(new HttpClientDownloadableImpl());
        spider.setProcessable(new XiCiProcessableImpl());
        spider.setStoreable(new RedisStoreableImpl());
        spider.setRepositoryable(new RedisRepositoryableImpl());
        String url = "http://www.xicidaili.com/wt";
        spider.addUrl(url);
        spider.start();

    }

    /**
     * 启动爬虫
     */
    public void start() {
        check();
        while (true) {
            final String url = this.repositoryable.pull();
            if (StringUtils.isNotEmpty(url)) {
//                executor.execute(new Runnable() {
//                    public void run() {
                        Page page = Spider.this.download(url);
                        if (StringUtils.isNotEmpty(page.getContext())) {
                            Spider.this.process(page);
                            List<String> proxyList = page.getProxys();
                            if (StringUtils.isNotEmpty(page.getNextUrl())) {
                                Spider.this.repositoryable.add(page.getNextUrl());
                            }
                            for (String proxy : proxyList) {
                                Spider.this.storeable.storeProxy(proxy);
                            }
                        } else {
                            Spider.this.addUrl(page.getUrl());
                            logger.error("页面下载出现异常，将未下载成功的url重新进行下载，url:{}", page.getUrl());
                        }
//                    }
//                });
                SleepUtils.sleep(Config.million_1);
            } else {
                logger.warn("没有url了");
                SleepUtils.sleep(Config.million_5);
            }
        }

    }

    /**
     * 下载网页
     *
     * @param url 网页连接
     * @return page对象
     */
    public Page download(String url) {
        return this.downloadable.download(url);
    }

    /**
     * 解析网页
     *
     * @param page
     */
    public void process(Page page) {
        this.processable.process(page);
    }

    /**
     * 启动前，配置检查
     */
    private void check() {
        logger.info("开始执行配置检查");
        if (processable == null) {
            String message = "需要设置解析类";
            logger.error(message);
            throw new RuntimeException(message);
        }
        //打印实现类
        logger.info("========================配置检查开始=========================");
        logger.info("downloadable的实现类是:{}", downloadable.getClass().getName());
        logger.info("processable的实现类是:{}", processable.getClass().getName());
        logger.info("storeable的实现类是:{}", storeable.getClass().getName());
        logger.info("repositoyable的实现类是:{}", repositoryable.getClass().getName());
        logger.info("========================配置检查结束=========================");

    }

    /**
     * 添加url
     *
     * @param url
     */
    public void addUrl(String url) {
        this.repositoryable.add(url);
    }

    public void setDownloadable(Downloadable<Page> downloadable) {
        this.downloadable = downloadable;
    }

    public void setProcessable(Processable processable) {
        this.processable = processable;
    }

    public void setStoreable(Storeable storeable) {
        this.storeable = storeable;
    }

    public void setRepositoryable(Repositoryable repositoryable) {
        this.repositoryable = repositoryable;
    }
}
