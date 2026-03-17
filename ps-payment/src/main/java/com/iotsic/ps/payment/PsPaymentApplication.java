package com.iotsic.ps.payment;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"com.iotsic.ps"})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.iotsic.ps.api"})
public class PsPaymentApplication {

    public static void main(String[] args) {
        SpringApplication.run(PsPaymentApplication.class, args);
    }
}
