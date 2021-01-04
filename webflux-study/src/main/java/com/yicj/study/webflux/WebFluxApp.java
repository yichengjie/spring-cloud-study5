package com.yicj.study.webflux;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.yicj.study.webflux.mapper")
@SpringBootApplication
public class WebFluxApp {
    public static void main(String[] args) {
        SpringApplication.run(WebFluxApp.class, args) ;
    }
}
