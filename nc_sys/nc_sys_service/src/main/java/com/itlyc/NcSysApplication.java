package com.itlyc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@MapperScan("com.itlyc.mapper")
@EnableFeignClients
public class NcSysApplication {
    public static void main(String[] args) {
        SpringApplication.run(NcSysApplication.class, args);
    }
}
