package com.honeycomb.spider.component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author maoliang
 * @version 1.0.0
 */
public class PhotoSpider {

    @SneakyThrows
    public static void main(String[] args) {
        System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
        PhotoSpider photoSpider = new PhotoSpider();
        for (int i = 11; i < 20; i++){
            photoSpider.pageDown(i);
        }
    }

    @SneakyThrows
    public void download(String id){
        String downloadUrl = "https://unsplash.com/photos/{id}/download?force=true".replace("{id}", id);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(downloadUrl))
                .GET()
                .build();
        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 302) {
            String newuri = response.headers().firstValue("location").get();

            HttpRequest build = HttpRequest.newBuilder()
                    .uri(URI.create(newuri))
                    .GET()
                    .build();
            HttpResponse<byte[]> send = client.send(build, HttpResponse.BodyHandlers.ofByteArray());
            File file = new File("/Users/zhoufeng/Downloads/photo/" + id + ".jpg");
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(send.body());
            }
        }
    }

    public void pageDown(int page) throws ExecutionException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://unsplash.com/napi/photos?page={page}".replace("{page}", String.valueOf(page))))
                .GET()
                .build();
        CompletableFuture<Void> completableFuture = client.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(JSON::parseArray)
                .thenAccept(jsonArray -> jsonArray.stream().map(obj -> (JSONObject) obj).map(jsonObject -> jsonObject.getString("id")).forEach(this::download));
        completableFuture.get();
    }
}
