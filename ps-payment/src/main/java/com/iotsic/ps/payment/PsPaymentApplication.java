package com.iotsic.ps.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 支付服务启动类
 *
 * @author Ryan
 */
@SpringBootApplication(scanBasePackages = {"com.iotsic"})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.iotsic.ps.api"})
public class PsPaymentApplication {

    public static void main(String[] args) {
        SpringApplication.run(PsPaymentApplication.class, args);
    }
}
