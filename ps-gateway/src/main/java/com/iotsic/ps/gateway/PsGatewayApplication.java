package com.iotsic.ps.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = {"com.iotsic.ps"})
@EnableDiscoveryClient
public class PsGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(PsGatewayApplication.class, args);
    }
}
