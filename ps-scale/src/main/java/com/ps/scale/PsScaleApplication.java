package com.ps.scale;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.ps.api"})
@MapperScan("com.ps.scale.mapper")
public class PsScaleApplication {

    public static void main(String[] args) {
        SpringApplication.run(PsScaleApplication.class, args);
    }
}
