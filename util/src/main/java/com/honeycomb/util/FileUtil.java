package com.honeycomb.util;

import lombok.SneakyThrows;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author maoliang
 * @version 1.0.0
 */
public class FileUtil {

    @SneakyThrows
    public static void copy(String sourcePath, String targetPath) {
        try (FileOutputStream fos = new FileOutputStream(createFile(targetPath)); FileInputStream fis = new FileInputStream(sourcePath)) {
            copy(fos, fis);
        }
    }

    @SneakyThrows
    public static void copy(List<String> sourcePaths, String targetPath) {
        try (FileOutputStream fos = new FileOutputStream(createFile(targetPath))) {
            for (String path : sourcePaths) {
                try(FileInputStream fis = new FileInputStream(path)){
                    copy(fos, fis);
                }
            }
        }
    }

    @SneakyThrows
    private static void copy(FileOutputStream fos, FileInputStream fis) {
        byte[] bytes = new byte[10240];
        while (fis.read(bytes) != -1) {
            fos.write(bytes);
        }
    }

    public static File createFile(String path) throws IOException {
        File file = new File(path);
        createDir(path);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    private static void createDir(String path){
        int dir = path.lastIndexOf(File.separator);
        String dirPath = path.substring(0, dir);
        File dirFile = new File(dirPath);
        if(!dirFile.exists()){
            dirFile.mkdirs();
        }
    }

    public static void main(String[] args) throws IOException {
//        String sourcePath = "/Users/zhoufeng/Downloads/video/小Q/0.ts";
//        List<String> list = List.of("/Users/zhoufeng/Downloads/video/小Q/0.ts", "/Users/zhoufeng/Downloads/video/小Q/1.ts");
//        String targetPath = "/Users/zhoufeng/Downloads/video/小Q/honeycomb.ts";
//        copy(list, targetPath);
//        createFile("/Users/zhoufeng/Downloads/books/Docker+Kubernetes(k8s)微服务容器化实战/第1章%20初识微服务/1-1%20微服务-导学.mp4");

    }
}
