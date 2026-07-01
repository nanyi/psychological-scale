package com.iotsic.ps.analysis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 分析服务启动类
 *
 * @author Ryan
 */
@SpringBootApplication(scanBasePackages = {"com.iotsic"})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.iotsic.ps.api"})
public class PsAnalysisApplication {

    public static void main(String[] args) {
        SpringApplication.run(PsAnalysisApplication.class, args);
    }
}
