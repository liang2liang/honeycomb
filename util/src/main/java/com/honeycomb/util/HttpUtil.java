package com.honeycomb.util;

import lombok.SneakyThrows;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author maoliang
 * @version 1.0.0
 */
public class HttpUtil {

    private static final HttpClient client = HttpClient.newHttpClient();

    @SneakyThrows
    public static String getString(String url){
        return getStringResponse(url).body();
    }

    @SneakyThrows
    public static byte[] getBytes(String url){
        return getByteResponse(url).body();
    }

    @SneakyThrows
    public static HttpResponse<byte[]> getByteResponse(String url){
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        return client.send(httpRequest, HttpResponse.BodyHandlers.ofByteArray());
    }

    @SneakyThrows
    public static HttpResponse<String> getStringResponse(String url){
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        return client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
    }

    @SneakyThrows
    public static Path getPath(String url, String filePath){
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        return client.send(httpRequest, HttpResponse.BodyHandlers.ofFile(Paths.get(filePath))).body();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        HttpRequest build = HttpRequest.newBuilder()
                .uri(URI.create("https://account.geekbang.org/account/ticket/login"))
                .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.163 Safari/537.36")
                .header("Content-Type", "application/json")
                .header("Origin", "https://account.geekbang.org")
                .header("Cookie", "gksskpitn=c502e55a-0426-4300-97de-bdd206c2a808; SERVERID=1fa1f330efedec1559b3abbcb6e30f50|1587092137|1587092137")
                .POST(HttpRequest.BodyPublishers.ofString("{\"appid\":1,\"captcha\":\"\",\"cellphone\":\"188812345678\",\"country\":86,\"password\":\"188812\",\"platform\":3,\"source\":\"\"}"))
                .build();

        HttpResponse<String> send = client.send(build, HttpResponse.BodyHandlers.ofString());
        System.out.println(send.body());
    }
}
