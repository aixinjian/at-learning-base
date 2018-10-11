package com.learning.base.util;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class AliYunUploadUtil {

    private static String ALIYUN_BASE_PATH="cloud:/ehome/dev/";

    private static String ALIYUN_URL="https://img.1000.com";

    /**
     * 上传图片
     *
     * @param input
     * @param fileName
     * @param userCode
     * @return
     * @throws IOException
     * @throws FileNotFoundException
     */
    public static String uploadFileToServer(InputStream input, String fileName, String userCode) throws IOException, FileNotFoundException {
        return uploadFileToServer(input, fileName, userCode, ALIYUN_BASE_PATH);
    }



    /**
     * 根据url上传
     *
     * @param url
     * @param fileName
     * @param userCode
     * @return
     * @throws IOException
     * @throws FileNotFoundException
     */
    public static String uploadFileToServer(String url, String fileName, String userCode) throws IOException, FileNotFoundException {
        return uploadFileToServer(new URL(url).openStream(), fileName, userCode);
    }

    /**
     * 上传图片
     * @param input
     * @param fileName
     * @param userCode
     * @param basePath
     * @return
     * @throws IOException
     * @throws FileNotFoundException
     */
    public static String uploadFileToServer(InputStream input, String fileName, String userCode, String basePath) throws IOException, FileNotFoundException {
        Path cloudPath = Paths.get(URI.create(basePath + userCode + "/" + fileName));
        Files.copy(input, cloudPath, StandardCopyOption.REPLACE_EXISTING);
        return ALIYUN_URL + cloudPath.toUri().toString();
    }



}
