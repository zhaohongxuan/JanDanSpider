package com.zeusjava.jandan.processor;

import com.zeusjava.jandan.util.FileUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.Request;

import static us.codecraft.webmagic.selector.Selectors.xpath;

import java.io.File;
import java.util.List;

/**
 * Created by LittleXuan on 2015/8/10.
 * 处理煎蛋网无聊图Processor
 */
public class PicProcessor implements PageProcessor {
    //得到网站配置
    private Site site = Site.me().setDomain("jandan.net").addHeader("Accept",
            "application/x-ms-application, image/jpeg, application/xaml+xml, image/gif, image/pjpeg, application/x-ms-xbap, */*")
            .addHeader("Referer", "http://jandan.net/pic").setSleepTime(10000).setUserAgent("zhaohongxuan")
            .addStartUrl("http://jandan.net/pic");
    private static Logger logger = Logger.getLogger(PicProcessor.class);
    private String downloadDir="D://download/";

    @Override
    public void process(Page page) {
        System.out.println("================================");
        //定义抽取信息，并保存信息
        processPicture(page);
        //得到下一页链接
        String comments=page.getHtml().xpath("//a[@class='previous-comment-page']").toString();
        logger.info("comments:"+comments);
        String link = xpath("a/@href").select(comments);
        logger.info("link:" + link);
        Request request = new Request(link);
        page.addTargetRequest(request);

        System.out.println("================================");

    }

    //处理图片类
    private void processPicture(Page page) {
    //得到所有Gif的li标签

        List<String> gifLists = page.getHtml().xpath("//ol[@class='commentlist']/li[@id]").all();
        for (String gif:gifLists){
            //得到标题
            String title=xpath("//div[@class='author']/strong").selectElement(gif).attr("title");
            logger.info("title:"+title);
            //得到上传者
            String author=xpath("//div[@class='author']/strong").selectElement(gif).text();
            //将标题中的防伪码转换为：上传者名称
            title=title.replace("防伪码",author);
            //图片url
            //如果有org_src属性，则是gif图片
            String url=xpath("//div[@class='text']/p/img").selectElement(gif).attr("src");
            String gifUrl=xpath("//div[@class='text']/p/img").selectElement(gif).attr("org_src");
            if(StringUtils.isNotEmpty(gifUrl)){
                logger.info("Gif图片...替换新链接...");
                url=gifUrl;//如果是gif则用大图链接替换缩略图链接
            }
            logger.info("图片url:" + url);
            //保存图片到本地
            String filePath=downloadDir+ File.separator+author;
            String picType=url.substring(url.length()-3);
            try {
                FileUtil.downloadFile(url,filePath,title, picType);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public Site getSite() {
        return site;

    }


}
