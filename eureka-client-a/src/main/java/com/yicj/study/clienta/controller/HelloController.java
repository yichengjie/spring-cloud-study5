package com.yicj.study.clienta.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        return "hello world" ;
    }

    @GetMapping("/xxx")
    public String xxx(){
        return "hello xxx" ;
    }

    @GetMapping("/hello2")
    public String hello2(@RequestParam("name") String name){

        return "hello " + name ;
    }

    @GetMapping("/")
    public String index(){

        return "index" ;
    }
}
