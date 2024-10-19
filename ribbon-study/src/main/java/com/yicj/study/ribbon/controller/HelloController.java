package com.yicj.study.ribbon.controller;

import com.yicj.study.ribbon.client.HelloFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HelloController {
    @Autowired
    private RestTemplate restTemplate ;

    @Autowired
    private HelloFeignClient feignClient ;

    @GetMapping("/hello")
    public String hello(@RequestParam("name") String name){
        String url = "http://CLIENT-A/hello2?name=" + name ;
        return restTemplate.getForObject(url, String.class) ;
    }

    @GetMapping("/hello2")
    public String hello2(@RequestParam("name") String name){
        return feignClient.hello2(name);
    }
}
