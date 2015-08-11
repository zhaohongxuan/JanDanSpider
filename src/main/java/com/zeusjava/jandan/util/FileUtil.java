package com.zeusjava.jandan.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by LittleXuan on 2015/8/11.
 * 文件操作工具类
 */
public class FileUtil {
    private static Logger logger = Logger.getLogger(FileUtil.class);
    /**
     * 下载文件
     *
     * @param url
     *            文件http地址
     * @param filePath
     *            目标文件路径
     * @param fileName
     *            目标文件
     * @param picType
     *            文件类型
     * @throws java.io.IOException
     */
    public static synchronized void downloadFile(String url,String filePath, String fileName,String picType)
            throws Exception {
        logger.info("----------------------下载文件开始---------------------");
        CloseableHttpClient httpclient = HttpClients.createDefault();
        if (url == null || "".equals(url)) {
            return;
        }
        //目标目录
        File desPathFile = new File(filePath);
        if (!desPathFile.exists()) {
            desPathFile.mkdirs();
        }
        //得到文件绝对路径
        String fullPath =filePath +File.separator+fileName+"."+picType;
        logger.info("文件路径："+filePath);
        logger.info("文件名："+fileName);
        logger.info("源文件url："+url);
        //从元网址下载图片
        HttpGet httpget = new HttpGet(url);
        HttpResponse response = httpclient.execute(httpget);
        HttpEntity entity = response.getEntity();
        InputStream in = entity.getContent();
        //设置下载地址
        File file = new File(fullPath);

        try {
            FileOutputStream fout = new FileOutputStream(file);
            int l = -1;
            byte[] tmp = new byte[1024];
            while ((l = in.read(tmp)) != -1) {
                fout.write(tmp,0,l);
            }
            fout.flush();
            fout.close();
        } finally {

            in.close();
        }
        logger.info("----------------------下载文件结束---------------------");
    }
}
