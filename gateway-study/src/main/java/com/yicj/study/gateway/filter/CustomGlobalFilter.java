package com.yicj.study.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class CustomGlobalFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 这里编写的内容为pre逻辑
        log.info("filter pre busi ...");
        return chain.filter(exchange).then(Mono.fromRunnable(()->{
            // 这里编写的内容为post逻辑
            log.info("filter post busi ...");
        }));
    }
}
