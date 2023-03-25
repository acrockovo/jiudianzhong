package com.itlyc;

import com.itlyc.aliyun.auto.AliyunOssAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(exclude = {AliyunOssAutoConfiguration.class})
@EnableFeignClients
@EnableDiscoveryClient
@MapperScan("com.itlyc.doc.mapper")
public class NcDocumentApplication {
    public static void main(String[] args) {
        SpringApplication.run(NcDocumentApplication.class, args);
    }
}
