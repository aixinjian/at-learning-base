package com.learning.base.util;

import com.learning.base.bean.qiniu.QiNiuSavePath;
import com.learning.base.exception.BaseException;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.processing.OperationManager;
import com.qiniu.processing.OperationStatus;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.qiniu.util.UrlSafeBase64;
import lombok.Getter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class QiNiuUtil {

    private static Logger logger = LoggerFactory.getLogger(QiNiuUtil.class);

    private static String movieBucketName = "qianmi-jz-movies";

    private static String imageBucketName = "qianmi-jz-image";

    private static String docBucketName = "qianmi-jz-document";

    /**
     * 私有空间
     */
    private static String ownBucketName = "ehome-own";

    /**
     * 存储分段视频：m3u8+ts文件
     */
    private static String playBucketName = "ehome-play";

    /**
     * 多媒体处理队列
     */
    private static String persistentPipeline = "ehome-queue";


    private static Map<String, String> bucketUrlMap = new HashMap<String, String>() {
        {put(movieBucketName, "http://movie.qianmi.com/");}
        {put(imageBucketName, "http://jzimg.qianmi.com/");}
        {put(docBucketName, "http://doc.jz.qianmi.com/");}
        {put(ownBucketName, "http://own.bm001.com/");}
        {put(playBucketName, "http://play.bm001.com/");}
    };

    private static String ACCESS_KEY = "ROCdXqxo7Eq9kPYsasB1NTaeFPWBaALLpwwvpkWA";
    private static String SECRET_KEY = "fLlW9Uni1jNDzfGsgtlsRFagAOBoEjLnEhWM6THj";

    private static Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
    private static Configuration cfg = new Configuration(Zone.zone0());
    private static UploadManager uploadManager = new UploadManager(cfg);
    private static BucketManager bucketManager = new BucketManager(auth, cfg);
    //构建持久化数据处理对象
    private static OperationManager operationManager = new OperationManager(auth, cfg);

    /**
     * 上传图片到七牛云存储
     *
     * @param data     图片数据
     * @param fileName 名称
     * @return 返回图片连接
     **/
    public static String uploadImage(byte[] data, String fileName) throws BaseException {
        return upload(imageBucketName, data, fileName);
    }

    /**
     * 删除图片
     *
     * @return
     */
    public static boolean deleteImage(String key) throws BaseException {
        return delete(imageBucketName, key);
    }


    /**
     * 上传视频到七牛云存储
     *
     * @param data     图片数据
     * @param fileName 名称
     * @return 返回图片连接
     **/
    public static String uploadMovie(byte[] data, String fileName) throws BaseException {
        return upload(movieBucketName, data, fileName);
    }

    /**
     * 删除视频
     *
     * @return
     */
    public static boolean deleteMovie(String key) throws BaseException {
        return delete(movieBucketName, key);
    }

    /**
     * 上传文档到七牛云存储
     *
     * @param data 文件数据
     * @return 返回文件连接
     **/
    public static String uploadDocument(byte[] data, String fileName) throws BaseException {
        return upload(docBucketName, data, fileName);
    }

    /**
     * 删除文档
     *
     * @return
     */
    public static boolean deleteDocument(String key) throws BaseException {
        return delete(docBucketName, key);
    }

    /**
     * 上传私有文件到七牛云存储
     *
     * @param data 文件数据
     * @return 返回文件连接
     **/
    public static String uploadPrivate(byte[] data, String fileName) throws BaseException {
        return upload(ownBucketName, data, fileName);
    }

    /**
     * 删除文件
     *
     * @return
     */
    public static boolean deletePrivate(String key) throws BaseException {
        ParamChecker.notBlank(key, "删除的目标文件不能为空");
        return delete(ownBucketName, key);
    }

    /**
     * 获得私有空间的访问权限
     *
     * @param publicUrl 私有空间的源路径
     * @param expireSeconds 过期时间
     * @return
     */
    public static String getFinalUrl(String publicUrl, long expireSeconds) {
        return auth.privateDownloadUrl(publicUrl, expireSeconds);
    }

    /**
     * 视频切片
     *
     * @param fileUrl 需要切片的视频
     * @param segSeconds 每一段的秒数
     * @param hlsKey 加密的key
     * @param hlsKeyUrl 获取加密key的地址
     * @return
     * @throws Exception
     */
    public static QiNiuSavePath slice(String fileUrl, Integer segSeconds, String hlsKey, String hlsKeyUrl) throws BaseException {

        ParamChecker.notBlank(fileUrl, "指定切片视频不能为空");

        BucketPath bucketPath = new BucketPath(fileUrl);
        String targetFileName = "slice/" + bucketPath.getFileName().split("\\.")[0] + ".m3u8";

        //数据处理指令，支持多个指令
        String saveMp4Entry = playBucketName + ":" + targetFileName;

        StringBuilder fops = new StringBuilder(200);
        Integer segtime = segSeconds;
        if (segtime == null) {
            segtime = 60; // 默认60秒一片
        }
        fops.append("avthumb/m3u8/noDomain/1/ab/320k/r/24/segtime/" + segtime);
        if (StringUtils.isNotBlank(hlsKey) && StringUtils.isNotBlank(hlsKeyUrl)) {
            fops.append("/hlsKeyType/0/hlsKey/")
                    .append(UrlSafeBase64.encodeToString(hlsKey))
                    .append("/hlsKeyUrl/")
                    .append(UrlSafeBase64.encodeToString(hlsKeyUrl));
        }
        fops.append("|saveas/").append(UrlSafeBase64.encodeToString(saveMp4Entry));

        try {
            String persistentId = operationManager.pfop(bucketPath.getBucketName(), bucketPath.getFileName(), fops.toString(), persistentPipeline, true);
            return new QiNiuSavePath(bucketUrlMap.get(playBucketName) + targetFileName, persistentId);

        } catch (QiniuException e) {
            logger.error("七牛视频切片异常:" + e.response.toString(), e);
            throw new BaseException("七牛视频切片异常", e);
        }
    }

    /**
     * 附加logo水印
     *
     * @param fileUrl 需要打水印的文件
     * @param logoUrl 水印的源路径。
     * @param wmGravity 水印锚点参数表
     * @param wmOffsetX 视频图片水印位置的相对横向偏移量，当值为正数时则向右偏移，反之向左，有-10的固定偏移。
     * @param wmOffsetY 视频图片水印位置的相对纵向偏移量，当值为正数时则向下偏移，反之向上，有10的固定偏移。
     *
     * 水印锚点参数表：
     *   NorthWest     |     North      |     NorthEast
     *   |                |
     *   |                |
     *   --------------+----------------+--------------
     *   |                |
     *   West          |     Center     |          East
     *   |                |
     *   --------------+----------------+--------------
     *   |                |
     *   |                |
     *   SouthWest     |     South      |     SouthEast
     * @return
     * @throws Exception
     */
    public static String watermarkLogo(String fileUrl, String logoUrl, String wmGravity, Integer wmOffsetX, Integer wmOffsetY) throws BaseException {
        ParamChecker.notBlank(fileUrl, "打水印的文件不能为空");
        ParamChecker.notBlank(logoUrl, "logo不能为空");

        BucketPath bucketPath = new BucketPath(fileUrl);
        //数据处理指令，支持多个指令
        String saveMp4Entry = bucketPath.getBucketName() + ":" + bucketPath.getFileName();
        StringBuilder fops = new StringBuilder(200);
        fops.append("avthumb/mp4/wmImage/" + UrlSafeBase64.encodeToString(logoUrl));
        if (StringUtils.isNotBlank(wmGravity)) {
            fops.append("/wmGravity/").append(wmGravity);
        }
        if (wmOffsetX != null) {
            fops.append("/wmOffsetX/").append(wmOffsetX);
        }
        if (wmOffsetY != null) {
            fops.append("/wmOffsetY/").append(wmOffsetY);
        }
        fops.append("|saveas/").append(UrlSafeBase64.encodeToString(saveMp4Entry));

        //数据处理完成结果通知地址
        //String persistentNotifyUrl = "http://api.example.com/qiniu/pfop/notify";

        try {
            return operationManager.pfop(bucketPath.getBucketName(), bucketPath.getFileName(), fops.toString(), persistentPipeline, true);

        } catch (QiniuException e) {
            logger.error("打水印七牛异常:" + e.response.toString(), e);
            throw new BaseException("打水印七牛异常", e);
        }
    }

    /**
     * 视频拆剪
     *
     * @param fileUrl
     * @param beginSeconds
     * @param remainSeconds
     * @return
     * @throws Exception
     */
    public static QiNiuSavePath cut(String fileUrl, int beginSeconds, int remainSeconds) throws BaseException {
        ParamChecker.notBlank(fileUrl, "源视频不能为空");
        ParamChecker.expectTrue(beginSeconds >= 0, "起始位置不能为负");
        ParamChecker.expectTrue(remainSeconds > 0, "保留长度不能为0");
        BucketPath bucketPath = new BucketPath(fileUrl);

        String targetFile = "probate/" + bucketPath.getFileName();
        //数据处理指令，支持多个指令
        String saveMp4Entry = movieBucketName + ":" + targetFile;
        StringBuilder fops = new StringBuilder(200);
        fops.append("avthumb/mp4/ss/" + beginSeconds + "/t/" + remainSeconds);
        fops.append("|saveas/").append(UrlSafeBase64.encodeToString(saveMp4Entry));

        //数据处理完成结果通知地址
        //String persistentNotifyUrl = "http://api.example.com/qiniu/pfop/notify";

        //...其他参数参考类注释

        try {
            String persistentId = operationManager.pfop(bucketPath.getBucketName(), bucketPath.getFileName(), fops.toString(), persistentPipeline, true);
            return new QiNiuSavePath(bucketUrlMap.get(movieBucketName) + targetFile, persistentId);
        } catch (QiniuException e) {
            logger.error("裁剪七牛视频异常:" + e.response.toString(), e);
            throw new BaseException("裁剪七牛视频异常", e);
        }
    }

    /**
     * 查询队列任务执行结果
     *
     * @param persistentId
     * @return
     * @throws BaseException
     */
    public static OperationStatus queryOperationStatus(String persistentId) throws BaseException {
        ParamChecker.notBlank(persistentId, "任务编号不能为空");
        try {
            return operationManager.prefop(persistentId);
        } catch (QiniuException e) {
            logger.error("查询七牛执行任务结果异常:" + e.response.toString(), e);
            throw new BaseException("查询七牛执行任务结果异常", e);
        }
    }


    private static String upload(String bucketName, byte[] data, String fileName) throws BaseException {
        ParamChecker.notBlank(bucketName, "bucket不能为空");
        ParamChecker.nonNull(data, "文件不能为空");
        ParamChecker.notBlank(fileName, "文件名不能为空");


        String token = auth.uploadToken(bucketName);
        try {
            Response response = uploadManager.put(data, fileName, token);
            if (response.isOK()) {
                StringMap map = response.jsonToMap();
                return bucketUrlMap.get(bucketName) + map.get("key").toString();
            }
            logger.error("上传七牛失败，结果：{}", response.getInfo());
            throw new BaseException("上传七牛失败");
        } catch (QiniuException e) {
            logger.error("上传七牛异常:" + e.response.toString(), e);
            throw new BaseException("上传七牛异常", e);
        }
    }


    private static boolean delete(String bucketName, String key) throws BaseException {
        try {
            Response response = bucketManager.delete(bucketName, key);
            return response.isOK();
        } catch (QiniuException e) {
            logger.error("删除七牛异常:" + e.response.toString(), e);
            throw new BaseException("删除七牛异常", e);
        }
    }


    public static class BucketPath {
        @Getter
        private String fileName;

        @Getter
        private String bucketName;

        private BucketPath(String fileUrl) {
            bucketUrlMap.forEach((name, url) -> {
                if (fileUrl.startsWith(url)) {
                    this.bucketName = name;
                    this.fileName = fileUrl.substring(url.length());
                }
            });
            ParamChecker.notBlank(fileName, "非本七牛账号的源，无法处理");
            ParamChecker.notBlank(bucketName, "非本七牛账号的源，无法处理");
        }
    }


}
