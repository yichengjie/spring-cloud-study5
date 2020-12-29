package com.yicj.study.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
// Gateway Filter与Global Filter的区别
// 从作用范围来看，Global Filter会被应用到所有的路由上，而Gateway Filter仅应用到单个路由或则一个分组路由上
//@Component
public class AuthSignatureFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = exchange.getRequest().getQueryParams().getFirst("authToken") ;
        if (token == null || token.isEmpty()){
            // 当请求不携带token或则token为空时，直接设置请求状态码为401，返回
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED) ;
            return exchange.getResponse().setComplete() ;
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -400;
    }
}
