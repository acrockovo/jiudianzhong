package com.itlyc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.itlyc.mapper")
public class NcSysApplication {
    public static void main(String[] args) {
        SpringApplication.run(NcSysApplication.class, args);
    }
}
