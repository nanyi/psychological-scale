package com.iotsic.ps.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.iotsic.ps.api"})
@MapperScan("com.iotsic.ps.order.mapper")
public class PsOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(PsOrderApplication.class, args);
    }
}
