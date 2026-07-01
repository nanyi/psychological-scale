package com.iotsic.ps.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 订单服务启动类
 *
 * @author Ryan
 */
@SpringBootApplication(scanBasePackages = {"com.iotsic"})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.iotsic.ps.api"})
public class PsOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(PsOrderApplication.class, args);
    }
}
