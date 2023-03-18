package com.itlyc.aliyun.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("aliyun.oss")
public class OssProperties {
    /*
     * 客户端ID
     */
    private String accessKeyId;
    /**
     * 客户端秘钥
     */
    private String accessKeySecret;
    /**
     * 存储空间名称
     */
    private String bucket;
    /**
     * 存储空间所属域名
     */
    private String endpoint;
    /**
     * 完整存储空间url地址
     */
    private String url;
}
