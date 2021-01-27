package com.yicj.study.gateway.component;

import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.core.publisher.ParallelFlux;

public class DefaultServerRequest {
    public DefaultServerRequest(ServerWebExchange exchange) {
    }

    public Mono<Object> bodyToMono(Class<String> stringClass) {
        return null;
    }
}
