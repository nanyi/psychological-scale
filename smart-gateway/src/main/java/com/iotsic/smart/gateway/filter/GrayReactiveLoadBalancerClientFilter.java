package com.iotsic.smart.gateway.filter;

import com.iotsic.smart.framework.loadbalance.GrayRoundRobinLoadBalancer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.CompletionContext;
import org.springframework.cloud.client.loadbalancer.DefaultRequest;
import org.springframework.cloud.client.loadbalancer.LoadBalancerLifecycle;
import org.springframework.cloud.client.loadbalancer.LoadBalancerLifecycleValidator;
import org.springframework.cloud.client.loadbalancer.LoadBalancerProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalancerUriTools;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.RequestData;
import org.springframework.cloud.client.loadbalancer.RequestDataContext;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.client.loadbalancer.ResponseData;
import org.springframework.cloud.gateway.config.GatewayLoadBalancerProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.ReactiveLoadBalancerClientFilter;
import org.springframework.cloud.gateway.support.DelegatingServiceInstance;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Map;
import java.util.Set;

/**
 * 支持灰度功能的 {@link ReactiveLoadBalancerClientFilter} 实现类
 *
 * @author Ryan
 */
@Slf4j
@SuppressWarnings({ "rawtypes", "unchecked" })
public class GrayReactiveLoadBalancerClientFilter extends ReactiveLoadBalancerClientFilter {

    private static final String GRAY_LB = "lb";

    private final LoadBalancerClientFactory clientFactory;

    private final GatewayLoadBalancerProperties properties;

    public GrayReactiveLoadBalancerClientFilter(LoadBalancerClientFactory clientFactory, GatewayLoadBalancerProperties properties) {
        super(null, properties);
        this.clientFactory = clientFactory;
        this.properties = properties;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        URI requestUri = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR);
        String schemePrefix = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_SCHEME_PREFIX_ATTR);

        // 将 lb 替换成 grayLb，表示灰度负载均衡
        if (requestUri == null || (!GRAY_LB.equals(requestUri.getScheme()) && !GRAY_LB.equals(schemePrefix))) {
            return chain.filter(exchange);
        }

        // preserve the original url
        ServerWebExchangeUtils.addOriginalRequestUrl(exchange, requestUri);

        if (log.isTraceEnabled()) {
            log.trace("{} url before: {}", ReactiveLoadBalancerClientFilter.class.getSimpleName(), requestUri);
        }

        String serviceId = requestUri.getHost();
        Set<LoadBalancerLifecycle> supportedLifecycleProcessors = LoadBalancerLifecycleValidator
                .getSupportedLifecycleProcessors(clientFactory.getInstances(serviceId, LoadBalancerLifecycle.class),
                        RequestDataContext.class, ResponseData.class, ServiceInstance.class);
        DefaultRequest<RequestDataContext> lbRequest = new DefaultRequest<>(
                new RequestDataContext(new RequestData(exchange.getRequest()), getHint(serviceId)));

        // 负载均衡
        return choose(lbRequest, serviceId, supportedLifecycleProcessors).doOnNext(response -> {
                    if (!response.hasServer()) {
                        supportedLifecycleProcessors.forEach(lifecycle -> lifecycle
                                .onComplete(new CompletionContext<>(CompletionContext.Status.DISCARD, lbRequest, response)));
                        throw NotFoundException.create(properties.isUse404(), "Unable to find instance for " + serviceId);
                    }

                    ServiceInstance retrievedInstance = response.getServer();

                    URI uri = exchange.getRequest().getURI();

                    String overrideScheme = retrievedInstance.isSecure() ? "https" : "http";
                    if (schemePrefix != null) {
                        overrideScheme = requestUri.getScheme();
                    }

                    DelegatingServiceInstance serviceInstance = new DelegatingServiceInstance(retrievedInstance, overrideScheme);

                    if (log.isDebugEnabled()) {
                        log.debug("[LoadBalancerClientFilter] reconstructURI: {}, {}", uri, serviceInstance);
                    }

                    URI requestUrl = LoadBalancerUriTools.reconstructURI(serviceInstance, uri);

                    log.info("[LoadBalancerClientFilter] url chosen: {}", requestUrl);

                    exchange.getAttributes().put(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR, requestUrl);
                    exchange.getAttributes().put(ServerWebExchangeUtils.GATEWAY_LOADBALANCER_RESPONSE_ATTR, response);
                    supportedLifecycleProcessors.forEach(lifecycle -> lifecycle.onStartRequest(lbRequest, response));
                }).then(chain.filter(exchange))
                .doOnError(throwable -> supportedLifecycleProcessors.forEach(lifecycle -> lifecycle
                        .onComplete(new CompletionContext<ResponseData, ServiceInstance, RequestDataContext>(
                                CompletionContext.Status.FAILED, throwable, lbRequest,
                                exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_LOADBALANCER_RESPONSE_ATTR)))))
                .doOnSuccess(aVoid -> supportedLifecycleProcessors.forEach(lifecycle -> lifecycle
                        .onComplete(new CompletionContext<ResponseData, ServiceInstance, RequestDataContext>(
                                CompletionContext.Status.SUCCESS, lbRequest,
                                exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_LOADBALANCER_RESPONSE_ATTR),
                                new ResponseData(exchange.getResponse(), new RequestData(exchange.getRequest()))))));
    }

    private Mono<Response<ServiceInstance>> choose(Request<RequestDataContext> lbRequest, String serviceId, Set<LoadBalancerLifecycle> supportedLifecycleProcessors) {
        GrayRoundRobinLoadBalancer loadBalancer = new GrayRoundRobinLoadBalancer(clientFactory.getLazyProvider(serviceId, ServiceInstanceListSupplier.class), serviceId);
        supportedLifecycleProcessors.forEach(lifecycle -> lifecycle.onStart(lbRequest));
        return loadBalancer.choose(lbRequest);
    }

    private String getHint(String serviceId) {
        LoadBalancerProperties loadBalancerProperties = clientFactory.getProperties(serviceId);
        Map<String, String> hints = loadBalancerProperties.getHint();
        String defaultHint = hints.getOrDefault("default", "default");
        String hintPropertyValue = hints.get(serviceId);
        return hintPropertyValue != null ? hintPropertyValue : defaultHint;
    }

    @Override
    public int getOrder() {
        return LOAD_BALANCER_CLIENT_FILTER_ORDER;
    }
}
