package com.iotsic.ps.report;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"com.iotsic.ps"})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.iotsic.ps.api"})
public class PsReportApplication {

    public static void main(String[] args) {
        SpringApplication.run(PsReportApplication.class, args);
    }
}
