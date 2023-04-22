package com.chen.staservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * @author CSY
 * @version v1.0
 * @date 2022/2/5 13:15
 */
@SpringBootApplication
@MapperScan("com.chen.staservice.mapper")
@ComponentScan("com.chen")
@EnableFeignClients
@EnableDiscoveryClient

//定时任务注解
@EnableScheduling
public class StaApplication {
    public static void main(String[] args) {
        SpringApplication.run(StaApplication.class,args);
    }
}



