package com.spiderProxy.downloadImpl;

import com.spiderProxy.domain.Page;
import com.spiderProxy.download.Downloadable;
import com.spiderProxy.utils.PageUtils;

public class HttpClientDownloadableImpl implements Downloadable<Page> {

    /**
     * 下载功能
     * @param url   下载连接
     */
    public Page download(String url) {
        Page page = new Page();
        String context = PageUtils.getContext(url);
        page.setContext(context);
        page.setUrl(url);
        return page;
    }
}
