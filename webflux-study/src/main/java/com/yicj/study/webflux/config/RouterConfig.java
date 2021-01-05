package com.yicj.study.webflux.config;

import com.yicj.study.webflux.handler.HelloHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {

    @Autowired
    private HelloHandler helloHandler ;

    @Bean
    public RouterFunction<ServerResponse> helloRouter(){
        RouterFunction<ServerResponse> routerFunction =
                route(GET("/hello"), helloHandler::hello)
                .andRoute(GET("/world"), helloHandler::world)
                .andRoute(GET("/times"), helloHandler::times)
                .andRoute(GET("/findById/{id}"), helloHandler::findById);

        return routerFunction ;
    }

}
