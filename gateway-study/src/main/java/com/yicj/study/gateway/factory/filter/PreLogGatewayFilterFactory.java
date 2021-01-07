package com.yicj.study.gateway.factory.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractNameValueGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

@Slf4j
@Component
public class PreLogGatewayFilterFactory extends AbstractNameValueGatewayFilterFactory {
    @Override
    public GatewayFilter apply(NameValueConfig config) {
        return (exchange, chain) -> {
            log.info("请求进来了...{}, {}", config.getName(), config.getValue());
            //1. 修改request
            //exchange.getRequest().mutate().xxx ;
            //2. 修改exchange
            //exchange.mutate().xxx
            //3. 传递给下一个过滤器处理
            //chain.filter(exchange)
            //4. 拿到响应
            //exchange.getResponse() ;
            ServerHttpRequest modifiedRequest = exchange.getRequest().mutate().build();
            ServerWebExchange modifiedExchange = exchange.mutate().request(modifiedRequest).build();
            return chain.filter(modifiedExchange) ;
        };
    }
}
