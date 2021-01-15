package com.yicj.study.ribbon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HelloController {
    @Autowired
    private RestTemplate restTemplate ;
    @GetMapping("/hello")
    public String hello(@RequestParam("name") String name){
        String url = "http://CLIENT-A/hello2?name=" + name ;
        return restTemplate.getForObject(url, String.class) ;
    }
}
