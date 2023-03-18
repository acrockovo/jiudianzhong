package com.itlyc.aliyun.template;

import com.itlyc.aliyun.properties.OssProperties;
import com.aliyun.oss.OSS;
import com.aliyun.oss.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import sun.misc.BASE64Decoder;

import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class AliyunOssTemplate {

    @Autowired
    private OssProperties ossProperties;

    @Autowired
    private OSS oss;

    /**
     * 创建空间
     * @param bucketName 存储空间
     * @return String
     */
    public String createBucketName(String bucketName) {
        //存储空间
        final String bucketNames = bucketName;
        if (!oss.doesBucketExist(bucketName)) {
            //创建存储空间
            Bucket bucket = oss.createBucket(bucketName);
            log.info("创建存储空间成功");
            return bucket.getName();
        }
        return bucketNames;
    }

    /**
     * 创建文件夹
     * @param bucketName 存储空间
     * @param folder
     * @return  文件夹名
     */
    public String createFolder(String bucketName, String folder) {
        //文件夹名
        final String keySuffixWithSlash = folder;
        //判断文件夹是否存在，不存在则创建
        if (!oss.doesObjectExist(bucketName, keySuffixWithSlash)) {
            //创建文件夹
            oss.putObject(bucketName, keySuffixWithSlash, new ByteArrayInputStream(new byte[0]));
            log.info("创建文件夹成功");
            //得到文件夹名
            OSSObject object = oss.getObject(bucketName, keySuffixWithSlash);
            String fileDir = object.getKey();
            return fileDir;
        }
        return keySuffixWithSlash;
    }

    /**
     * 上传文件 - base64
     *
     * @param objectName
     * @param base64
     */
    public String upload4base64(String objectName, String base64) {
        try {
            log.info("正在执行：上传文件 - base64，objectName=" + objectName);
            return upload4bytes(objectName, new BASE64Decoder().decodeBuffer(base64));
        } catch (IOException e) {
            log.error("异常：上传文件 - base64，objectName=" + objectName, e);
            throw new RuntimeException("[starter][oss]文件上传异常:" + getStackTraceAsString(e));
        }

    }

    /**
     * 上传文件 - byte[]
     *
     * @param objectName
     * @param bytes
     */
    public String upload4bytes(String objectName, byte[] bytes) {
        log.info("正在执行：上传文件 - byte[]，objectName=" + objectName);
        PutObjectRequest putObjectRequest = new PutObjectRequest(ossProperties.getBucket(), objectName, new ByteArrayInputStream(bytes));
        oss.putObject(putObjectRequest);

        // 生成URL
        return getFileUrl(objectName);
    }

    /**
     * 文件上传
     * @param fileName 上传服务器文件名称 该名称可以手动指定
     * @param inputStream 文件输入流
     * @return
     */
    public String uploadImage(String fileName, InputStream inputStream) {

        // 上传文件流。
        // <yourObjectName>表示上传文件到OSS时需要指定包含文件后缀在内的完整路径，例如 images/2020/11/11/asdf.jpg。
        String objectName = "images/" + new SimpleDateFormat("yyyy/MM/dd").format(new Date())
                + "/" + System.currentTimeMillis() + fileName.substring(fileName.lastIndexOf("."));

        // meta设置请求头,解决访问图片地址直接下载
        ObjectMetadata meta = new ObjectMetadata();
        oss.putObject(ossProperties.getBucket(), objectName, inputStream, meta);
        //返回图片在线地址
        return ossProperties.getUrl() + "/" + objectName;
    }



    /**
     * 上传文件 - 文件流
     *
     * @param objectName
     * @param inputStream
     * @return
     */
    public String upload4inputStream(String objectName, InputStream inputStream) {
        log.info("正在执行：上传文件 - 文件流，objectName=" + objectName);
        PutObjectRequest putObjectRequest = new PutObjectRequest(ossProperties.getBucket(), objectName, inputStream);
        oss.putObject(putObjectRequest);

        // 生成URL
        return getFileUrl(objectName);
    }

    /**
     * 上传文件 - ⽹络文件
     *
     * @param objectName
     * @param url
     * @return
     */
    public String upload4url(String objectName, String url) {
        log.info("正在执行：上传文件 - ⽹络文件，objectName=" + objectName + "，url=" + url);
        // 上传⽹络流
        InputStream inputStream = null;
        try {
            inputStream = new URL(url).openStream();
        } catch (IOException e) {
            log.error("异常：上传文件 - ⽹络文件，objectName=" + objectName + "，url=" + url + objectName, e);
            throw new RuntimeException("[starter][oss]文件上传异常:" + getStackTraceAsString(e));
        }
        oss.putObject(ossProperties.getBucket(), objectName, inputStream);

        // 生成URL
        return getFileUrl(objectName);
    }


    /**
     * 文件下载 - BufferedReader
     *
     * @param objectName
     * @return
     * @throws IOException
     */
    public BufferedReader download(String objectName) {
        log.info("正在执行：文件下载 - BufferedReader，objectName=" + objectName);
        try {
            OSSObject ossObject = oss.getObject(ossProperties.getBucket(), objectName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(ossObject.getObjectContent()));
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
            }
            reader.close();
            return reader;
        } catch (IOException e) {
            log.error("异常：文件下载 - BufferedReader，objectName=" + objectName, e);
            throw new RuntimeException("[starter][oss]文件下载异常:" + getStackTraceAsString(e));
        }
    }

    /**
     * 文件下载 - 本地路径
     *
     * @param objectName
     * @param path
     */
    public void downLoad(String objectName, String path) {
        log.info("正在执行：文件下载 - 本地路径，objectName=" + objectName + "，path=" + path);
        File file = new File(path);
        oss.getObject(new GetObjectRequest(ossProperties.getBucket(), objectName), file);
    }

    /**
     * 文件是否存在
     *
     * @param objectName
     * @return
     */
    public boolean exist(String objectName) {
        log.info("正在执行：文件是否存在，objectName=" + objectName);
        return oss.doesObjectExist(ossProperties.getBucket(), objectName);
    }

    /**
     * 文件删除
     *
     * @param objectName
     */
    public void delete(String objectName) {
        log.info("正在执行：文件删除，objectName=" + objectName);
        oss.deleteObject(ossProperties.getBucket(), objectName);
    }

    /**
     * 获取obj访问url
     *
     * @param objectName
     * @return
     */
    public String getFileUrl(String objectName) {

        // build file url
        StringBuilder url = new StringBuilder();
        url.append(ossProperties.getUrl())
                .append("/")
                .append(objectName);

        // 生成URL
        return url.toString();

    }


    /**
     * 获取具体的堆栈信息
     *
     * @param e
     * @return
     */
    private String getStackTraceAsString(Exception e) {
        try {
            // StringWriter将包含堆栈信息
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            // 获取堆栈信息
            e.printStackTrace(printWriter);
            // 转换成String，并返回该String
            StringBuffer error = stringWriter.getBuffer();
            return error.toString();
        } catch (Exception e2) {
            return "[starter][oss]获取堆栈信息异常";
        }
    }
}
