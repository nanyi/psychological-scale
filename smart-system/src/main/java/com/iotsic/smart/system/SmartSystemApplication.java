package com.iotsic.smart.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 用户服务启动类
 *
 * @author Ryan
 */
@SpringBootApplication(scanBasePackages = {"com.iotsic"})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.iotsic.smart.api"})
public class SmartSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartSystemApplication.class, args);
    }
}
