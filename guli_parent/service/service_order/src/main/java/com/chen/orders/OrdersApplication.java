package com.chen.orders;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author CSY
 * @version v1.0
 * @date 2022/2/4 14:26
 */
@SpringBootApplication
//远程调用接口注册
@EnableDiscoveryClient
//远程调用端
@EnableFeignClients

@ComponentScan("com.chen")
@MapperScan("com.chen.orders.mapper")
public class OrdersApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrdersApplication.class,args);
    }
}
