package com.spiderProxy.processableImpl;

import com.spiderProxy.domain.Page;
import com.spiderProxy.process.Processable;
import com.spiderProxy.utils.HtmlUtils;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import java.util.List;

/**
 * 解析西刺
 */
public class XiCiProcessableImpl implements Processable {

    /**
     * 解析网页
     *
     * @param page
     */
    public void process(Page page) {
        //使用htmlcleaner解析对象
        HtmlCleaner htmlCleaner = new HtmlCleaner();
        //对页面进行封装。转换成一个tagnode对象,通过xpath对页面元素可以进行快速标记
        TagNode rootNode = htmlCleaner.clean(page.getContext());
        try {
            parse(rootNode, page);
            //解析下一页
            String href = HtmlUtils.getAttributeByName(rootNode, "//*[@id=\"body\"]/div[2]/a[11]", "href");
            if (href != null && href.length() > 0) {
                page.setNextUrl("http://www.xicidaili.com" + href);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析ip
     *
     * @param rootNode
     * @param page
     */
    public void parse(TagNode rootNode, Page page) {
        try {
            Object[] trs = rootNode.evaluateXPath("//table[@id=\"ip_list\"]//tr[position()>1]");
            for (Object tr : trs) {
                TagNode trNode = (TagNode) tr;
                List<TagNode> trTagNodes = trNode.getChildTagList();
                //ip
                String ip = trTagNodes.get(1).getText().toString().trim();
                //port
                String port = trTagNodes.get(2).getText().toString().trim();
                //是否匿名
                boolean isAnony = trTagNodes.get(4).getText().toString().trim().equals("高匿");
                //IP类型：http、https
                boolean type = trTagNodes.get(5).getText().toString().trim().equals("HTTP");
                //连接速度
                String titleSpeed = trTagNodes.get(6).getChildTagList().get(0).getAttributeByName("title");
                titleSpeed = titleSpeed.substring(0, titleSpeed.length() - 1);
                boolean connSpeed = Double.valueOf(titleSpeed).compareTo(1.00) == -1;
                //连接时间
                String titleConn = trTagNodes.get(7).getChildTagList().get(0).getAttributeByName("title");
                titleConn = titleConn.substring(0, titleConn.length() - 1);
                boolean connTime = Double.valueOf(titleConn).compareTo(1.00) == -1;
                //将匿名、连接速度&连接时间<1s的http请求进行保存
                if (isAnony && type && connSpeed && connTime) {
                    page.addProxy(ip + ":" + port);
                }
            }
        } catch (XPatherException e) {
            e.printStackTrace();
        }
    }

    public void check() {

    }
}
