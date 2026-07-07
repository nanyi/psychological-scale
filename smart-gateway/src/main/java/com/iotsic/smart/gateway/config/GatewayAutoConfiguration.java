package com.iotsic.smart.gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

/**
 * 网关自动配置类
 *
 * @author Ryan
 * @since 2026-03-12
 */
@Configuration
@Slf4j
public class GatewayAutoConfiguration {

    @Bean(value = "loadBalancedWebClientBuilder")
    @LoadBalanced
    public WebClient.Builder loadBalancedWebClientBuilder() {
        return WebClient.builder().filter(exchangeFilterFunction());
    }

    /**
     * 异常响应
     */
    public static ExchangeFilterFunction exchangeFilterFunction() {
        return ExchangeFilterFunction.ofResponseProcessor(GatewayAutoConfiguration::exceptionHandler);
    }

    private static Mono<ClientResponse> exceptionHandler(ClientResponse response) {
        if (response.statusCode().is4xxClientError() || response.statusCode().is5xxServerError()) {
            return response.bodyToMono(String.class)
                    .flatMap(body -> Mono.error(new ResponseStatusException(response.statusCode(), body)));
        } else {
            return Mono.just(response);
        }
    }

    /**
     * 网关CORS跨域配置
     */
    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsWebFilter(source);
    }
}
