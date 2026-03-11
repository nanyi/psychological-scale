package com.iotsic.ps.thirdparty;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.iotsic.ps.api"})
@MapperScan("com.iotsic.ps.thirdparty.mapper")
public class PsThirdpartyApplication {

    public static void main(String[] args) {
        SpringApplication.run(PsThirdpartyApplication.class, args);
    }
}
