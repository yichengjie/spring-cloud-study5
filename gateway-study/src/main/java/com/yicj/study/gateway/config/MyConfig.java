package com.yicj.study.gateway.config;

import com.yicj.study.gateway.filter.CustomGatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
}
