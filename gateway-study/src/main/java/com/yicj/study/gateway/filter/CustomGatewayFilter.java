package com.yicj.study.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
public class CustomGatewayFilter implements GatewayFilter {
    private static final String COUNT_START_TIME = "COUNT_START_TIME" ;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        exchange.getAttributes().put(COUNT_START_TIME, System.currentTimeMillis()) ;
        return chain.filter(exchange).then(
           Mono.fromRunnable(()->{
               Long startTime = exchange.getAttribute(COUNT_START_TIME);
               long endTime = (System.currentTimeMillis() - startTime) ;
               log.info("=====> " + exchange.getRequest().getURI().getRawPath() +": " + endTime +"ms");
           })
        );
    }
}
