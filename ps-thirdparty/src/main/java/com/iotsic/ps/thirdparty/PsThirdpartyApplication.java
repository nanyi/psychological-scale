package com.iotsic.ps.thirdparty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"com.iotsic.ps"})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.iotsic.ps.api"})
public class PsThirdpartyApplication {

    public static void main(String[] args) {
        SpringApplication.run(PsThirdpartyApplication.class, args);
    }
}
