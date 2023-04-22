package com.chen.educms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * @author Chenchenx
 * @version v1.0
 * @date 2021/12/1008:23
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan({"com.chen"})
@MapperScan("com.chen.educms.mapper")
public class CmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class, args);
    }
}