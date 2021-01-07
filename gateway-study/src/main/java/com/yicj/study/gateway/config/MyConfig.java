package com.yicj.study.gateway.config;

import com.yicj.study.gateway.filter.CustomGatewayFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import reactor.core.publisher.Mono;

@Slf4j
@Configuration
public class MyConfig {

    @Bean
    public RouteLocator customerRouteLocator1(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/test/**")
                        .filters(f -> f.stripPrefix(1).filter(new CustomGatewayFilter()))
                        // 注意这里写http://localhost:8081与写http://localhost:8081/hello效果一样
                        .uri("http://localhost:8081/xxx")
                        .order(0)
                        .id("customer_filter_router1")
                )
                .build();
    }

    @Bean
    public RouteLocator customerRouteLocator2(RouteLocatorBuilder builder) {
        return builder.routes()
            .route(r -> r.path("/xxx")
                    .filters(f -> f.filter(new CustomGatewayFilter()))
                    // 注意这里写http://localhost:8081与写http://localhost:8081/hello效果一样
                    .uri("http://localhost:8081/hello")
                    .order(0)
                    .id("customer_filter_router2")
            )
            .build();
    }


    /**
     * lambda return之前时pre 过滤器
     * return 之后的then中式post过滤器
     * @return
     */
    @Bean
    @Order(-1)
    public GlobalFilter a() {
        return (exchange, chain) -> {
            log.info("first pre filter");
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                log.info("third post filter");
            }));
        };
    }

    @Bean
    @Order(0)
    public GlobalFilter b() {
        return (exchange, chain) -> {
            log.info("second pre filter");
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                log.info("second post filter");
            }));
        };
    }

    @Bean
    @Order(1)
    public GlobalFilter c() {
        return (exchange, chain) -> {
            log.info("third pre filter");
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                log.info("first post filter");
            }));
        };
    }
}
