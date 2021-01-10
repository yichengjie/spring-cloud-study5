package com.yicj.study.feign.config;

import com.yicj.study.feign.handler.HelloFeignHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;


@Configuration
public class MyConfig {

    @Bean
    public RouterFunction routerFunction(HelloFeignHandler handler){
        return RouterFunctions.route(GET("/search/github"), handler::searchGithubRepoByStr) ;
    }
}
