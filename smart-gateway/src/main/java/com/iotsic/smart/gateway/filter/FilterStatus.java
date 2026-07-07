package com.iotsic.smart.gateway.filter;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * filter 简单状态控制
 *
 * @author Ryan
 */
@Slf4j
public class FilterStatus implements Ordered {

    private static final List<Integer> FILTER_LIST = new ArrayList<>(16);

    private static final String REQUEST_FILTER_COMPLETE = "complete";

    /**
     * 标识 filter 结束, 不往下 pass
     */
    public Mono<Void> complete(ServerWebExchange exchange, GatewayFilterChain chain) {
        exchange.getAttributes().put(REQUEST_FILTER_COMPLETE, true);
        return chain.filter(exchange);
    }

    /**
     * 是否已标识 filter 结束，不往下 pass
     */
    public boolean isCompleted(ServerWebExchange exchange) {
        return exchange.getAttribute(REQUEST_FILTER_COMPLETE) != null;
    }

    /**
     * 是否跳过
     */
    public boolean isSkipped(ServerWebExchange exchange) {
        return exchange.getAttribute(String.valueOf(getOrder())) != null;
    }

    /**
     * skip 下一个
     */
    public void skipNext(ServerWebExchange exchange) {
        int[] intArr = FILTER_LIST.stream().mapToInt(Integer::intValue).toArray();
        Arrays.sort(intArr);
        log.debug("skipNext :{}", Arrays.toString(intArr));
        if (intArr.length == 0) {
            return;
        }
        for (int i = 0; i < intArr.length - 1; i++) {
            if (intArr[i] == getOrder()) {
                exchange.getAttributes().put(String.valueOf(intArr[i + 1]), true);
            }
        }
    }

    @PostConstruct
    void init() {
        synchronized (FILTER_LIST) {
            FILTER_LIST.add(getOrder());
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
