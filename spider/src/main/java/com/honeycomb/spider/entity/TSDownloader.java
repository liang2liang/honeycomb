package com.honeycomb.spider.entity;

import com.alibaba.fastjson.JSON;
import com.honeycomb.util.ExecutorServiceUtil;
import com.honeycomb.util.FileUtil;
import com.honeycomb.util.HttpUtil;
import com.honeycomb.util.RegularUtil;
import lombok.SneakyThrows;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 处理.ts视频
 * @author maoliang
 * @version 1.0.0
 */
public class TSDownloader {

    private static final String ANALYSIS_URL = "http://www.41478.net/api.php?url={url}&cb=jQuery18208887740146235492_1572513371297&_=1572513371947";

    private String filePath;

//    public static void main(String[] args) {
//        TSDownloader tsDownloader = new TSDownloader("/Users/zhoufeng/Downloads/video");
//        TsVideo video = tsDownloader.analysisUrl("https://www.iqiyi.com/v_19rsfx7jg0.html?vfrm=pcw_home&vfrmblk=L&vfrmrst=711219_home_dianying_float_pic_play3");
//        List<String> tsUrl = new ArrayList<>();
//        tsDownloader.analysisM3u8Url(video.getUrl(), tsUrl);
//        long start = System.currentTimeMillis();
//        tsDownloader.composeFile(tsUrl, video.getTitle());
//        System.out.println("总耗时：" + (System.currentTimeMillis() - start));
//    }

    public TSDownloader(String filePath) {
        this.filePath = filePath;
    }

    /**
     * 分析视频地址
     *
     * @param url 视频链接
     * @return 倒链地址
     */
    public TsVideo analysisUrl(String url) {
        if (StringUtils.isEmpty(url)) {
            throw new IllegalArgumentException("url must be not null!!!");
        }
        String requestUrl = ANALYSIS_URL.replace("{url}", url);
        String body = HttpUtil.getString(requestUrl);
        String videoJson = body.substring(body.indexOf("(") + 1, body.length() - 2);
        return JSON.parseObject(videoJson, TsVideo.class);
    }

    /**
     * 获取所有ts地址
     *
     * @param videoUrl
     * @param tsUrlList
     */
    public void analysisM3u8Url(String videoUrl, List<String> tsUrlList) {
        String[] urlArray = HttpUtil.getString(videoUrl).split("\n");
        for (String url : urlArray) {
            if (RegularUtil.isM3u8(url)) {
                analysisM3u8Url(processUrl(url, videoUrl), tsUrlList);
            } else if (RegularUtil.isTs(url)) {
                tsUrlList.add(processUrl(url, videoUrl));
            }
        }
    }

    /**
     * url处理
     *
     * @param url
     * @param upperLevelUrl
     * @return
     */
    public String processUrl(String url, String upperLevelUrl) {
        if (url.startsWith("http")) {
            return url;
        }
        return upperLevelUrl.substring(0, upperLevelUrl.lastIndexOf("/") + 1).concat(url);
    }

    @SneakyThrows
    public void composeFile(List<String> tsUrl, String videoName) {
        String fileRoot = this.filePath + File.separator + videoName + "/";
        new File(fileRoot).mkdirs();
        CountDownLatch countDownLatch = new CountDownLatch(tsUrl.size());
        for (int i = 0, len = tsUrl.size(); i < len; i++) {
            ExecutorServiceUtil.execute(new Task(fileRoot + i + ".ts", tsUrl.get(i), countDownLatch));
        }
        countDownLatch.await();
        List<String> tsPath = new ArrayList<>(tsUrl.size());
        for (int i = 0, len = tsUrl.size(); i < len; i++) {
            tsPath.add(fileRoot + i + ".ts");
        }
        FileUtil.copy(tsPath, fileRoot + videoName + ".ts");
    }

    private static class Task implements Runnable {

        private String path;

        private String url;

        private CountDownLatch countDownLatch;

        public Task(String path, String url, CountDownLatch countDownLatch) throws IOException {
            this.path = path;
            this.url = url;
            this.countDownLatch = countDownLatch;
            new File(path).createNewFile();
        }

        @Override
        @SneakyThrows
        public void run() {
            long start = System.currentTimeMillis();
            System.out.println(path + " download start!!!");
            Files.write(Path.of(this.path), HttpUtil.getBytes(url), StandardOpenOption.TRUNCATE_EXISTING);
            System.out.println(path + " download success!!! 总耗时: " + (System.currentTimeMillis() - start));
            countDownLatch.countDown();
        }
    }
}
