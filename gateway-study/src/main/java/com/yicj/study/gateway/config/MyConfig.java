package com.yicj.study.gateway.config;

import com.yicj.study.gateway.filter.CustomGatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig {

    @Bean
    public RouteLocator customerRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
            .route(r -> r.path("/customer/**")
                    .filters(f -> f.stripPrefix(1).filter(new CustomGatewayFilter()))
                    .uri("http://localhost:8081")
                    .order(0)
                    .id("customer_filter_router")
            )
            .build();
    }
}
