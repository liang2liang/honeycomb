package com.honeycomb.spider.controller;

import com.honeycomb.spider.entity.TSDownloader;
import com.honeycomb.spider.entity.TsVideo;
import com.honeycomb.util.HttpUtil;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author maoliang
 * @version 1.0.0
 */
@Controller
public class VideoController {

    @RequestMapping(value = "/obtain/video")
    @ResponseBody
    public String obtainVideoUrl(@PathParam("url") String url) {
        return new TSDownloader("").analysisUrl(url).getUrl();
    }

    @SneakyThrows
    @RequestMapping(value = "/download/video")
    public HttpServletResponse downloadVideo(HttpServletResponse response, @PathParam("url") String url) {
        TSDownloader tsDownloader = new TSDownloader("");
        TsVideo video = tsDownloader.analysisUrl(url);
        List<String> tsUrlArray = new ArrayList<>();
        tsDownloader.analysisM3u8Url(video.getUrl(), tsUrlArray);
        response.addHeader("Content-Disposition", "attachment;filename=" + new String(video.getTitle().concat(".ts").getBytes(StandardCharsets.UTF_8) , StandardCharsets.ISO_8859_1));
        response.setContentType("application/octet-stream;charset=utf-8");
        try(OutputStream toClient = new BufferedOutputStream(response.getOutputStream())){
            for(String tsUrl : tsUrlArray){
                byte[] bytes = HttpUtil.getBytes(tsUrl);
                toClient.write(bytes);
            }
            toClient.flush();
        }
        return response;
    }
}
