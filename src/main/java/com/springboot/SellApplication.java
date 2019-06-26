package com.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
//@MapperScan(basePackages = "com.springboot.dataobject.mapper")
@EnableCaching
public class SellApplication {
    public static void main(String[] args) {
        SpringApplication.run(SellApplication.class,args);
    }
}