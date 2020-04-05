package com.honeycomb.spider.component;

import com.honeycomb.util.FileUtil;
import com.honeycomb.util.HttpUtil;
import com.honeycomb.util.StringUtil;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author maoliang
 * @version 1.0.0
 */
public class SpiderStudyFile {

    private static final String HOST = "http://172.30.157.153:2333";

    private static final String URL = HOST + "/E%3A/Google 云端硬盘/慕课网2019(By_lemeaco-GDUp_kuk)";

    public static void main(String[] args) {
//        System.out.println(new SpiderStudyFile().encoderURl(URL.concat("/毛亮")));
        new SpiderStudyFile().start(URL);
    }

    public void start(String uri){
        uris(uri).forEach(this::download);
    }

    public void download(String url){
        String fileName = url.substring(url.indexOf("慕课网2019(By_lemeaco-GDUp_kuk)") + 29);
        if(exists(fileName)){
            System.out.println(fileName + "已存在");
            return;
        }

        HttpResponse<byte[]> byteResponse = HttpUtil.getByteResponse(encoderURl(url));
        if(isFileFolder(byteResponse)){
            start(url);
        }else{
            System.out.println(fileName + " start download");
            download(byteResponse.body(), fileName);
            System.out.println(fileName + " download success!!!");
            byteResponse = null;
        }
    }

    /**
     * 获取所有url
     * @param url
     * @return
     */
    public List<String> uris(String url){
        String html = HttpUtil.getString(encoderURl(url));
        Document parse = Jsoup.parse(html);
        Elements elements = parse.select("span.nobr > nobr > a");
        return elements.stream().map(element -> element.attr("href")).map(uri -> HOST + uri).collect(Collectors.toList());
    }

    public boolean isFileFolder(HttpResponse<byte[]> byteResponse){
        String contentType = byteResponse.headers().firstValue("Content-Type").orElse(StringUtil.EMPTY);
        return contentType.contains("text/html");
    }

    @SneakyThrows
    public void download(byte[] bytes, String fileName) {
        File file = FileUtil.createFile("/Volumes/学习资料/慕课网2019(By_lemeaco-GDUp_kuk)/" + fileName);
        try(FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(bytes);
        }
    }

    public boolean exists(String filePath){
        filePath = "/Volumes/学习资料/慕课网2019(By_lemeaco-GDUp_kuk)/" + filePath;
        if(filePath.contains("大咖说")){
            return true;
        }

        if(filePath.contains("27-Cocos2d-x游戏开发")){
            return true;
        }

        if(filePath.indexOf(".") != -1) {
            File file = new File(filePath);
            return file.exists();
        }else{
            return false;
        }
    }

    public String encoderURl(String url){
        int i = url.indexOf("http://172.30.157.153:2333/E%3A");
        String[] split = url.substring(i + 32).split("/");
        List<String> collect = Arrays.stream(split).map(str -> str.replace("%20", " ")).map(URLEncoder::encode).map(str -> str = str.replaceAll("\\+", "%20")).collect(Collectors.toList());
        String baseUrl = url.substring(0, i + 31);
        for (String str : collect){
            baseUrl = baseUrl.concat("/").concat(str);
        }
        return baseUrl;
    }
}
