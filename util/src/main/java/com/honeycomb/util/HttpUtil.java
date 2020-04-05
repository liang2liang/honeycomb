package com.honeycomb.util;

import lombok.SneakyThrows;

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
}
