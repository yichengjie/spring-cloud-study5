package com.yicj.study.webflux.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class HomeController {
    @GetMapping("/helloWorld")
    public Mono<String> hello(){
        return Mono.just("hello world") ;
    }
}
