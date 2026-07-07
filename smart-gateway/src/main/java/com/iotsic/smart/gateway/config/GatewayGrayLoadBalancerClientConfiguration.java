package com.iotsic.smart.gateway.config;

import com.iotsic.smart.gateway.filter.GrayReactiveLoadBalancerClientFilter;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.gateway.config.GatewayLoadBalancerProperties;
import org.springframework.cloud.gateway.config.GatewayReactiveLoadBalancerClientAutoConfiguration;
import org.springframework.cloud.gateway.config.conditional.ConditionalOnEnabledGlobalFilter;
import org.springframework.cloud.gateway.filter.ReactiveLoadBalancerClientFilter;
import org.springframework.cloud.loadbalancer.config.LoadBalancerAutoConfiguration;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.DispatcherHandler;

/**
 * 网关多环境版本负载均衡 配置类
 *
 * @author Ryan
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({LoadBalancerClient.class, LoadBalancerAutoConfiguration.class, DispatcherHandler.class})
@AutoConfigureAfter(LoadBalancerAutoConfiguration.class)
@AutoConfigureBefore({GatewayReactiveLoadBalancerClientAutoConfiguration.class})
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
@EnableConfigurationProperties({GatewayLoadBalancerProperties.class})
public class GatewayGrayLoadBalancerClientConfiguration {

    @Bean
    @ConditionalOnBean(LoadBalancerClientFactory.class)
    @ConditionalOnMissingBean(ReactiveLoadBalancerClientFilter.class)
    @ConditionalOnEnabledGlobalFilter
    @ConditionalOnProperty(value = "smart.gray.enabled", havingValue = "true", matchIfMissing = true)
    public ReactiveLoadBalancerClientFilter gatewayLoadBalancerClientFilter(LoadBalancerClientFactory loadBalancerClientFactory, GatewayLoadBalancerProperties properties) {
        return new GrayReactiveLoadBalancerClientFilter(loadBalancerClientFactory, properties);
    }
}
