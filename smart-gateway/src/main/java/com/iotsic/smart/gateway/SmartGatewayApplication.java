package com.iotsic.smart.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 网关服务启动类
 *
 * @author Ryan
 */
@SpringBootApplication(scanBasePackages = {"com.iotsic"})
@EnableDiscoveryClient
public class SmartGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartGatewayApplication.class, args);
    }
}
